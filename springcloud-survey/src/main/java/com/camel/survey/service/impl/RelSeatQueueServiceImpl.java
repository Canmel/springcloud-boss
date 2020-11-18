package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.survey.mapper.ZsQueueMapper;
import com.camel.survey.mapper.ZsSeatMapper;
import com.camel.survey.model.RelSeatQueue;
import com.camel.survey.mapper.RelSeatQueueMapper;
import com.camel.survey.model.ZsQueue;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.RelSeatQueueService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@Service
public class RelSeatQueueServiceImpl extends ServiceImpl<RelSeatQueueMapper, RelSeatQueue> implements RelSeatQueueService {

    @Autowired
    private RelSeatQueueMapper mapper;

    @Autowired
    private ZsQueueMapper queueMapper;

    @Autowired
    private ZsSeatMapper seatMapper;
}
