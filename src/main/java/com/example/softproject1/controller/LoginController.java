package com.example.softproject1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginP() {

        return "login";
    }
    @GetMapping("/ForgottenId")
    public String forgetId() {

        return "ForgottenId";
    }
    @GetMapping("/ForgottenPw")
    public String forgetP() {

        return "ForgottenPw";
    }


}