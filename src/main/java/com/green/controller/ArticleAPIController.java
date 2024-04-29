package com.green.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController     // @Controller + @ResponseBody
public class ArticleAPIController {
	
	@Autowired
	private ArticleService articleService;
	
	// Get List : 목록조회
	@GetMapping(value="/api/articles", produces=MediaType.APPLICATION_JSON_VALUE)
	//@GetMapping(value="/api/articles", produces=MediaType.APPLICATION_XML_VALUE)
	//@GetMapping(value="/api/articles", produces="application/xml;charset=utf-8")
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
	// HttpStatus.BAD_REQUEST : 400 <- 사용자 정의 에러 : user defined
	// .build() == body(null) 
	// @ResponseBody : 넘어온 값의 java 의 객체 (ArticleForm 으로 저장한다)
	@PostMapping("/api/articles")
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
			Article created = articleService.create(dto);
			ResponseEntity<Article> result = (created != null)
					?ResponseEntity.status(HttpStatus.OK).body(created)
					:ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return result;
	}
	
	// PATCH : UPDATE
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
		System.out.println("id:"+id+",dto:"+dto);
		Article updated = articleService.update(id,dto);
		ResponseEntity<Article> result = (updated != null)
				?ResponseEntity.status(HttpStatus.OK).body(updated)
				:ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	return result;
		
	}
	// Delete : Delete
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id, @RequestBody ArticleForm dto){
		System.out.println("id:"+id+",dto:"+dto);
		Article deleted = articleService.delete(id);
		ResponseEntity<Article> result = (deleted != null)
				?ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleted)
						:ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return result;
		
	}
	
}
