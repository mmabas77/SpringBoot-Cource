package com.mmabas77.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayloadController {
    public static final String PAYLOAD_VIEW = "payload/payload";
    @RequestMapping("/payload")
    public String getPayload(){
        return PAYLOAD_VIEW;
    }
}
