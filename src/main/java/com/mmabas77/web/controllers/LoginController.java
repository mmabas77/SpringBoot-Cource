package com.mmabas77.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    public static final String LOGIN_VIEW = "user/login";

    @RequestMapping("/login")
    public String getLogin() {
        return LOGIN_VIEW;
    }
}
