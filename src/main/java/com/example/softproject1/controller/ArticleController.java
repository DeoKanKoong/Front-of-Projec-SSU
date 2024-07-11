package com.example.softproject1.controller;
import com.example.softproject1.dto.CommentDto;
import com.example.softproject1.repository.ArticleRepository;
import com.example.softproject1.dto.ArticleForm;
import com.example.softproject1.entity.Article;
import com.example.softproject1.repository.CommentRepository;
import com.example.softproject1.service.ArticleService;
import com.example.softproject1.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
        Article article=form.toEntity();//DTO 엔티티 변환
        log.info(article.toString());
        Article saved=articleRepository.save(article);//리파지터리로 엔티티를 DB에 저장
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id,Model model){
        log.info("id = "+id);
        Article articleEntity=articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos=commentService.comments(id);
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        ArrayList<Article> articleEntityList=articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity=articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        Article articleEntity=form.toEntity();
        log.info(articleEntity.toString());
        Article target=articleRepository.findById(articleEntity.getId()).orElse(null);
        if(target!=null){
            articleRepository.save(articleEntity);
        }
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        if (target != null) {
//            commentRepository.deleteByArticleId(id);
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }

        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }




}
