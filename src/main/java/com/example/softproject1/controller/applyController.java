package com.example.softproject1.controller;

import com.example.softproject1.entity.Article;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.ArticleRepository;
import com.example.softproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class applyController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/students/apply/{articleId}")
    public String apply(@PathVariable("articleId") int articleId, Model model) {
        // Here you would typically save the application to your database
        // For simplicity, let's assume you have a service method to handle this

        // Assuming you have a service method to handle application logic
        // For example, a method to save application details and associate them with the article
        // applicationService.applyToArticle(articleId, userDetails.getUsername());

        // For demonstration, we just return a confirmation message
        model.addAttribute("message", "Application submitted successfully!");

        return "apply/confirmation";
    }

//    @GetMapping("/students/applicants/{articleId}")
//    public String getApplicants(@PathVariable("articleId") int articleId, Model model) {
//        Article article = articleRepository.findById((long) articleId).orElse(null);
//        if (article == null) {
//            // Handle case where article with given ID does not exist
//            return "error"; // Redirect to an error page or handle appropriately
//        }
//
////        List<UserEntity> applicants = article.getApplicants(); // Assuming Article entity has applicants list
//
//        model.addAttribute("article", article);
//        model.addAttribute("applicants", applicants);
//
//        return "apply/applicants";
//    }
}
