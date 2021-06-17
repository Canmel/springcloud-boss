package com.camel.realname.controller;

import com.camel.realname.annotation.AuthIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("")
    @AuthIgnore
    public String index() {
        return "index"  ;
    }


}
