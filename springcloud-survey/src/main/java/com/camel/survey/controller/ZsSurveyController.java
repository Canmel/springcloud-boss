package com.camel.survey.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.annotation.AuthIgnore;
import com.camel.survey.enums.ZsSurveyState;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.MyFileTransterBackUpdate;
import com.camel.survey.service.ZsAnswerService;
import com.camel.survey.service.ZsSurveyService;
import com.camel.survey.service.impl.FiletransCallBack;
import com.camel.survey.utils.FileTransfer;
import com.camel.survey.vo.ZsAnswerSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 * ┗━┓     ┏━┛
 * ┃     ┃　Code is far away from bug with the animal protecting
 * ┃     ┃   神兽保佑,代码无bug
 * ┃     ┃
 * ┃     ┃
 * ┃     ┃        < 前端控制器>
 * ┃     ┃
 * ┃     ┗━━━━┓   @author baily
 * ┃          ┣┓
 * ┃          ┏┛  @since 1.0
 * ┗┓┓┏━━━━┳┓┏┛
 * ┃┫┫    ┃┫┫    @date 2019-12-06
 * ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/zsSurvey")
public class ZsSurveyController extends BaseCommonController {


    @Autowired
    private ZsSurveyService service;

    @Autowired
    private ZsAnswerService zsAnswerService;

    @Autowired
    private MyFileTransterBackUpdate myFileTransterBackUpdate;

    /**
     * 分页查询
     */
    @GetMapping
    public Result index(ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return ResultUtil.success(service.selectPage(entity, oAuth2Authentication));
    }

    @GetMapping("/avgTime/{id}")
    public Result avgTime(@PathVariable Integer id) {
        return ResultUtil.success(service.avgTime(id));
    }

    /**
     * 获取详情
     */
    @AuthIgnore
    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    /**
     * 新建保存
     */
    @PostMapping
    public Result save(@RequestBody ZsSurvey entity, OAuth2Authentication oAuth2Authentication) {
        return service.save(entity, oAuth2Authentication);
    }

    /**
     * 编辑 更新
     */
    @PutMapping
    public Result update(@RequestBody ZsSurvey entity) {
        return service.update(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @PutMapping("/close/{id}")
    public Result close(@PathVariable Integer id) {
        ZsSurvey survey = service.selectById(id);
        survey.setState(ZsSurveyState.CLOSED);
        service.updateById(survey);
        return ResultUtil.success("问卷 " + survey.getName() + " 已经关闭！");
    }

    @GetMapping("/{id}/projects")
    public Result projects(@PathVariable Integer id) {
        return service.selectListByProjectId(id);
    }

    /**
     * 获取问卷的所有问题和选项
     *
     * @param id
     * @return Result 结果集包含vo.ZsQuestionSave
     * ZsQuestionSave中包含question and option
     */
    @AuthIgnore
    @GetMapping("/questionAndOptions/{id}")
    public Result loadQuestionAndOptions(@PathVariable Integer id) {
        return service.getQuestionAndOptions(id);
    }

    /**
     * 获取问卷的所有问题和选项
     * 为了问卷
     *
     * @param id
     * @return Result 结果集包含vo.ZsQuestionSave
     * ZsQuestionSave中包含question and option
     */
    @AuthIgnore
    @GetMapping("/questions/{id}")
    public Result loadQuestionSurvey(@PathVariable Integer id) {
        ZsSurvey survey = service.selectById(id);
        if (!survey.getState().equals(ZsSurveyState.COLLECTING)) {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "该问卷尚未开始收集，请联系管理员");
        }
        return ResultUtil.success(service.questions(id));
    }

    @GetMapping("/latestSurvey")
    public Result latestSurvey() {
        return ResultUtil.success(service.findLatestSurvey());
    }

    /**
     * 开始调查
     *
     * @param id
     * @return
     */
    @GetMapping("/start/{id}")
    public Result start(@PathVariable Integer id) {
        return service.start(id);
    }


    /**
     * 回收问卷
     *
     * @param id
     * @return
     */
    @GetMapping("/stopOrUse/{id}")
    public Result stop(@PathVariable Integer id) {
        return service.stopOrUse(id);
    }


    /**
     * 申请参加
     *
     * @return
     */
    @GetMapping("/sign/{id}")
    public Result sign(@PathVariable Integer id, OAuth2Authentication oAuth2Authentication) {
        return service.sign(id, oAuth2Authentication);
    }

    @PostMapping("/valid")
    public Result valid(@RequestBody ZsAnswerSave zsAnswerSave) {
        return service.valid(zsAnswerSave);
    }

    @GetMapping("/test/{id}")
    public Result test(@PathVariable Integer id) {
        List<ZsAnswer> zsAnswerList = zsAnswerService.selectAllWithConversation(id);
        for (ZsAnswer answer : zsAnswerList) {
            if (!StringUtils.isEmpty(answer.getRecord())) {
                FileTransfer.getInstance("LTAIzMblfN958hdS", "JCvyOFHQGk2nxXspac0Cm3mnz818AG")
                        .doTrans(answer, "adgQwpK0xEsoIUrf", myFileTransterBackUpdate, zsAnswerService);
            }
        }
        return ResultUtil.success("发起成功");
    }

    @AuthIgnore
    @PostMapping("/testCallback/result")
    public void GetResult(HttpServletRequest request) {
        myFileTransterBackUpdate.update(request);
    }

    /**
     * 获取service
     */
    @Override
    public IService getiService() {
        return service;
    }

    /**
     * 获取模块名称
     */
    @Override
    public String getMouduleName() {
        return "";
    }

}