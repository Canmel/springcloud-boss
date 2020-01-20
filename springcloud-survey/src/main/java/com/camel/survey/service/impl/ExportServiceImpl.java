package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.survey.enums.ZsStatus;
import com.camel.survey.exceptions.ExportFillDataException;
import com.camel.survey.mapper.ZsAnswerMapper;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import com.camel.survey.service.ExportService;
import com.camel.survey.service.ZsAnswerItemService;
import com.camel.survey.service.ZsOptionService;
import com.camel.survey.service.ZsQuestionService;
import com.camel.survey.vo.ZsCrossExport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <导出>
 * @author baily
 * @since 1.0
 * @date 2019/12/24
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

    public static final Integer SELECT_STEP = 100;

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
    public HSSFWorkbook seat(Integer id) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("坐席收集样本统计");
        HSSFCellStyle headStyle = createHeadStyle(wb);
        HSSFCellStyle style = createCellStyle(wb);
        List<Object> v = new ArrayList<>();
        v.add("坐席");
        v.add("问题");
        v.add("样本");
        fillRow(sheet.createRow(0), headStyle, v);
        List<Map<String, Object>> result = zsAnswerItemService.selectSeatTotal(id);
        for (Map<String, Object> map: result) {
            List<Object> rowValue = new ArrayList<>();
            rowValue.add(map.get("seat"));
            rowValue.add(map.get("questionNum"));
            rowValue.add(map.get("surveyNum"));
            fillRow(sheet.createRow(result.indexOf(map) + 1), style, rowValue);
        }
        return wb;
    }

    @Override
    public HSSFWorkbook answer(Integer surveyId) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("样本明细");
        HSSFRow head = sheet.createRow(0);
        HSSFCellStyle headStyle = createHeadStyle(wb);
        HSSFCellStyle style = createCellStyle(wb);
        List<ZsQuestion> questionList = zsQuestionService.selectBySurveyId(surveyId);
        List<Object> headValues = CollectionUtils.arrayToList(new String[]{"时间", "电话", "坐席"});
        List<Object> list = new ArrayList<>(headValues);
        questionList.forEach(opt -> {
            list.add(opt.getName());
        });
        list.add("合计");
        fillRow(head, headStyle, list);
        List<Map<String, Object>> result = zsAnswerItemService.selectExport(surveyId);
        for (int i = 0; i < result.size(); i++) {
            int cellNum = 0;
            HSSFRow row = sheet.createRow(1 + i);
            fillCell(row.createCell(cellNum++), style, sf.format(result.get(i).get("created_at")));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("creator"));
            fillCell(row.createCell(cellNum++), style, (String) result.get(i).get("seat"));
            String opts = (String) result.get(i).get("opts");
            String[] options = opts.split("@##@", -1);

            String ques = "";
            ques = (String) result.get(i).get("questions");
            String[] questions = ques.split("@##@", -1);
            List<String> qs = CollectionUtils.arrayToList(questions);
            for (ZsQuestion question : questionList) {
                if (qs.indexOf(question.getName()) > -1) {
                    fillCell(row.createCell(questionList.indexOf(question) + 3), style, options[qs.indexOf(question.getName())]);
                }
            }
        }

        return wb;
    }

    @Override
    public HSSFWorkbook total(Integer surveyId) {
        //创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        List<ZsQuestion> questions = zsQuestionService.selectBySurveyId(surveyId);
        questions.forEach(question -> {
            HSSFCellStyle style = createCellStyle(wb);
            Wrapper<ZsOption> optionWrapper = new EntityWrapper<>();
            optionWrapper.eq("question_id", question.getId());
            optionWrapper.eq("status", ZsStatus.CREATED.getValue());
            List<ZsOption> optionList = zsOptionService.selectList(optionWrapper);
            List<Map<String, Object>> answers = selectRateBySurveyQuestion(surveyId, question.getName());
            Integer count = 0;

            HSSFSheet sheet = wb.createSheet("Q" + question.getOrderNum());
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            //创建单元格，并设置值表头 设置表头居中
            try {
                // 填充工作表
                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = row.createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                fillCell(cell, createHeadStyle(wb), "Q" + question.getOrderNum() + "." + question.getName());
                HSSFRow rowTitle = sheet.createRow(1);
                fillCell(rowTitle.createCell(0), createTitleStyle(wb), "");
                fillCell(rowTitle.createCell(1), createTitleStyle(wb), "选项");
                fillCell(rowTitle.createCell(2), createTitleStyle(wb), "个数");
                fillCell(rowTitle.createCell(3), createTitleStyle(wb), "百分比");

                for (int i = 0; i < optionList.size(); i++) {
                    Integer cellIndex = 0;
                    HSSFRow optionRow = sheet.createRow(2 + i);
                    fillCell(optionRow.createCell(cellIndex++), style, i + 1);
                    fillCell(optionRow.createCell(cellIndex++), style, optionList.get(i).getName());
                    for (Map<String, Object> answer : answers) {
                        if (count == 0) {
                            count = Integer.parseInt(answer.get("count").toString());
                        }
                        if (answer.get("option").equals(optionList.get(i).getName())) {
                            String num = answer.get("num").toString();
                            fillCell(optionRow.createCell(cellIndex++), style, Integer.parseInt(num));
                            fillCell(optionRow.createCell(cellIndex), style, (String) answer.get("rate"));
                        }
                    }
                    if (cellIndex < 3) {
                        fillCell(optionRow.createCell(cellIndex++), style, 0);
                        fillCell(optionRow.createCell(cellIndex), style, 0);
                    }
                }
                HSSFRow row6 = sheet.createRow(optionList.size() + 2);
                fillRow(sheet.createRow(optionList.size() + 2), style, CollectionUtils.arrayToList(new String[]{"合计", "", count.toString(), "100%"}));

            } catch (Exception ex) {
                throw new ExportFillDataException();
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

    public void setDefaultStyle(HSSFRow row, HSSFCellStyle style, Integer colum) {
        for (int i = 0; i < colum; i++) {
            row.createCell(i).setCellStyle(style);
        }
    }

    /**
     * 单一两表交叉导出
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
     * 通过选项id 或者 问题id获取选项
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
            }
            else if (value instanceof String) {
                fillCell(row.createCell(index++), style, (String) value);
            }
            else {
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
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        return style;
    }

    HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    HSSFCellStyle createCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
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
}
