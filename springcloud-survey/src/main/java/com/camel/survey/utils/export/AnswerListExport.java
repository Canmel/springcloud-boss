package com.camel.survey.utils.export;

import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.ZsAnswerMapper;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.model.ZsSurvey;
import com.camel.survey.service.ZsAnswerItemService;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.service.ZsSurveyService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AnswerListExport {

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ZsSurveyService zsSurveyService;

    @Autowired
    private ZsAnswerMapper zsAnswerMapper;

    @Autowired
    private ZsQuestionService zsQuestionService;

    @Autowired
    private ZsAnswerItemService zsAnswerItemService;

    public HSSFWorkbook list(HSSFWorkbook wb, ZsAnswer answer) throws ParseException {
        Integer surveyId = answer.getSurveyId();
        HSSFSheet sheet = wb.createSheet("附录-样本明细");
        HSSFCellStyle headStyle = HSSFUtils.createHeadStyle(wb);
        HSSFCellStyle style = HSSFUtils.createCellStyle(wb);
        ZsSurvey survey = zsSurveyService.selectById(surveyId);
        List<Object> surveyName = new ArrayList<>();
        surveyName.add("项目名称");
        surveyName.add(survey.getName());
        HSSFUtils.fillRow(sheet.createRow(0), headStyle, surveyName);
        String timeRange = zsAnswerMapper.selectTimeRange(surveyId);
        System.out.println(timeRange);
        List<Object> startTime = new ArrayList<>();
        startTime.add("工作开始时间");
        startTime.add(sf.format(new Date(sf.parse(timeRange.substring(0, 19)).getTime() - 300000)));
        HSSFUtils.fillRow(sheet.createRow(1), headStyle, startTime);
        List<Object> endTime = new ArrayList<>();
        endTime.add("工作结束时间");
        endTime.add(sf.format(new Date(sf.parse(timeRange.substring(22, 41)).getTime() + 300000)));
        HSSFUtils.fillRow(sheet.createRow(2), headStyle, endTime);
        List<ZsQuestion> questionList = zsQuestionService.selectBySurveyId(surveyId);
        List<Object> headValues = new ArrayList<>();
        headValues.add("时间");
        headValues.add("电话");
        headValues.add("工号");
        headValues.add("访员姓名");
        headValues.add("收集开始时间");
        headValues.add("收集结束时间");
        headValues.add("通话时长");
        headValues.add("是否有效");
        headValues.add("无效原因");
        headValues.add("复核人");
        headValues.add("复核时间");
        headValues.add("复核意见");
        headValues.add("标签");
        List<String> titleQList = new ArrayList<>();
        List<String> titleIdList = new ArrayList<>();
        questionList.forEach(que -> {
            if (que.getType().equals(2)) {
                for (int i = 0; i < que.getOptions().size(); i++) {
                    titleQList.add(que.getName() + "_" + que.getOptions().get(i).getName());
                    titleIdList.add(que.getId() + "_" + que.getOptions().get(i).getId());
                }
            } else {
                titleQList.add(que.getName());
                titleIdList.add(que.getId() + "");
            }
        });
        headValues.addAll(titleQList);
        headValues.add("合计");

        HSSFUtils.fillRow(sheet.createRow(3), headStyle, headValues);
        zsAnswerMapper.addUsername();
        List<Map<String, Object>> result = zsAnswerItemService.selectExport(surveyId);
        for (int i = 0; i < result.size(); i++) {
            int cellNum = 0;
            HSSFRow row = sheet.createRow(4 + i);
            HSSFUtils.fillCell(row.createCell(cellNum++), style, sf.format(result.get(i).get("created_at")));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("creator"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("work_num"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("username"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("start_time"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("end_time"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("call_lasts_time"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, result.get(i).get("valid").equals(ZsYesOrNo.YES.getCode()) ? "正常" : "无效");
            HSSFUtils.fillCell(row.createCell(cellNum++), style, HSSFUtils.renderInvalidMsg(result.get(i)));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("reviewer_name"));
            Date reviewAt = (Date) result.get(i).get("reviewer_at");
            HSSFUtils.fillCell(row.createCell(cellNum++), style, ObjectUtils.isEmpty(result.get(i).get("reviewer_at")) ? "" : sf.format(new Date(reviewAt.getTime())));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("review_msg"));
            HSSFUtils.fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("label"));
            String answers = (String) result.get(i).get("answers");
            String[] answersArray = answers.split("@##@", -1);

            String options = (String) result.get(i).get("optionIds");
            String[] optionsArray = options.split("@##@", -1);
            List<String> optionList = CollectionUtils.arrayToList(optionsArray);

            String ques = "";
            String queIds = "";
            ques = (String) result.get(i).get("questions");
            queIds = (String) result.get(i).get("questionIds");
            String[] questions = ques.split("@##@", -1);
            String[] questionIds = queIds.split("@##@", -1);
            List<String> qs = CollectionUtils.arrayToList(questions);
            List<String> qIds = CollectionUtils.arrayToList(questionIds);

            // 循环所有标题/表头上的题目
            for (int index = 0; index < titleIdList.size(); index++) {
                // 拆分表头
                String titleStr = titleIdList.get(index);
                String titleQ = "";
                String titleO = "";
                if (titleStr.contains("_")) {
                    titleQ = titleStr.split("_")[0];
                    titleO = titleStr.split("_")[1];
                }
                // 循环问题
                for (int qIndex = 0; qIndex < qIds.size(); qIndex++) {
                    // 全等，即单选
                    if (titleStr.equals(qIds.get(qIndex))) {
                        HSSFUtils.fillCell(row.createCell(13 + index), style, answersArray[qIndex]);
                        qIndex = qIds.size();
                    } else {
                        // 多选， 并且问题和excel当前表头相同
                        if (titleQ.equals(qIds.get(qIndex))) {
                            // 根据问题的序号，得出的选项
                            String oStr = optionList.get(qIndex);
                            // 如果excel中表头也有这个选项，则表示位置正确
                            if (org.apache.commons.lang.StringUtils.isNotBlank(oStr) && oStr.equals(titleO)) {
                                HSSFUtils.fillCell(row.createCell(13 + index), style, answersArray[qIndex]);
                                qIndex = qIds.size();
                            }
                        }
                    }
                }

            }
        }
        return wb;
    }
}
