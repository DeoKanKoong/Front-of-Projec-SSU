package com.example.softproject1.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }
    @GetMapping("/loginto")
    public String indextologin(Model model) {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("id",id);
        return "indextologin";
    }
}
