package com.camel.survey.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.core.utils.PaginationUtil;
import com.camel.survey.exceptions.SourceDataNotValidException;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.model.ZsOption;
import com.camel.survey.model.ZsQueue;
import com.camel.survey.mapper.ZsQueueMapper;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.model.cti.Queue;
import com.camel.survey.service.ZsQueueService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.survey.service.ZsSeatService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@Service
public class ZsQueueServiceImpl extends ServiceImpl<ZsQueueMapper, ZsQueue> implements ZsQueueService {
    @Autowired
    private ZsQueueMapper mapper;

    @Autowired
    private ZsSeatService seatService;

    @Override
    public PageInfo<ZsOption> selectPage(ZsQueue entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    @Transactional
    public void syncQueue(List<Queue> queueList) {
        for (Queue q : queueList) {
            Wrapper<ZsQueue> queueWrapper = new EntityWrapper<>();
            queueWrapper.eq("name", q.queueName);

            Integer c = mapper.selectCount(queueWrapper);
            if (c < 1) {
                mapper.insert(new ZsQueue(q.getQueueName(), q.getQueueNum()));
            }

        }
    }

    @Override
    public void syncQueue() throws UnsupportedEncodingException {
        String resp = "";
        try {
            JSONObject jsonObject = JSONUtil.createObj();
            resp = HttpUtil.createPost("http://tj.svdata.cn/yscrm/v2/infs/getQueueAgentInfo.json").header("Content-Type", "application/json").body(jsonObject.toString(), "application/json").execute().body();
            JSONObject respObject = JSONUtil.parseObj(resp);
            JSONArray array = respObject.getJSONArray("info");
            for (int i = 0; i < array.size(); i++) {
                JSONObject item = (JSONObject) array.get(i);
                String queueNum = item.getStr("queueNum");
                String queueName = item.getStr("queueName");
                Wrapper<ZsQueue> queueWrapper = new EntityWrapper<>();
                queueWrapper.eq("name", queueName);
                if(mapper.selectCount(queueWrapper) < 1) {
                    mapper.insert(new ZsQueue(queueName, queueNum));
                }
//                JSONArray agentArray = item.getJSONArray("agents");
//                for (int j = 0; j < agentArray.size(); j++) {
//                    JSONObject object = (JSONObject) agentArray.get(j);
//
//                }
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new SourceDataNotValidException("同步队列信息失败");
        }
    }

    @Override
    public void push(ZsQueue queue) {
        Wrapper<ZsSeat> seatWrapper = new EntityWrapper<>();
        seatWrapper.eq("queue_id", queue.getId());
        List<ZsSeat> seats = seatService.selectList(seatWrapper);
        List<String> paramsSeatNums = seats.stream().map(ZsSeat::getSeatNum).collect(Collectors.toList());
        String resp = "";
        com.alibaba.fastjson.JSONObject params = new com.alibaba.fastjson.JSONObject();
        if(ObjectUtil.isEmpty(queue.getNum())) {
            throw new SourceDataNotValidException("队列暂无CTI信息");
        }
        params.put("queueNum", queue.getNum());
        resp = HttpUtil.createPost("http://tj.svdata.cn/yscrm/v2/infs/getQueueAgentInfo.json").header("Content-Type", "application/json").body(params.toJSONString(), "application/json").execute().body();
        JSONObject respObject = JSONUtil.parseObj(resp);
        JSONObject q = respObject.getJSONArray("info").getJSONObject(0);
        JSONArray agents = q.getJSONArray("agents");
        List<String> agentList = new ArrayList<>();
        for (int i = 0; i < agents.size(); i++) {
            JSONObject agent = (JSONObject) agents.get(i);
            agentList.add(agent.getStr("agentnum"));
        }
        params.put("operType", "2");
        params.put("agentNum", String.join(",", agentList));
        String deleteResult = HttpUtil.createPost("http://tj.svdata.cn/yscrm/v2/infs/setQueueAgentInfo.json").header("Content-Type", "application/json").body(params.toJSONString(), "application/json").execute().body();
        System.out.println(deleteResult);
        JSONObject deleteResultObject = JSONUtil.parseObj(deleteResult);
        if("000000".equals(deleteResultObject.getStr("statuscode"))) {
            params = new com.alibaba.fastjson.JSONObject();
            params.put("queueNum", queue.getNum());
            params.put("operType", "1");
            params.put("agentNum", String.join(",", paramsSeatNums  ));
            String r = HttpUtil.createPost("http://tj.svdata.cn/yscrm/v2/infs/setQueueAgentInfo.json").header("Content-Type", "application/json").body(params.toJSONString(), "application/json").execute().body();
            System.out.println(r);
        }
    }

    @Override
    public void pull(ZsQueue queue) {
        String resp = "";
        com.alibaba.fastjson.JSONObject params = new com.alibaba.fastjson.JSONObject();
        if(ObjectUtil.isEmpty(queue.getNum())) {
            throw new SourceDataNotValidException("队列暂无CTI信息");
        }
        params.put("queueNum", queue.getNum());
        resp = HttpUtil.createPost("http://tj.svdata.cn/yscrm/v2/infs/getQueueAgentInfo.json").header("Content-Type", "application/json").body(params.toJSONString(), "application/json").execute().body();
        JSONObject respObject = JSONUtil.parseObj(resp);
        JSONObject q = respObject.getJSONArray("info").getJSONObject(0);
        JSONArray agents = q.getJSONArray("agents");
        seatService.clearQueue(queue.getId());
        for (int i = 0; i < agents.size(); i++) {
            JSONObject agent = (JSONObject) agents.get(i);
            ZsSeat seat = seatService.selectBySeat(agent.getStr("agentnum"));
            seat.setQueueId(queue.getId());
            seatService.insertOrUpdate(seat);
        }
    }
}
