package com.camel.survey.service.impl;

import com.camel.survey.service.MqService;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Topic;

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
 * <Mq实现>
 * @author baily
 * @since 1.0
 * @date 2019/9/19
 **/
@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    @Override
    public boolean sendMsg(String msg) {
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Log.Topic"), msg);
        return true;
    }

    @Override
    public boolean send(String msg) {
        this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
        return true;
    }

    @Override
    public boolean sendForNotice(String msg) {
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Notice.Topic"), msg);
        return true;
    }
}
