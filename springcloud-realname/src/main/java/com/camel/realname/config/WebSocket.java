package com.camel.realname.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/webSocket")
@Component
@Slf4j
public class WebSocket {
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    /**
     * 建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
//        log.info("【新建连接】，连接总数:{}", webSockets.size());
        System.out.println(MessageFormat.format("【新建连接】，连接总数:{0}", webSockets.size()));
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose(){
        webSockets.remove(this);
//        log.info("【断开连接】，连接总数:{}", webSockets.size());
        System.out.println(MessageFormat.format("【断开连接】，连接总数:{0}", webSockets.size()));
    }

    /**
     * 接收到信息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
//        log.info("【收到】，客户端的信息:{}，连接总数:{}", message, webSockets.size());
        System.out.println(MessageFormat.format("【收到】，客户端的信息:{0}，连接总数:{1}",message, webSockets.size()));
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message){
//        log.info("【广播发送】，信息:{}，总连接数:{}", message, webSockets.size());
        System.out.println(MessageFormat.format("【广播发送】，信息:{0}，总连接数:{1}", message, webSockets.size()));
        for (WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                System.out.println(MessageFormat.format("【广播发送】，信息异常:{0}", e.fillInStackTrace()));
            }
        }
    }

    /**
     * 发生错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
        System.out.println("发生错误");
        error.printStackTrace();
    }
}
