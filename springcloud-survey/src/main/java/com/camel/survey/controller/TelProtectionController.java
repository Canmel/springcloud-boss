package com.camel.survey.controller;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.model.SysCompany;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.TelProtection;
import com.camel.survey.service.PstnnumberService;
import com.camel.survey.service.TelProtectionService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.camel.survey.vo.CompanyVo;
import com.camel.survey.vo.FinalCusVo;
import com.camel.survey.vo.NumberVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baily
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/telProtection")
public class TelProtectionController extends BaseCommonController {
    public static final List<String> excelFileSuf = ListUtil.toList("XLS", "XLSX");
    public static final List<String> picFileSuf = ListUtil.toList("PNG", "JPG");


    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Autowired
    private TelProtectionService telService;

    @Autowired
    private PstnnumberService pstnnumberService;

    // @GetMapping("/apply/{id}")
    // public Result apply(@PathVariable Integer id) {
    //     return service.apply(id);
    // }

    /**
     * 供应商：分页查询号码列表
     * @param telProtection 查询条件
     * @return Result
     */
    @GetMapping("/telList")
    public Result queryByPid(TelProtection telProtection) {
        PageInfo<TelProtection> pageList = telService.queryByPid(telProtection);
        return ResultUtil.success("查询成功",pageList);
    }

    /**
     * 供应商：分页查询项目列表
     * @param telProtection 查询条件
     * @return Result
     */
    @GetMapping("/projectList")
    public Result queryByFid(TelProtection telProtection) {
        PageInfo<TelProtection> pageList = telService.queryByFid(telProtection);
        return ResultUtil.success("查询成功",pageList);
    }

    /**
     * 供应商：修改项目
     * @param telProtection 修改条件
     * @return Result
     */
    @PutMapping("/modifiByTid")
    public Result modifiByTid(@RequestBody TelProtection telProtection) {
        if (telService.modifiByTid(telProtection.getProjectId(),telProtection.getId())) {
            return ResultUtil.success("修改项目成功");
        }
        return ResultUtil.error(400, "修改项目失败");
    }

    /**
     * 显示最终用户所有电话
     * @param telProtection
     * @return
     */
    @GetMapping("/accreditList")
    public Result accredit(TelProtection telProtection){
        SysUser current = applicationToolsUtils.currentUser();
        telProtection.setFinalCusId(current.getUid());
        PageInfo<TelProtection> pageList = telService.grantTelList(telProtection);
        return ResultUtil.success("查询成功",pageList);
    }

    /**
     * 显示所有合作伙伴
     * @param sysCompany
     * @return
     */
    @GetMapping("/partnerList")
    public Result partnerList(SysCompany sysCompany,Integer telId){
        PageInfo<CompanyVo> pageList = telService.partnerList(sysCompany,telId);
        return ResultUtil.success("查询成功",pageList);
    }

    /**
     * 对供应商进行授权
     * @param telProtection
     * @return
     */
    @PutMapping("/grant")
    public Result grantNumber(@RequestBody TelProtection telProtection){
        Integer exist = telService.isExist(telProtection.getPartnerId(),telProtection.getId());
        if (exist > 0){
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(),"该用户已获得授权");
        }
        return telService.grant(telProtection);
    }

    /**
     * 撤销授权
     * @return
     */
    @PutMapping("/revoke")
    public Result revoke(@RequestBody TelProtection telProtection){
        if (StringUtils.isEmpty(telProtection.getId()) && StringUtils.isEmpty(telProtection.getPartnerId())){
            return ResultUtil.error(ResultEnum.NOT_VALID_PARAM);
        }
        return telService.revoke(telProtection);
    }

    /**
     * 解除绑定
     * @return
     */
    @DeleteMapping("/revokeTel/{id}")
    public Result revokeTel(@PathVariable("id") Integer id){
        Integer res = telService.revokeTel(id);
        if(res > 0){
            return ResultUtil.success("解除绑定成功");
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"解除绑定失败");
    }

    @GetMapping("/numberManage")
    public Result index(NumberVo numberVo){
        return ResultUtil.success("success",telService.all(numberVo));

//        String[] arr = {"1212","2323","3434","4545","5656","6767","7878","8989","9090","11111","22222","33333","44444","55555","66666","77777"};
//        //手动分页
//        List<String> list = Arrays.asList(arr);
//        //创建Page类
//        Page page = new Page(numberVo.getPageNum(), numberVo.getPageSize());
//        //为Page类中的total属性赋值
//        int total = list.size();
//        page.setTotal(total);
//        //计算当前需要显示的数据下标起始值
//        int startIndex = (numberVo.getPageNum() - 1) * numberVo.getPageSize();
//        int endIndex = Math.min(startIndex + numberVo.getPageSize(),total);
//        //从链表中截取需要显示的子链表，并加入到Page
//        page.addAll(list.subList(startIndex,endIndex));
//        //以Page创建PageInfo
//        PageInfo<String> pageInfo = new PageInfo<String>(page);
//        return ResultUtil.success("success",pageInfo);
    }

    @GetMapping("/finalList")
    public Result finalList(SysUser sysUser,String tel){
        PageInfo<FinalCusVo> finalList = telService.finalList(sysUser,tel);
        return ResultUtil.success("查询成功",finalList);
    }

    @PostMapping("/grantFinal")
    public Result grantFinal(@RequestBody TelProtection telProtection){
        if (StringUtils.isEmpty(telProtection.getTel()) && !StringUtils.isEmpty(telProtection.getFinalCusId())){
            return ResultUtil.error(ResultEnum.BAD_REQUEST);
        }
        return ResultUtil.success("绑定最终用户成功",telService.grantFinal(telProtection));
    }

    @GetMapping("/getName/{tel}")
    public Result getFinalName(@PathVariable("tel") String tel){
        return telService.getFinalName(tel);
    }

    @Override
    public IService getiService() {
        return telService;
    }

    @Override
    public String getMouduleName() {
        return "外呼号码";
    }
    //
    // public boolean isExcel(MultipartFile file) {
    //     String[] fileNames = file.getOriginalFilename().split("\\.");
    //     if (ArrayUtil.isNotEmpty(fileNames)) {
    //         String type = fileNames[fileNames.length - 1];
    //         return excelFileSuf.indexOf(type.toUpperCase()) > -1;
    //     }
    //     return false;
    // }
    //
    // public boolean isPic(MultipartFile file) {
    //     String[] fileNames = file.getOriginalFilename().split("\\.");
    //     if (ArrayUtil.isNotEmpty(fileNames)) {
    //         String type = fileNames[fileNames.length - 1];
    //         return picFileSuf.indexOf(type.toUpperCase()) > -1;
    //     }
    //     return false;
    // }
}

