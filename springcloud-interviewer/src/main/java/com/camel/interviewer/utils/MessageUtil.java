package com.camel.interviewer.utils;

import com.camel.interviewer.entity.wx.ImageText;
import com.camel.interviewer.entity.wx.ImageTextMessage;
import com.camel.interviewer.entity.wx.TextMessage;
import com.jfinal.weixin.sdk.msg.out.OutImageMsg;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageUtil {
    public static final String MSGTYPE_EVENT = "event";//消息类型--事件
    public static final String MESSAGE_SUBSCIBE = "subscribe";//消息事件类型--订阅事件
    public static final String MESSAGE_UNSUBSCIBE = "unsubscribe";//消息事件类型--取消订阅事件
    public static final String MESSAGE_TEXT = "text";//消息类型--文本消息

    /*
     * 组装文本消息
     */
    public static String textMsg(String toUserName,String fromUserName,String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return XmlUtil.textMsgToxml(text);
    }

    /*
     * 响应订阅事件--回复文本消息
     */
    public static String subscribeForImageText(String toUserName,String fromUserName){
        String appId = "wx0a2efc77aac2a84b";
        List<ImageText> list = new ArrayList<>();
        ImageTextMessage imageTextMessage = new ImageTextMessage();
        ImageText imageTextItem = new ImageText();
        imageTextItem.setTitle("欢迎关注赛炜调查");
        imageTextItem.setDescription("进行操作之前请先完善个人资料");
        imageTextItem.setPicUrl("https://diaocha.svdata.cn/viewer/wx/img/share04.png");
        imageTextItem.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri=https://diaocha.svdata.cn/viewer/view/profile&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        list.add(imageTextItem);

        imageTextMessage.setFromUserName(fromUserName);
        imageTextMessage.setToUserName(toUserName);
        imageTextMessage.setCreateTime(new Date().getTime());
        imageTextMessage.setMsgType("event");
        imageTextMessage.setArticleCount(1);
        imageTextMessage.setArticles(list);
        return ImageTextMessageToXml(imageTextMessage);
    }

    /*
     * 响应取消订阅事件
     */
    public static String unsubscribe(String toUserName,String fromUserName){
        //TODO 可以进行取关后的其他后续业务处理
        System.out.println("用户："+ fromUserName +"取消关注~");
        return "";
    }

    /**
     * 图文消息转XML结构方法
     */
    public static String ImageTextMessageToXml(ImageTextMessage message) {
        XStream xs = new XStream();
        //由于转换后xml根节点默认为class类，需转化为<xml>
        xs.alias("xml", message.getClass());
        xs.alias("item", new ImageText().getClass());
        return xs.toXML(message);
    }
}
