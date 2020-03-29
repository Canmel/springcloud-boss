package com.camel.survey.vo;

import com.camel.survey.exceptions.SurveyFormSaveException;
import com.camel.survey.model.ZsAnswer;
import com.camel.survey.model.ZsAnswerItem;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQuestion;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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
 * <回答的总集>
 * @author baily
 * @since 1.0
 * @date 2019/12/17
 **/
@Data
public class ZsAnswerSave {

    /**
     * 调查详细
     */
    public List<ZsAnswerItemSave> zsAnswerItemSaves;

    /**
     * 所属调查
     */
    public Integer surveyId;

    /**
     * 电话
     */
    public String phone;

    /**
     * 坐席
     */
    public String seat;

    /**
     * 录音位置
     */
    public String record;

    /**
     * 获取选项ID
     * @return
     */
    public List<Integer> getOptIds() {
        List<Integer> oIds = new ArrayList<>();
        zsAnswerItemSaves.forEach(zsAnswerItemSave -> {
            oIds.add(zsAnswerItemSave.getOId());
        });
        return oIds;
    }

    public ZsAnswer buildAnswer() {
        ZsAnswer answer = new ZsAnswer(this.surveyId, this.phone);
        answer.setSeat(ObjectUtils.isEmpty(this.seat) ? "0" : this.seat);
        answer.setRecord(this.record);
        return answer;
    }

    public ZsAnswerItem buildAnswerItem(ZsQuestion question, ZsOption zsOption, Integer answerId, String value) {
        ZsAnswerItem zsAnswerItem = new ZsAnswerItem(question.getName(), ObjectUtils.isEmpty(zsOption) ? "" : zsOption.getName(), answerId, value, question.getType(), this.phone);
        zsAnswerItem.setSurveyId(surveyId);
        zsAnswerItem.setQuestionId(question.getId());
        zsAnswerItem.setZsOption(zsOption);
        return zsAnswerItem;
    }

    /**
     * 构建所有回答
     * @param zsQuestionList    根据问卷所查出来的所有问题
     * @param optionList        根据问卷所查出来的所有选项
     * @param answerId          回答答案的主键
     * @return
     */
    public List<ZsAnswerItem> buildAnswerItems(List<ZsQuestion> zsQuestionList, List<ZsOption> optionList, Integer answerId) {
        List<ZsAnswerItem> result = new ArrayList<>();

        zsAnswerItemSaves.forEach(answerItem -> {
            ZsQuestion question = null;
            ZsOption zsOption = null;
            for (ZsQuestion q: zsQuestionList) {
                if(q.getId().equals(answerItem.getqId())) {
                    question = q;
                }
            }

            for (ZsOption option: optionList) {
                if(option.getName().equals(answerItem.getValue()) && option.getQuestionId().equals(answerItem.getqId())){
                    zsOption = option;
                }
            }
            if(ObjectUtils.isEmpty(zsOption)) {
                for (ZsOption option: optionList) {
                    if(option.getHasRemark() && option.getQuestionId().equals(answerItem.getqId()) && option.getId().equals(answerItem.getOId())) {
                        zsOption = option;
                    }
                }
            }
            result.add(buildAnswerItem(question, zsOption, answerId, answerItem.getValue()));
        });

        return result;
    }
}
