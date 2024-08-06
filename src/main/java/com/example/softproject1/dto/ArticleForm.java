package com.example.softproject1.dto;

import com.example.softproject1.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@ToString
@Setter@Getter
public class ArticleForm {

    private String title;
    private Integer numberOfPeople;
    private String deadline; // 또는 LocalDate
    private String condition;
    private String preferentialTreatment;
    private String field;
    private MultipartFile representativeImage;
    private String content;

    public Article toEntity() {
        return new Article(null,title,content);
    }


}
