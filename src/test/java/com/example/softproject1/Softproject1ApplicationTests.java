package com.example.softproject1;

import com.example.softproject1.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Softproject1ApplicationTests {
	@Autowired
	private ArticleService articleService;

	@Test
	void testJpa(){
		for(int i=0;i<=300;i++){
			String title=String.format("테스트데이터입니다:[%03d]",i);
			String content="내용";
			this.articleService.create(title,content);
		}
	}
}
