package com.example.softproject1.controller;

import com.example.softproject1.dto.CustomUserDetails;
import com.example.softproject1.entity.Article;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.ArticleRepository;
import com.example.softproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            String username = userDetails.getUsername();
            UserEntity user = userRepository.findByUsername(username);

            model.addAttribute("name", user.getName());
            model.addAttribute("department", user.getDepartment());
            return "indextologin";
        }

        return "MainCommunity";
    }

    @GetMapping("/loginto")
    public String indextologin(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username);

        model.addAttribute("name", user.getName());
        model.addAttribute("department", user.getDepartment());
        return "indextologin";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username);

        model.addAttribute("user", user);
        return "profile";
    }
}