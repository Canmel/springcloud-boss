package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.activiti.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/role/all")
    public Result allRole(){
        return feignService.allRole();
    }

    /**
     * 查询指定角色的用户
     * @param id
     * @return
     */
    @GetMapping("/user/role/{id}")
    public Result usersByRoleId(@PathVariable Integer id){
        return feignService.usersByRole(id);
    }
}
