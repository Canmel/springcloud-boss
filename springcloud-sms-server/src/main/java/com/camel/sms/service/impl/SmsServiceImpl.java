package com.camel.sms.service.impl;

import com.camel.sms.service.SmsService;
import com.camel.sms.utils.LhzxHttpClientUtil;
import org.springframework.stereotype.Service;

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
 * <短信服务实现>
 * @author baily
 * @since 1.0
 * @date 2019/9/19
 **/
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public boolean send(String target, String content) {
        LhzxHttpClientUtil client = LhzxHttpClientUtil.getInstance();
        client.send(target, content);
        return false;
    }
}
