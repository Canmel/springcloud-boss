package com.camel.attendance.service.impl;

import com.camel.attendance.enums.SignRecordDetermine;
import com.camel.attendance.enums.SignRecordStatus;
import com.camel.attendance.enums.SignRecordType;
import com.camel.attendance.exceptions.NotSignInTimeException;
import com.camel.attendance.exceptions.NotSignOutTimeException;
import com.camel.attendance.model.Args;
import com.camel.attendance.model.SignRecords;
import com.camel.attendance.mapper.SignRecordsMapper;
import com.camel.attendance.service.ArgsService;
import com.camel.attendance.service.SignRecordsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.attendance.utils.ApplicationToolsUtils;
import com.camel.attendance.vo.SignDayRecord;
import com.camel.attendance.vo.SignRecordTotal;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        <打卡记录 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-11-27
 *                ┗┻┛    ┗┻┛
 */
@Service
public class SignRecordsServiceImpl extends ServiceImpl<SignRecordsMapper, SignRecords> implements SignRecordsService {

    @Autowired
    private SignRecordsMapper mapper;

    @Autowired
    private ArgsService argsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public PageInfo<SignRecords> selectPage(SignRecords entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        List<SignRecords> signRecordsList = pageInfo.getList();
        signRecordsList.forEach(signRecord -> {
            applicationToolsUtils.allUsers().forEach(sysUser -> {
                if (sysUser.getUid().equals(signRecord.getUserId())) {
                    signRecord.setUser(sysUser);
                }
            });
        });
        return pageInfo;
    }

    @Override
    public Result signIn(SignRecords signRecords, OAuth2Authentication auth2Authentication) throws ParseException, NotSignInTimeException, NotSignOutTimeException {
        // 获取打卡时间的配置，完成记录， 创建快照
        setArgs(signRecords);

        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, auth2Authentication.getName());
        signRecords.setUserId(member.getId());
        signRecords.setStatus(SignRecordStatus.NORMAL.getCode());
        signRecords.setType(SignRecordType.SIGN_IN.getCode());
        determine(signRecords);

        if (doSign(signRecords, member)) {
            return ResultUtil.success("考勤成功");
        } else {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "未知的错误，请联系管理员");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean doSign(SignRecords signRecords, Member member) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SignRecords si = new SignRecords(member.getId(), simpleDateFormat.format(new Date()));
        SignRecords result = mapper.selectLastValidRecord(si);
        if (!ObjectUtils.isEmpty(result)) {
            if (SignRecordDetermine.NORMAL.getValue().equals(result.getDetermine())) {
                return true;
            }
            result.setStatus(0);
            mapper.updateById(result);
        }
        return insert(signRecords);
    }

    @Override
    public Result signOut(SignRecords signRecords, OAuth2Authentication auth2Authentication) throws ParseException, NotSignInTimeException, NotSignOutTimeException {
        // 获取打卡时间的配置，完成记录， 创建快照
        setArgs(signRecords);
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, auth2Authentication.getName());
        signRecords.setUserId(member.getId());
        signRecords.setStatus(SignRecordStatus.NORMAL.getCode());
        signRecords.setType(SignRecordType.SIGN_OUT.getCode());
        determine(signRecords);
        if (doSign(signRecords, member)) {
            return ResultUtil.success("操作成功");
        } else {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "未知的错误，请联系管理员");
        }
    }

    /**
     * 判定
     * @param signRecords
     */
    public void determine(SignRecords signRecords) throws ParseException, NotSignInTimeException, NotSignOutTimeException {
        signRecords.setDetermine(SignRecordDetermine.NORMAL.getValue());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);

        SimpleDateFormat dataParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date signInDate = dataParser.parse(dateStr + " " + signRecords.getSignInTime());
        Date signOutDate = dataParser.parse(dateStr + " " + signRecords.getSignOutTime());

        if (SignRecordType.SIGN_IN.getValue().equals(signRecords.getType())) {
            if (date.getTime() > signInDate.getTime()) {
                signRecords.setDetermine(SignRecordDetermine.DELAY.getValue());
            } else {
                if ((date.getTime() - signRecords.getAdvanceTime() * 60 * 1000) < date.getTime()) {
                    signRecords.setDetermine(SignRecordDetermine.NORMAL.getValue());
                } else {
                    throw new NotSignInTimeException();
                }
            }
        }
        if (SignRecordType.SIGN_OUT.getValue().equals(signRecords.getType())) {
            if (date.getTime() < signOutDate.getTime()) {
                signRecords.setDetermine(SignRecordDetermine.ADVANCE.getValue());
            } else {
                if ((signOutDate.getTime() + signRecords.getDelayTime() * 60 * 1000) > date.getTime()) {
                    signRecords.setDetermine(SignRecordDetermine.NORMAL.getValue());
                } else {
                    throw new NotSignOutTimeException();
                }
            }
        }

    }

    private void setArgs(SignRecords signRecords) {
        List<Args> argsList = argsService.selectForMain();
        argsList.forEach(args -> {
            if (Args.SIGN_IN_TIME_CODE.equals(args.getCode())) {
                signRecords.setSignInTime(args.getValue());
            }
            if (Args.SIGN_OUT_TIME_CODE.equals(args.getCode())) {
                signRecords.setSignOutTime(args.getValue());
            }
            if (Args.ADVANCE_TIME_CODE.equals(args.getCode())) {
                signRecords.setAdvanceTime(Integer.parseInt(args.getValue()));
            }
            if (Args.DELAY_TIME_CODE.equals(args.getCode())) {
                signRecords.setDelayTime(Integer.parseInt(args.getValue()));
            }
        });
    }

    @Override
    public List<Map<String, Object>> selectByMonth(String ydate, String mdate, OAuth2Authentication oAuth2Authentication) {
        String month = ydate + "-" + mdate;
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        SignRecords signRecords = new SignRecords(member.getId(), month);
        List<String> days = getDayByMonth(ydate, mdate);
        List<Map<String, Object>> result = new ArrayList<>();
        // 查询这个月的签到情况
        List<SignRecords> signRecordsList = mapper.selectTotalByMonth(signRecords);
        days.forEach(day -> {
            Map<String, Object> subResult = new HashMap<>(16);
            subResult.put("createdAt", day);
            signRecordsList.forEach(record -> {
                if (day.equals(simpleDateFormat.format(record.getCreatedAt()))) {
                    Integer determineValue = record.getDetermine();
                    Integer signType = record.getType();
                    // 签到
                    if (SignRecordType.SIGN_IN.getValue().equals(signType)) {
                        if (determineValue.equals(SignRecordDetermine.NORMAL.getValue())) {
                            subResult.put("sign_in_determine_code", SignRecordDetermine.NORMAL.getValue());
                            subResult.put("sign_in_determine_text", SignRecordDetermine.NORMAL.getName());
                        } else if (determineValue.equals(SignRecordDetermine.ADVANCE.getValue())) {
                            subResult.put("sign_in_determine_code", SignRecordDetermine.ADVANCE.getValue());
                            subResult.put("sign_in_determine_text", SignRecordDetermine.ADVANCE.getName());
                        } else if (determineValue.equals(SignRecordDetermine.DELAY.getValue())) {
                            subResult.put("sign_in_determine_code", SignRecordDetermine.DELAY.getValue());
                            subResult.put("sign_in_determine_text", SignRecordDetermine.DELAY.getName());
                        }

                        subResult.put("sign_in_time", record.getCreatedAtStr());
                    } else {
                        if (determineValue.equals(SignRecordDetermine.NORMAL.getValue())) {
                            subResult.put("sign_out_determine_code", SignRecordDetermine.NORMAL.getValue());
                            subResult.put("sign_out_determine_text", SignRecordDetermine.NORMAL.getName());
                        } else if (determineValue.equals(SignRecordDetermine.ADVANCE.getValue())) {
                            subResult.put("sign_out_determine_code", SignRecordDetermine.ADVANCE.getValue());
                            subResult.put("sign_out_determine_text", SignRecordDetermine.ADVANCE.getName());
                        } else if (determineValue.equals(SignRecordDetermine.DELAY.getValue())) {
                            subResult.put("sign_out_determine_code", SignRecordDetermine.DELAY.getValue());
                            subResult.put("sign_out_determine_text", SignRecordDetermine.DELAY.getName());
                        }

                        subResult.put("sign_out_time", record.getCreatedAtStr());
                    }


                }
            });
            result.add(subResult);
        });
        return result;
    }

    public void loadSignResult(Map<String, Object> subResult, SignRecords record) {
        Integer determineValue = record.getDetermine();
        if (determineValue.equals(SignRecordDetermine.NORMAL.getValue())) {
            subResult.put("sign_determine_code", SignRecordDetermine.NORMAL.getValue());
            subResult.put("sign_determine_text", SignRecordDetermine.NORMAL.getName());
        } else if (determineValue.equals(SignRecordDetermine.ADVANCE.getValue())) {
            subResult.put("sign_determine_code", SignRecordDetermine.ADVANCE.getValue());
            subResult.put("sign_determine_text", SignRecordDetermine.ADVANCE.getName());
        } else if (determineValue.equals(SignRecordDetermine.DELAY.getValue())) {
            subResult.put("sign_determine_code", SignRecordDetermine.DELAY.getValue());
            subResult.put("sign_determine_text", SignRecordDetermine.DELAY.getName());
        }
    }

    public static List<String> getDayByMonth(String yearParam, String monthParam) {
        List list = new ArrayList();
        Calendar calendar = new GregorianCalendar(Integer.parseInt(yearParam), Integer.parseInt(monthParam), 0);
        int day = calendar.getActualMaximum(Calendar.DATE);

        int y = new Date().getYear() + 1900;
        int m = new Date().getMonth() + 1;
        int d = new Date().getDay();

        if (Integer.parseInt(yearParam) == y && Integer.parseInt(monthParam) == m) {
            day = d;
        }

        for (int i = 1; i <= day; i++) {
            String aDate = null;
            if (Integer.parseInt(monthParam) < 10 && i < 10) {
                aDate = yearParam + "-0" + monthParam + "-0" + i;
            }
            if (Integer.parseInt(monthParam) < 10 && i >= 10) {
                aDate = yearParam + "-0" + monthParam + "-" + i;
            }
            if (Integer.parseInt(monthParam) >= 10 && i < 10) {
                aDate = yearParam + "-" + monthParam + "-0" + i;
            }
            if (Integer.parseInt(monthParam) >= 10 && i >= 10) {
                aDate = yearParam + "-" + monthParam + "-" + i;
            }

            list.add(aDate);
        }
        return list;
    }

    @Override
    public List<SignRecords> day(String day, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        SignRecords signRecords = new SignRecords(member.getId(), day);
        return mapper.selectByDay(signRecords);
    }

    @Override
    public SignRecordTotal totalByMonth(String month, OAuth2Authentication oAuth2Authentication) {
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, oAuth2Authentication.getName());
        SignRecords signRecords = new SignRecords(member.getId(), month);
        List<SignRecords> signRecordsList = mapper.selectTotalByMonth(signRecords);
        List<String> days = getDayByMonth(month.split("-")[0], month.split("-")[1]);

        List<SignDayRecord> signDayRecordList = getDayNomalRecordsFromRecords(month, signRecordsList);
        // 判定正常
        determineDays(signDayRecordList);
        // 判定异常
        determineAbnormal(signDayRecordList, signRecordsList);
        return null;
    }

    /**
     * 通过考勤记录获取每日记录统计
     * @param month
     * @param signRecordsList
     * @return
     */
    public List<SignDayRecord> getDayNomalRecordsFromRecords(String month, List<SignRecords> signRecordsList) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> days = getDayByMonth(month.split("-")[0], month.split("-")[1]);
        List<SignDayRecord> recordsList = new ArrayList<>();
        days.forEach(day -> {
            SignDayRecord signDayRecord = new SignDayRecord(day);
            signRecordsList.forEach(si -> {
                if (simpleDateFormat.format(si.getCreatedAt()).equals(day)) {
                    // 签到
                    if (SignRecordType.SIGN_IN.getValue().equals(si.getType())) {
                        // 正常
                        if (SignRecordDetermine.NORMAL.getValue().equals(si.getDetermine())) {
                            signDayRecord.setSignInDate(format.format(si.getCreatedAt()));
                        }
                    }
                    // 签退
                    if (SignRecordType.SIGN_OUT.getValue().equals(si.getType())) {
                        // 正常
                        if (SignRecordDetermine.NORMAL.getValue().equals(si.getDetermine())) {
                            signDayRecord.setSignOutDate(format.format(si.getCreatedAt()));
                        }
                    }
                }
            });
            recordsList.add(signDayRecord);
        });
        return recordsList;
    }

    /**
     * 判定每天考勤状态
     * @param signDayRecords
     * @return
     */
    public List<SignDayRecord> determineDays(List<SignDayRecord> signDayRecords) {
        signDayRecords.forEach(signDayRecord -> {
            if (StringUtils.isNotBlank(signDayRecord.getSignInDate()) && StringUtils.isNotBlank(signDayRecord.getSignOutDate())) {
                signDayRecord.setStatus(SignRecordDetermine.NORMAL.getValue());
            }
        });

        return signDayRecords;
    }

    /**
     * 判定异常
     * @return
     */
    public List<SignDayRecord> determineAbnormal(List<SignDayRecord> signDayRecords, List<SignRecords> signRecordsList) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        signDayRecords.forEach(signDayRecord -> {
            if (!SignRecordDetermine.NORMAL.getValue().equals(signDayRecord.getStatus())) {
                boolean hasSign = false;
                for (int i = 0; i < signRecordsList.size(); i++) {
                    if (simpleDateFormat.format(signRecordsList.get(i).getCreatedAt()).equals(signDayRecord.getSignDay())) {
                        hasSign = true;
                    }


                }
                if (!hasSign) {
                    signDayRecord.setStatus(SignRecordDetermine.FORGET.getValue());
                }
            }
            System.out.println(signDayRecord.toString());
        });
        return signDayRecords;
    }
}
