package com.example.softproject1.controller;

import com.example.softproject1.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class VerificationController {

    private final EmailService emailService;

    @GetMapping("/verification")
    public String getVerificationPage() {
        return "verification"; // verification.html을 반환
    }

    @PostMapping("/sendVerificationEmail")
    public String sendVerificationEmail(@RequestParam("email") String email, Model model) {
        try {
            emailService.sendSimpleVerificationMail(email, LocalDateTime.now());
            model.addAttribute("message", "Verification email sent to " + email);
        } catch (Exception e) {
            model.addAttribute("message", "Failed to send verification email: " + e.getMessage());
        }
        return "verification"; // 결과를 보여주는 페이지로 이동
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam("code") String code, Model model) {
        try {
            emailService.verifyCode(code, LocalDateTime.now());
            model.addAttribute("message", "Verification successful!");
        } catch (Exception e) {
            model.addAttribute("message", "Verification failed: " + e.getMessage());
        }
        return "verification"; // 결과 페이지로 이동
    }
}
