package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.ZsAnswerMapper;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.service.ExportService;
import com.camel.survey.service.ZsAnswerItemService;
import com.camel.survey.service.ZsOptionService;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.model.*;
import com.camel.survey.service.*;
import com.camel.survey.utils.ExcelUtil;
import com.camel.survey.vo.ZsCrossExport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <导出>
 *
 * @author baily
 * @date 2019/12/24
 * @since 1.0
 **/
@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ZsQuestionService zsQuestionService;

    @Autowired
    private ZsOptionService zsOptionService;

    @Autowired
    private ZsAnswerMapper zsAnswerMapper;

    @Autowired
    private ZsAnswerItemService zsAnswerItemService;

    @Autowired
    private ZsSurveyService zsSurveyService;

    public static final Integer SELECT_STEP = 100;

    public static Logger logger = LoggerFactory.getLogger(ExportService.class);

    DecimalFormat df = new DecimalFormat("0.00%");
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public HSSFWorkbook items(Integer surveyId, Integer qId) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("问卷样本统计");
        List<ZsOption> optionList = zsOptionService.selectBySurveyId(surveyId);
        List<ZsOption> options = zsOptionService.selectFllByQuestionId(qId);
        List oNames = new ArrayList<>();
        options.forEach(o -> {
            oNames.add(o.getName());
        });
        oNames.add("合计");
        fillRowStrStart(sheet.createRow(0), wb.createCellStyle(), oNames, 1);
        Integer index = 0;
        for (ZsOption option : optionList) {
            if (option.getQuestionId().equals(qId)) {
                continue;
            }
            HSSFRow row = sheet.createRow(++index);
            HSSFCell cellOption = row.createCell(0);
            cellOption.setCellValue("Q" + option.getZsQuestion().getOrderNum() + "-" + option.getOrderNum() + " " + option.getName());
            Long total = 0L;
            for (ZsOption o : options) {
                Map<String, Object> count = zsAnswerMapper.selectCountCross(option.getZsQuestion().getName(), option.getName(), o.getZsQuestion().getName(), o.getName(), surveyId);
                row.createCell(options.indexOf(o) + 1).setCellValue((Long) count.get("count"));
                total += (Long) count.get("count");
            }
            row.createCell(options.size() + 1).setCellValue(total);
        }
        return wb;
    }

    @Override
    public HSSFWorkbook workNum(Integer id) throws ParseException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("访员样本统计");
        HSSFCellStyle headStyle = createHeadStyle(wb);
        HSSFCellStyle style = createCellStyle(wb);
        ZsSurvey survey = zsSurveyService.selectById(id);
        List<Object> surveyName = new ArrayList<>();
        surveyName.add("项目名称");
        surveyName.add(survey.getName());
        fillRow(sheet.createRow(0), headStyle, surveyName);
        String timeRange= zsAnswerMapper.selectTimeRange(id);
        List<Object> startTime = new ArrayList<>();
        startTime.add("工作开始时间");
        startTime.add(sf.format(new Date(sf.parse(timeRange.substring(0,19)).getTime()-300000)));
        fillRow(sheet.createRow(1), headStyle, startTime);
        List<Object> endTime = new ArrayList<>();
        endTime.add("工作结束时间");
        endTime.add(sf.format(new Date(sf.parse(timeRange.substring(22,41)).getTime()+300000)));
        fillRow(sheet.createRow(2), headStyle, endTime);
        List<Object> v = new ArrayList<>();
        v.add("工号");
        v.add("访员姓名");
        v.add("问题");
        v.add("样本");
        fillRow(sheet.createRow(3), headStyle, v);
        List<Map<String, Object>> result = zsAnswerItemService.selectWorkNumTotal(id);
        for (Map<String, Object> map : result) {
            List<Object> rowValue = new ArrayList<>();
            rowValue.add(map.get("work_num"));
            rowValue.add(map.get("username"));
            rowValue.add(map.get("questionNum"));
            rowValue.add(map.get("surveyNum"));
            fillRow(sheet.createRow(result.indexOf(map) + 4), style, rowValue);
        }
        return wb;
    }

    @Override
    public HSSFWorkbook answer(Integer surveyId) throws ParseException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("样本明细");
        HSSFCellStyle headStyle = createHeadStyle(wb);
        HSSFCellStyle style = createCellStyle(wb);
        ZsSurvey survey = zsSurveyService.selectById(surveyId);
        List<Object> surveyName = new ArrayList<>();
        surveyName.add("项目名称");
        surveyName.add(survey.getName());
        fillRow(sheet.createRow(0), headStyle, surveyName);
        String timeRange= zsAnswerMapper.selectTimeRange(surveyId);
        System.out.println(timeRange);
        List<Object> startTime = new ArrayList<>();
        startTime.add("工作开始时间");
        startTime.add(sf.format(new Date(sf.parse(timeRange.substring(0,19)).getTime()-300000)));
        fillRow(sheet.createRow(1), headStyle, startTime);
        List<Object> endTime = new ArrayList<>();
        endTime.add("工作结束时间");
        endTime.add(sf.format(new Date(sf.parse(timeRange.substring(22,41)).getTime()+300000)));
        fillRow(sheet.createRow(2), headStyle, endTime);
        List<ZsQuestion> questionList = zsQuestionService.selectBySurveyId(surveyId);
        List<Object> headValues = new ArrayList<>();
        headValues.add("时间");
        headValues.add("电话");
        headValues.add("工号");
        headValues.add("访员姓名");
        headValues.add("收集开始时间");
        headValues.add("收集结束时间");
        headValues.add("通话时长");
        headValues.add("样本状态");
        headValues.add("标签");
        List<String> titleQList = new ArrayList<>();
        questionList.forEach(que -> {
            if (que.getType().equals(2)) {
                for (int i = 0; i < que.getOptions().size(); i++) {
                    titleQList.add(que.getName() + "_" + que.getOptions().get(i).getName());
                }
            } else {
                titleQList.add(que.getName());
            }
        });
        headValues.addAll(titleQList);
        headValues.add("合计");

        fillRow(sheet.createRow(3), headStyle, headValues);
        List<Map<String, Object>> result = zsAnswerItemService.selectExport(surveyId);


        for (int i = 0; i < result.size(); i++) {
            int cellNum = 0;
            HSSFRow row = sheet.createRow(4 + i);
            fillCell(row.createCell(cellNum++), style, sf.format(result.get(i).get("created_at")));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("creator"));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("work_num"));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("username"));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("start_time"));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("end_time"));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("call_lasts_time"));
            if(result.get(i).get("in_valid_msg") !=null){
                fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("in_valid_msg"));
            }
            else{
                fillCell(row.createCell(cellNum++), style, "有效");
            }
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("label"));
            String answers = (String) result.get(i).get("answers");
            String[] answersArray = answers.split("@##@", -1);

            String options = (String) result.get(i).get("options");
            String[] optionsArray = options.split("@##@", -1);
            List<String> optionList = CollectionUtils.arrayToList(optionsArray);

            String ques = "";
            ques = (String) result.get(i).get("questions");
            String[] questions = ques.split("@##@", -1);
            List<String> qs = CollectionUtils.arrayToList(questions);

            // 循环所有标题/表头上的题目
            for (int index = 0; index < titleQList.size(); index++) {
                // 拆分表头
                String titleStr = titleQList.get(index);
                String titleQ = "";
                String titleO = "";
                if (titleStr.contains("_")) {
                    titleQ = titleStr.split("_")[0];
                    titleO = titleStr.split("_")[1];
                }
                // 循环问题
                for (int qIndex = 0; qIndex < qs.size(); qIndex++) {
                    // 全等，即单选
                    if (titleStr.equals(qs.get(qIndex))) {
                        fillCell(row.createCell(9 + index), style, answersArray[qIndex]);
                        qIndex = qs.size();
                    } else {
                        // 多选， 并且问题和excel当前表头相同
                        if (titleQ.equals(qs.get(qIndex))) {
                            // 根据问题的序号，得出的选项
                            String oStr = optionList.get(qIndex);
                            // 如果excel中表头也有这个选项，则表示位置正确
                            if (org.apache.commons.lang.StringUtils.isNotBlank(oStr) && oStr.equals(titleO)) {
                                fillCell(row.createCell(9 + index), style, answersArray[qIndex]);
                                qIndex = qs.size();
                            }
                        }
                    }
                }

            }
        }
        return wb;
    }

    @Override
    public SXSSFWorkbook total(Integer surveyId) {
        //创建一个WorkBook,对应一个Excel文件
        List<ZsQuestion> questions = zsQuestionService.selectBySurveyId(surveyId);
        SXSSFWorkbook wb = new SXSSFWorkbook();
        questions.forEach(question -> {
            logger.info("开始第" + questions.indexOf(question) + "个表的数据导出");
            logger.info("组装表头信息");
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("Q" + question.getOrderNum());
            sheet.setForceFormulaRecalculation(true);
            Row titleRow = sheet.createRow(0);
            ExcelUtil.setTotalTitle(ExcelUtil.sheetName(question.getName(), questions.indexOf(question) + 1), titleRow, sheet);
            ExcelUtil.creatTotalHead(sheet, 20);
            logger.info("查询数据");
            List<ZsOption> options = zsOptionService.selectBySurveyId(surveyId);
            int rowNum = 21;
            int oNum = 0;
            for (ZsOption option : options) {
                if (!ObjectUtils.isEmpty(option.getQuestionId()) && option.getQuestionId().equals(question.getId())) {
                    Row row = sheet.createRow(rowNum++);
                    oNum++;
                    ExcelUtil.creatTotalRow(row, option.getName(), selectAnswerItemCount(surveyId, option.getQuestionId(), option.getName()), option.getOrderNum());
                }
            }

            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 2, 15, 18);
            anchor.setAnchorType(2);
            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(21, rowNum - 1, 2, 2));
            ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(21, rowNum - 1, 3, 3));
            if(oNum > 6) {
                XSSFChart chart = (XSSFChart) drawing.createChart(anchor);
                BarChartData data = chart.getChartDataFactory().createBarChartData();
                ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
                ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
                leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
                data.addSerie(xs, ys1);
                chart.plot(data, bottomAxis, leftAxis);
                chart.getOrCreateLegend();
            } else {
                XSSFChart chart = (XSSFChart) drawing.createChart(anchor);
                PieChartData data = chart.getChartDataFactory().createPieChartData();
                data.addSerie(xs, ys1);
                chart.plot(data);
                chart.getOrCreateLegend();
            }
        });
        return wb;
    }


    @Override
    public HSSFWorkbook cross(ZsCrossExport zsCrossExport) {
        // 创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 首选和次选问题
        ZsQuestion questionS = zsQuestionService.selectById(zsCrossExport.getSecondSelect());
        if (!ObjectUtils.isEmpty(questionS)) {
            crossSingle(wb, zsCrossExport);
        } else {
            crossMuilty(wb, zsCrossExport);
        }
        return wb;
    }

    @Override
    public HSSFWorkbook crossSimple(ZsCrossExport zsCrossExport) {
        // 创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 首选和次选问题
        ZsQuestion questionS = zsQuestionService.selectById(zsCrossExport.getSecondSelect());
        if (!ObjectUtils.isEmpty(questionS)) {
            crossSingleSimple(wb, zsCrossExport);
        } else {
            crossMuiltySimple(wb, zsCrossExport);
        }
        return wb;
    }

    public void setDefaultStyle(HSSFRow row, HSSFCellStyle style, Integer colum) {
        for (int i = 0; i < colum; i++) {
            row.createCell(i).setCellStyle(style);
        }
    }

    /**
     * 单一两表交叉导出
     *
     * @param wb
     */
    public void crossSingle(HSSFWorkbook wb, ZsCrossExport zsCrossExport) {
        HSSFCellStyle style = createCellStyle(wb);
        ZsQuestion questionF = zsQuestionService.selectById(zsCrossExport.getFirstSelect());
        ZsQuestion questionS = zsQuestionService.selectById(zsCrossExport.getSecondSelect());
        DecimalFormat decimalFormat = new DecimalFormat(".00");

        List<ZsOption> optionListF = getAllOption(zsCrossExport.getFirstOption(), zsCrossExport.getFirstSelect());
        List<ZsOption> optionListS = getAllOption(zsCrossExport.getSecondOption(), zsCrossExport.getSecondSelect());
        HSSFSheet sheet = wb.createSheet("Q" + questionF.getOrderNum() + "--Q" + questionS.getOrderNum());
        HSSFRow rowQ1 = sheet.createRow(0);
        fillCell(rowQ1.createCell(0), createHeadStyle(wb), "Q" + questionF.getOrderNum() + "." + questionF.getName());
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2 * optionListF.size() + 3));
        List optionStrs = getCrossFeild(optionListF);
        fillRow(sheet.createRow(2), style, optionStrs, 2, true);
        HSSFRow rowQ2 = sheet.createRow(3);
        fillCell(rowQ2.createCell(0), createHeadStyle(wb), "Q" + questionS.getOrderNum() + "." + questionS.getName());
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 2 * optionListF.size() + 3));
        Long totalNum = 0L;
        List<HSSFRow> rows = new ArrayList<>();
        for (ZsOption os : optionListS) {
            List<Map<String, Object>> results = zsAnswerItemService.selectCrossCounts(questionF, questionS, os, zsCrossExport.getSurveyId());
            HSSFRow row = sheet.createRow(5 + 2 * optionListS.indexOf(os));
            HSSFRow rowSpace = sheet.createRow(5 + 2 * optionListS.indexOf(os) + 1);
            setDefaultStyle(row, style, optionListF.size() * 2 + 4);
            setDefaultStyle(rowSpace, style, optionListF.size() * 2 + 4);
            fillCell(row.createCell(0), style, os.getName());
            Long totalX = 0L;
            fillCell(row.createCell(0), style, os.getName());
            for (Map<String, Object> res : results) {
                for (ZsOption option : optionListF) {
                    if (option.getName().equals(res.get("option").toString())) {
                        int index = optionListF.indexOf(option);
                        if (index > -1) {
                            fillCell(row.createCell(2 + 2 * index), style, res.get("count").toString());
                            totalX += (Long) res.get("count");
                        }
                        option.setCount(option.getCount() + ((Long) res.get("count")).intValue());
                    }
                }
            }
            totalNum += totalX;
            fillCell(row.createCell(2 + 2 * optionListF.size()), style, totalX.toString());
            fillCell(row.createCell(3 + 2 * optionListF.size()), style, "100%");
            row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), rowSpace.getRowNum(), 0, 1));
            // 合计部分
            for (Map<String, Object> res : results) {
                for (ZsOption option : optionListF) {
                    if (option.getName().equals(res.get("option").toString())) {
                        int index = optionListF.indexOf(option);
                        if (index > -1) {
                            fillCell(row.createCell(2 + 2 * index + 1), style, decimalFormat.format((100 * (Long) res.get("count")) / totalX.floatValue()) + "%");
                        }
                    }
                }
            }
        }

        for (ZsOption os : optionListS) {
            String st = sheet.getRow(5 + 2 * optionListS.indexOf(os)).getCell(2 + 2 * optionListF.size()).getStringCellValue();
            String vt = decimalFormat.format(100 * Float.parseFloat(st) / totalNum.intValue()) + "%";
            sheet.getRow(6 + 2 * optionListS.indexOf(os)).getCell(2 + 2 * optionListF.size()).setCellValue(vt);
            for (ZsOption option : optionListF) {
                String s = sheet.getRow(5 + 2 * optionListS.indexOf(os)).getCell(2 + 2 * optionListF.indexOf(option)).getStringCellValue();
                if (!StringUtils.isEmpty(s)) {
                    String v = decimalFormat.format(100 * Float.parseFloat(s) / option.getCount()) + "%";
                    sheet.getRow(6 + 2 * optionListS.indexOf(os)).getCell(2 + 2 * optionListF.indexOf(option)).setCellValue(v);
                }
            }
        }

        HSSFRow total = sheet.createRow(5 + optionListS.size() * 2);
        HSSFRow totalPlus = sheet.createRow(6 + optionListS.size() * 2);
        setDefaultStyle(total, style, optionListF.size() * 2 + 4);
        setDefaultStyle(totalPlus, style, optionListF.size() * 2 + 4);
        fillCell(total.createCell(0), style, "合计");
        for (ZsOption option : optionListF) {
            fillCell(total.createCell(2 + 2 * optionListF.indexOf(option)), style, option.getCount());
            String v = decimalFormat.format(100.0 * option.getCount() / totalNum) + "%";
            fillCell(total.createCell(3 + 2 * optionListF.indexOf(option)), style, v);
            fillCell(totalPlus.createCell(2 + 2 * optionListF.indexOf(option)), style, "100%");
        }
        fillCell(total.createCell(optionListF.size() * 2 + 2), style, totalNum.intValue());
        total.getSheet().addMergedRegion(new CellRangeAddress(total.getRowNum(), totalPlus.getRowNum(), 0, 1));

    }

    /**
     * 单一两表交叉导出不带%
     *
     * @param wb
     */
    public void crossSingleSimple(HSSFWorkbook wb, ZsCrossExport zsCrossExport) {
        HSSFCellStyle style = createCellStyle(wb);
        ZsQuestion questionF = zsQuestionService.selectById(zsCrossExport.getFirstSelect());
        ZsQuestion questionS = zsQuestionService.selectById(zsCrossExport.getSecondSelect());
        List<ZsOption> optionListF = getAllOption(zsCrossExport.getFirstOption(), zsCrossExport.getFirstSelect());
        List<ZsOption> optionListS = getAllOption(zsCrossExport.getSecondOption(), zsCrossExport.getSecondSelect());
        HSSFSheet sheet = wb.createSheet("Q" + questionF.getOrderNum() + "--Q" + questionS.getOrderNum());
        HSSFRow rowQ1 = sheet.createRow(0);
        fillCell(rowQ1.createCell(0), createHeadStyle(wb), "Q" + questionF.getOrderNum() + "." + questionF.getName());
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2 * optionListF.size() + 3));
        List optionStrs = getCrossFeild(optionListF);
        fillRow(sheet.createRow(2), style, optionStrs, 2, true);
        HSSFRow rowQ2 = sheet.createRow(3);
        fillCell(rowQ2.createCell(0), createHeadStyle(wb), "Q" + questionS.getOrderNum() + "." + questionS.getName());
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 2 * optionListF.size() + 3));
        Long totalNum = 0L;
        for (ZsOption os : optionListS) {
            int indexS = optionListS.indexOf(os);
            List<Map<String, Object>> results = zsAnswerItemService.selectCrossCounts(questionF, questionS, os, zsCrossExport.getSurveyId());
            HSSFRow row = sheet.createRow(5 + 2 * optionListS.indexOf(os));
            HSSFRow rowSpace = sheet.createRow(5 + 2 * optionListS.indexOf(os) + 1);
            setDefaultStyle(row, style, optionListF.size() * 2 + 4);
            setDefaultStyle(rowSpace, style, optionListF.size() * 2 + 4);
            fillCell(row.createCell(0), style, os.getName());
            Long totalX = 0L;
            for (Map<String, Object> res : results) {
                for (ZsOption option : optionListF) {
                    int index = optionListF.indexOf(option);
                    if (option.getName().equals(res.get("option").toString())) {
                        if (index > -1) {
                            fillCell(row.createCell(2 + 2 * index), style, res.get("count").toString());
                            totalX += (Long) res.get("count");
                        }
                        option.setCount(option.getCount() + ((Long) res.get("count")).intValue());
                    }
                }
            }

            totalNum += totalX;
            fillCell(row.createCell(2 + 2 * optionListF.size()), style, totalX.toString());
            sheet.addMergedRegion(new CellRangeAddress(5 + 2 * indexS, 6 + 2 * indexS, 2 * optionListF.size() + 2, 2 * optionListF.size() + 3));
            row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), rowSpace.getRowNum(), 0, 1));
            for(int i=0;i<optionListF.size();i++){
                row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), rowSpace.getRowNum(), 2 + 2 * i, 3 + 2 * i));
            }
        }

        HSSFRow total = sheet.createRow(5 + optionListS.size() * 2);
        HSSFRow totalPlus = sheet.createRow(6 + optionListS.size() * 2);
        setDefaultStyle(total, style, optionListF.size() * 2 + 4);
        setDefaultStyle(totalPlus, style, optionListF.size() * 2 + 4);
        fillCell(total.createCell(0), style, "合计");
        for (ZsOption option : optionListF) {
            int index = optionListF.indexOf(option);
            fillCell(total.createCell(2 + 2 * optionListF.indexOf(option)), style, option.getCount());
            sheet.addMergedRegion(new CellRangeAddress(5 + 2 * optionListS.size(), 6 + 2 * optionListS.size(), 2 + 2 * index, 3 + 2 * index));
        }
        fillCell(total.createCell(optionListF.size() * 2 + 2), style, totalNum.intValue());
        sheet.addMergedRegion(new CellRangeAddress(5 + 2 * optionListS.size(), 6 + 2 * optionListS.size(), 2 * optionListF.size() + 2, 2 * optionListF.size() + 3));
        total.getSheet().addMergedRegion(new CellRangeAddress(total.getRowNum(), totalPlus.getRowNum(), 0, 1));

    }

    void fillValueStart(HSSFRow row, CellStyle style, String value, Integer total, Integer start, Integer step) {
        for (int i = 0; i < total; i++) {
            HSSFCell cell = row.createCell(start + step * i);
            cell.setCellStyle(style);
            cell.setCellValue(value);
            row.createCell(start + step * i + 1).setCellStyle(style);
        }
    }

    Integer getSum(Integer[] arr) {
        Integer result = 0;
        for (Integer i : arr) {
            result += i;
        }
        return result;
    }

    Integer[] getVerTotal(ZsQuestion questionF, ZsQuestion questionS, List<ZsOption> optionListF, List<ZsOption> optionListS, Integer surveyId) {
        Integer[] s = new Integer[optionListF.size()];
        for (int i = 0; i < optionListS.size(); i++) {
            for (int j = 0; j < optionListF.size(); j++) {
                Map<String, Object> countMap = zsAnswerItemService.selectCrossCount(questionF, questionS, optionListF.get(j), optionListS.get(i), surveyId);
                Integer count = ((Long) countMap.get("count")).intValue();
                if (ObjectUtils.isEmpty(s[j])) {
                    s[j] = count;
                } else {
                    s[j] += count;
                }
            }
        }
        return s;
    }

    void fillRowStart(HSSFRow row, CellStyle style, List<Integer> list, Integer start, Integer step) {
        Integer sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        for (Integer i : list) {
            HSSFCell cell = row.createCell(start + step * list.indexOf(i));
            cell.setCellStyle(style);
            if (sum > 0) {
                cell.setCellValue(df.format(i * 0.1 / sum * 10));
            } else {
                cell.setCellValue(df.format(0));
            }
        }
    }

    void fillRowStrStart(HSSFRow row, CellStyle style, List<String> list, Integer start) {
        for (String i : list) {
            HSSFCell cell = row.createCell(start + list.indexOf(i));
            cell.setCellStyle(style);
            cell.setCellValue(i);
        }
    }

    /**
     * 获取列表标题
     *
     * @param options
     * @return
     */
    public List<String> getCrossFeild(List<ZsOption> options) {
        List<String> result = new ArrayList<>();
        result.add("");
        options.forEach(o -> {
            result.add(o.getName());
        });
        result.add("合计");
        return result;
    }

    /**
     * 多表交叉导出
     *
     * @param wb
     */
    public void crossMuilty(HSSFWorkbook wb, ZsCrossExport crossExport) {
        List<ZsQuestion> questions = zsQuestionService.selectBySurveyId(crossExport.getSurveyId());
        ZsQuestion questionF = zsQuestionService.selectById(crossExport.getFirstSelect());
        for (ZsQuestion question : questions) {
            if (question.getId().equals(questionF.getId())) {
                continue;
            }
//            需要设置一下qF qS oF oS
            crossExport.setSecondSelect(question.getId());
            crossExport.setSecondOption(null);
            crossSingle(wb, crossExport);
        }
    }

    /**
     * 多表交叉导出不带%
     *
     * @param wb
     */
    public void crossMuiltySimple(HSSFWorkbook wb, ZsCrossExport crossExport) {
        List<ZsQuestion> questions = zsQuestionService.selectBySurveyId(crossExport.getSurveyId());
        ZsQuestion questionF = zsQuestionService.selectById(crossExport.getFirstSelect());
        for (ZsQuestion question : questions) {
            if (question.getId().equals(questionF.getId())) {
                continue;
            }
//            需要设置一下qF qS oF oS
            crossExport.setSecondSelect(question.getId());
            crossExport.setSecondOption(null);
            crossSingleSimple(wb, crossExport);
        }
    }

    /**
     * 通过选项id 或者 问题id获取选项
     *
     * @param optionIds
     * @param questionId
     * @return
     */
    List<ZsOption> getAllOption(List<Integer> optionIds, Integer questionId) {
        if (!CollectionUtils.isEmpty(optionIds)) {
            List<ZsOption> optionList = zsOptionService.selectBatchIds(optionIds);
            if (optionList.size() > 0) {
                return optionList;
            }
        }
        return zsOptionService.selectByQuestionId(questionId);
    }

//    void fillRow(HSSFRow row, HSSFCellStyle style, List<String> values) {
//        Integer index = 0;
//        for (String value : values) {
//            fillCell(row.createCell(index++), style, value);
//        }
//    }

    void fillRow(HSSFRow row, HSSFCellStyle style, List<Object> values) {
        Integer index = 0;
        for (Object value : values) {
            if (value instanceof Integer) {
                fillCell(row.createCell(index++), style, (Integer) value);
            } else if (value instanceof String) {
                fillCell(row.createCell(index++), style, (String) value);
            } else {
                fillCell(row.createCell(index++), style, value.toString());
            }
        }
    }

    void fillRow(HSSFRow row, HSSFCellStyle style, List<Object> values, Integer step) {
        Integer index = 0;
        for (Object value : values) {
            if (value instanceof Integer) {
                fillCell(row.createCell(step * index), style, (Integer) value);
            }
            if (value instanceof String) {
                fillCell(row.createCell(step * index), style, (String) value);
            }
            row.createCell(step * index + 1).setCellStyle(style);
            index++;
        }
    }

    void fillRow(HSSFRow row, HSSFCellStyle style, List<Object> values, Integer step, Boolean merge) {
        Integer index = 0;
        for (Object value : values) {
            if (merge) {
                row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), step * index, step * index + step - 1));
            }
            if (value instanceof Integer) {
                fillCell(row.createCell(step * index), style, (Integer) value);
            }
            if (value instanceof String) {
                fillCell(row.createCell(step * index), style, (String) value);
            }
            row.createCell(step * index + 1).setCellStyle(style);
            index++;
        }
    }

    HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        return style;
    }

    HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
//        style.setFillForegroundColor(HSSFColor.LIME.index);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    HSSFCellStyle createCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public HSSFCell fillCell(HSSFCell cell, HSSFCellStyle style, String value) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

    public HSSFCell fillCell(HSSFCell cell, HSSFCellStyle style, Integer value) {
        value = ObjectUtils.isEmpty(value) ? 0 : value;
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

    List<Map<String, Object>> selectRateBySurveyQuestion(Integer id, String question) {
        return zsAnswerMapper.selectRateBySurveyQuestion(id, question);
    }

    public Integer selectAnswerItemCount(Integer surveyId, Integer qId, String option) {
        Wrapper<ZsAnswerItem> answerItemWrapper = new EntityWrapper<>();
        answerItemWrapper.eq("survey_id", surveyId);
        answerItemWrapper.eq("question_id", qId);
        answerItemWrapper.eq("`option`", option);
        answerItemWrapper.eq("`valid`", 1);
        answerItemWrapper.groupBy("`option`");
        return zsAnswerItemService.selectCount(answerItemWrapper);
    }
}
