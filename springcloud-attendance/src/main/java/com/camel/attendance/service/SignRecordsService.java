package com.camel.attendance.service;

import com.camel.attendance.exceptions.NotSignInTimeException;
import com.camel.attendance.exceptions.NotSignOutTimeException;
import com.camel.attendance.model.SignRecords;
import com.baomidou.mybatisplus.service.IService;
import com.camel.attendance.vo.SignRecordTotal;
import com.camel.core.entity.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
 *               ┃     ┃        <打卡记录 服务类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-11-27
 *                ┗┻┛    ┗┻┛
 */
public interface SignRecordsService extends IService<SignRecords> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<SignRecords> selectPage(SignRecords entity);

    /**
     * 签入
     * @param signRecords
     * @param auth2Authentication
     * @return
     * @throws ParseException
     * @throws NotSignOutTimeException
     * @throws NotSignInTimeException
     */
    Result signIn(SignRecords signRecords, OAuth2Authentication auth2Authentication) throws ParseException, NotSignOutTimeException, NotSignInTimeException;

    /**
     * 签出
     * @param signRecords
     * @param auth2Authentication
     * @return
     * @throws ParseException
     * @throws NotSignOutTimeException
     * @throws NotSignInTimeException
     */
    Result signOut(SignRecords signRecords, OAuth2Authentication auth2Authentication) throws ParseException, NotSignOutTimeException, NotSignInTimeException;

    /**
     * 获取某个年月的考勤 统计
     * @param ydate
     * @param mdate
     * @return
     */
    List<Map<String, Object>> selectByMonth(String ydate, String mdate, OAuth2Authentication oAuth2Authentication);

    /**
     * 根据人和天查询当天当人考勤记录
     * @param day
     * @param oAuth2Authentication
     * @return
     */
    List<SignRecords> day(String day, OAuth2Authentication oAuth2Authentication);

    /**
     * 按月查询统计
     * @param month
     * @param oAuth2Authentication
     * @return
     */
    SignRecordTotal totalByMonth(String month, OAuth2Authentication oAuth2Authentication);
}
