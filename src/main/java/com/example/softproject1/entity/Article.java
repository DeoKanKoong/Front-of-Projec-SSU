package com.example.softproject1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

//    @Column(nullable = false,updatable = false)
//    private LocalDateTime createDate;

    public void patch(Article article) {
        if(article.title != null) {
            this.title=article.title;
        }
        if(article.content != null) {
            this.content=article.content;
        }
    }

//    @PrePersist
//    protected void onCreate() {
//        this.createDate = LocalDateTime.now();
//    }
}

