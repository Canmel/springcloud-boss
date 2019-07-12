package com.camel.oa.controller;


import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.service.ImperfectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
@RestController
@RequestMapping("/imperfect")
public class ImperfectController {

    @Autowired
    private ImperfectService service;

    @GetMapping("/errand/valid/{errandId}")
    public Result valid(@PathVariable Integer errandId){
        return ResultUtil.success(service.valid(errandId));
    }

}

