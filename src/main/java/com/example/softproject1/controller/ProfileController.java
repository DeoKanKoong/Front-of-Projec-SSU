package com.example.softproject1.controller;

import com.example.softproject1.entity.Profile;
import com.example.softproject1.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/submit-profile")
    @ResponseBody
    public String submitProfile(@RequestParam("articleId") int articleId,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("studentId") String studentId,
                                @RequestParam("department") String department,
                                @RequestParam("introduction") String introduction) {
        Profile profile = new Profile();
        profile.setArticleId(articleId);
        profile.setName(name);
        profile.setEmail(email);
        profile.setStudentId(studentId);
        profile.setDepartment(department);
        profile.setIntroduction(introduction);

        profileRepository.save(profile);

        return "success"; // 성공 메시지를 반환
    }
}
