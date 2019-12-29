package com.camel.survey.vo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

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
 * <回答详细>
 * @author baily
 * @since 1.0
 * @date 2019/12/17
 **/
@Data
public class ZsAnswerItemSave {
    private Integer qId;

    private Integer optId;

    private String name;

    private String value;

    public String getValue() {
        String[] nameArr = null;
        if(StringUtils.isNotBlank(this.value)) {
            nameArr = this.value.split("_");
        }
        if (!ObjectUtils.isEmpty(nameArr) && nameArr.length == 2) {
            return nameArr[1];
        }
        return value;
    }

    public Integer getqId() {
        String[] nameArr = null;
        if (StringUtils.isNotBlank(this.name)) {
            nameArr = this.name.split("_");
        }
        if (!ObjectUtils.isEmpty(nameArr) && nameArr.length == 3) {
            return Integer.parseInt(nameArr[1]);
        }
        return qId;
    }

    public Integer getOId() {
        String[] nameArr = null;
        if (StringUtils.isNotBlank(this.value)) {
            nameArr = this.value.split("_");
        }
        if (!ObjectUtils.isEmpty(nameArr) && nameArr.length == 2) {
            return Integer.parseInt(nameArr[0]);
        }
        return optId;
    }
}
