package com.camel.survey.utils.export;

import com.camel.survey.model.ZsAnswer;
import com.camel.survey.service.ZsAnswerService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnswerFormExportUtil {

    @Autowired
    private ZsAnswerService answerService;

    @Autowired
    private AnswerListExport answerListExport;

    @Autowired
    private OptionExport optionExport;

    public HSSFWorkbook export(ZsAnswer zsAnswer) {

        List<ZsAnswer> answers = answerService.list(zsAnswer);
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            answerListExport.list(wb, zsAnswer);
            optionExport.options(wb, zsAnswer);
            return wb;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
