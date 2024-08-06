package com.example.softproject1.service;


import com.example.softproject1.dto.ArticleForm;
import com.example.softproject1.entity.Article;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }
    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }
    public Article create(ArticleForm dto, UserEntity author){
        Article article = dto.toEntity();
        article.setAuthor(author);
        if(article.getId()!=null){
            return null;
        }
        return articleRepository.save(article);
    }
    public Article update(Long id,ArticleForm dto){
        Article article=dto.toEntity();
        log.info("id:{},article:{}",id,article.toString());
        Article target=articleRepository.findById(id).orElse(null);
        if(target==null||id!=article.getId()){
            log.info("잘못된 요청! id:{},article:{}",id,article.toString());
            return null;
        }
        target.patch(article);
        Article updated=articleRepository.save(target);
        return updated;
    }
    public Article delete(Long id){
        Article target=articleRepository.findById(id).orElse(null);
        if(target==null){return null;}
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos){
        List<Article> articleList = dtos.stream()
                .map(ArticleForm::toEntity)
                .collect(Collectors.toList());
        articleRepository.saveAll(articleList);
        return articleList;
    }
    public void create(String title,String content){
        Article a=new Article(null, title, content);
        a.setTitle(title);
        a.setContent(content);
//        a.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(a);
    }

    public Page<Article> getList(int page){
        Pageable pageable=PageRequest.of(page,10);
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getUserArticles(UserEntity author, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return articleRepository.findByAuthor(author, pageable);
    }

}
