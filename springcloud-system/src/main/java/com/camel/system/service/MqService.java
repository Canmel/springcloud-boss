package com.camel.system.service;

import javax.jms.JMSException;

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
 * <Mq>
 * @author baily
 * @since 1.0
 * @date 2019/9/19
 **/
public interface MqService {
    /**
     * 发送消息
     * @param msg
     * @return
     * @throws JMSException
     */
    boolean sendMsg(String msg) throws JMSException;

    /**
     * 发送
     * @param msg
     * @return
     */
    boolean send(String msg);

    /**
     * 发送消息
     * @param msg
     * @return
     */
    boolean sendForNotice(String msg);
}
