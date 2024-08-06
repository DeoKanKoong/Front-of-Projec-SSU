package com.example.softproject1.controller;

import com.example.softproject1.dto.ArticleForm;
import com.example.softproject1.dto.CommentDto;
import com.example.softproject1.entity.Article;
import com.example.softproject1.entity.UserEntity;
import com.example.softproject1.repository.ArticleRepository;
import com.example.softproject1.repository.CommentRepository;
import com.example.softproject1.repository.UserRepository;
import com.example.softproject1.service.ArticleService;
import com.example.softproject1.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.Authenticator;
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
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form, RedirectAttributes redirectAttributes) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity author=userRepository.findByUsername(userDetails.getUsername());


        // DTO를 엔티티로 변환
        Article article = form.toEntity();
        article.setAuthor(author);

        // 파일 처리
        if (form.getRepresentativeImage() != null && !form.getRepresentativeImage().isEmpty()) {
            // 파일 저장 로직
            String fileName = form.getRepresentativeImage().getOriginalFilename();
            // 저장 경로에 파일 저장
        }

        // 리파지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 등록되었습니다!");
        return "redirect:/articles/" + saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id,Model model){
        Article article = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        model.addAttribute("article", article);
        model.addAttribute("commentDtos", commentsDtos);
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model,@RequestParam(value="page",defaultValue = "0") int page) {
        Page<Article> paging = this.articleService.getList(page);
        model.addAttribute("paging", paging);
        return "articles/index";
    }
    @GetMapping("/articles/my") //내가 작성한 글 보기
    public String myArticles(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity author = userRepository.findByUsername(userDetails.getUsername());

        Page<Article> paging = articleService.getUserArticles(author, page);
        model.addAttribute("paging", paging);
        return "articles/my";
    }


    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity=articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        Article articleEntity=form.toEntity();
        Article target=articleRepository.findById(articleEntity.getId()).orElse(null);
        if(target!=null){
            articleRepository.save(articleEntity);
        }
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }




}
