package com.example.softproject1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int articleId;
    private String name;
    private String email;
    private String studentId;
    private String department;

    @Column(length = 1000)
    private String introduction;
}
