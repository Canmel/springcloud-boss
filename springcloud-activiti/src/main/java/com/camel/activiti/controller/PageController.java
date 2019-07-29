package com.camel.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuruijie on 2017/5/12.
 */

@Controller
public class PageController {
    @GetMapping("editor")
    public String test() {
        return "/modeler";
    }

    @GetMapping("/")
    public String index(HttpServletResponse response) {
        return "/index.html";
    }

    @GetMapping("/error")
    public String error() {
        return "/error.html";
    }
}
