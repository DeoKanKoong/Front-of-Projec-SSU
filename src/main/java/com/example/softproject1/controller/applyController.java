package com.example.softproject1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class applyController {
    @GetMapping("/students/apply={articleId}")
    public String apply(@PathVariable("articleId") int articleId, Model model) {
        model.addAttribute("articleId", articleId);
        return "apply/createprofile";
    }
}
