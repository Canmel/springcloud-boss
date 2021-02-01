package com.camel.survey.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.exceptions.SurveyNotValidException;
import com.camel.survey.model.ZsQueue;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.model.cti.Queue;
import com.camel.survey.model.cti.QueueVO;
import com.camel.survey.service.RelSeatQueueService;
import com.camel.survey.service.ZsQueueService;
import com.camel.survey.service.ZsSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    @GetMapping("/ivrs")
    public Result ivrs() {
        return ResultUtil.success(service.selectIVRS());
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

    /**
     * 同步队列信息
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/doSync")
    public Result syncQueue() throws UnsupportedEncodingException {
        service.syncQueue();
        return ResultUtil.success("同步队列成功");
    }

    /**
     * 将本地队列推送到CTI
     * @param id
     * @return
     */
    @GetMapping("/push/{id}")
    public Result push(@PathVariable Integer id) {
        ZsQueue queue = service.selectById(id);
        service.push(queue);
        return ResultUtil.success("提交队列成功");
    }

    /**
     * 拉取CTI队列
     * @param id
     * @return
     */
    @GetMapping("/pull/{id}")
    public Result pull(@PathVariable Integer id) {
        ZsQueue queue = service.selectById(id);
        service.pull(queue);
        return ResultUtil.success("拉取队列成功");
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

