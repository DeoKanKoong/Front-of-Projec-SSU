package com.example.softproject1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private Integer numberOfPeople;

    @Column
    private LocalDate deadline;

    @Column
    private String condition;

    @Column
    private String preferentialTreatment;

    @Column
    private String field;

    @Column
    private String representativeImage;


    public Article(UserEntity author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Article() {

    }

    public void patch(Article article) {
        if(article.title != null) {
            this.title=article.title;
        }
        if(article.content != null) {
            this.content=article.content;
        }
    }

}

