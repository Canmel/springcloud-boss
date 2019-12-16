package com.camel.survey.service.impl;

import com.camel.survey.service.ZsSmsService;
import com.camel.survey.vo.ZsSendSms;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
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
 * <发短信>
 * @author baily
 * @since 1.0
 * @date 2019/12/16
 **/
@Service
public class ZsSmsServiceImpl implements ZsSmsService {
    
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public boolean send(ZsSendSms sms) {
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Sms.Topic"), sms);
        return true;
    }
}
