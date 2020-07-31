package com.camel.interviewer.controller;

import com.camel.interviewer.annotation.AuthIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view")
@Controller
public class WeixinViewController {
    @AuthIgnore
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @AuthIgnore
    @GetMapping("/salary")
    public String salary() {
        return "salary";
    }

    @AuthIgnore
    @GetMapping("/appointment")
    public String appointment() {
        return "appointment";
    }

    @AuthIgnore
    @GetMapping("/share")
    public String share() {
        return "share";
    }
}
