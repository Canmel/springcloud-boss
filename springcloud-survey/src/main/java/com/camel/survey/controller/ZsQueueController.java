package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.model.RelSeatQueue;
import com.camel.survey.model.ZsQueue;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.RelSeatQueueService;
import com.camel.survey.service.ZsQueueService;
import com.camel.survey.service.ZsSeatService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/zsQueue")
public class ZsQueueController extends BaseCommonController {

    @Autowired
    private ZsQueueService service;

    @Autowired
    private ZsSeatService seatService;

    @Autowired
    private RelSeatQueueService seatQueueService;

    @GetMapping
    public Result index(ZsQueue entity) {
        return ResultUtil.success(service.selectPage(entity));
    }

    @GetMapping("/all")
    public Result all() {
        return ResultUtil.success(service.selectList(new EntityWrapper<ZsQueue>()));
    }

    @PostMapping("/add/{id}")
    @Transactional
    public Result add(String ids, @PathVariable Integer id) {
        String[] idsArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String idstr: idsArray) {
            try {
                idList.add(Integer.parseInt(idstr));
            }catch (NumberFormatException e) {
                throw new SurveyNotValidException();
            }
        }

        seatService.clearQueue(id);

        List<ZsSeat> seats = seatService.selectBatchIds(idList);
        for (ZsSeat seat: seats) {
            seat.setQueueId(id);
        }
        seatService.updateBatchById(seats);
        return ResultUtil.success("操作完成");
    }

    @PostMapping
    public Result save(ZsQueue entity) {
        return super.save(entity);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "队列";
    }
}

