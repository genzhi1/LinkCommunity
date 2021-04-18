package com.yxf.linkcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Publish {

    @GetMapping ("/publish")
    public String publish(){
        return "publish";
    }
}
