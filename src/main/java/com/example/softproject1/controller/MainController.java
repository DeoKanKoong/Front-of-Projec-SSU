package com.example.softproject1.controller;

import com.example.softproject1.dto.CustomUserDetails;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
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
}