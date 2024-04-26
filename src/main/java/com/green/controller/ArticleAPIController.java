package com.green.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ArticleAPIController {
	
	@Autowired
	private ArticleService articleService;
	
	// Get List : 목록조회
	@GetMapping("/api/articles")
	public List<Article> index() {
		return articleService.index();
	}
	
	// Get Id : id로 조회
	@GetMapping("/api/articles/{id}")
	public Article show(@PathVariable Long id) {
		Article article = articleService.show(id);
			
		return article;
	}
	
	// Post : insert - create
	// 결과 : 저장된 article 객체, 상태코드 <- 저장되었습니다
	// Generic : 파라미터 type을 객체 (T)type 를 사용해라
	// <T>  : class type, <?>   T는 외부에 입력된 type
	// ResponseEntity<Article> 
	// Article Data + http state code : 200 
	// {"id" : 12, "title":"새글", "content": "내용"}
	// HttpStatus.OK : 200
	// HttpStatus.BAD_REQUEST : 400
	// .build() == body(null) 
	// @ResponseBody : 넘어온 값의 json
	@PostMapping("/api/articles")
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
			Article created = articleService.create(dto);
			ResponseEntity<Article> result = (created != null)
					?ResponseEntity.status(HttpStatus.OK).body(created)
					:ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return result;
	}
	
	
	
}
