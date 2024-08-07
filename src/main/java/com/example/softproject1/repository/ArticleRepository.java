package com.example.softproject1.repository;

import com.example.softproject1.entity.Article;
import com.example.softproject1.entity.UserEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
    Page<Article> findAll(Pageable pageable);

    Page<Article> findByAuthor(UserEntity author, Pageable pageable);
}
