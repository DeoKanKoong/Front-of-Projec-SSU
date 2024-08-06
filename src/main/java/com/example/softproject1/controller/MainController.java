package com.example.softproject1.controller;

import com.example.softproject1.dto.CustomUserDetails;
import com.example.softproject1.entity.Article;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.ArticleRepository;
import com.example.softproject1.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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
    public String showHomePage(Model model, HttpSession session) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            String username = userDetails.getUsername();
            UserEntity user = userRepository.findByUsername(username);

            // 세션에 사용자 정보 저장
            session.setAttribute("username", user.getUsername());
            session.setAttribute("name", user.getName());
            session.setAttribute("department", user.getDepartment());

            return "MainCommunity"; // 메인 화면 리다이렉션
        }

        return "MainCommunity"; // 로그인 페이지로 리다이렉션
    }

    @GetMapping("/loginto")
    public String indextologin(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username);

        model.addAttribute("name", user.getName());
        model.addAttribute("department", user.getDepartment());
        return "MainCommunity";
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
