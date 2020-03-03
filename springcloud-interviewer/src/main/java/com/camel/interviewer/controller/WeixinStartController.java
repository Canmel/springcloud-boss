package com.camel.interviewer.controller;

import com.camel.interviewer.annotation.AuthIgnore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class WeixinStartController {

    @AuthIgnore
    @GetMapping
    private String start(String signature, String timestamp, String nonce, String echostr) {
        return echostr;
    }
}
