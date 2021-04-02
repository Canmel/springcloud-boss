package com.camel.interviewer.controller;

import com.camel.interviewer.annotation.AuthIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @AuthIgnore
    @GetMapping("/report")
    public String report() {
        return "report";
    }

    @AuthIgnore
    @GetMapping("/recommend")
    public String recommend() {
        return "recommend";
    }

    @AuthIgnore
    @GetMapping("/signIn")
    public ModelAndView signIn(String zc) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signIn");
        modelAndView.addObject("zc", zc);
        return modelAndView;
    }
}
