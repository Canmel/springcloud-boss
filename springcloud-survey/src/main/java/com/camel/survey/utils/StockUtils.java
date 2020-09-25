package com.camel.survey.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUtils {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void reduceCurrent(Integer surveyId, List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("surveyId", surveyId);
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Reduce.Topic"), json);
    }

    public void reduceCurrent(List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Reduce.Topic"), json);
    }

    public void addCurrent(Integer surveyId, List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("surveyId", surveyId);
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Add.Topic"), json);
    }

    public void addCurrent(List<Integer> optIds) {
        JSONObject json = new JSONObject();
        json.put("optId", optIds);
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("ActiveMQ.Stock.Add.Topic"), json);
    }

    /**
     * 要增加和减少的
     * @param reduceList
     * @param addList
     */
    public void changeOption(List<Integer> reduceList, List<Integer> addList) {
        addCurrent(addList);
        reduceCurrent(reduceList);
    }
}
