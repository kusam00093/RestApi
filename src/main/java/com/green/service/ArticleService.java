package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	// article 목록조회
	public List<Article> index() {
		// db 저장하기 전에 작업할 코딩
		// JPA 함수 : findAll()
		return articleRepository.findAll();
	}

	// article id 로 조회
	public Article show(Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		return article;
	}

	public Article create(ArticleForm dto) {
		
		// dto : {"title":"제목", "content":"내용"}
		Article article = dto.toEntity();
		
		// create 는 생성요청이고 번호 자동 증가이므로 번호 필요없다
		// 그래서 id가 존재하면 안된다
		
		if(article.getId() != null) {
			return null;
			
		}
		
		Article saved = articleRepository.save(article);
		return saved;
	}
	public Article update(Long id, ArticleForm dto) {
		// 1. dto -< ENTITY 로 변환
		Article article = dto.toEntity();
		log.info("id:{}, article:{}",id, article.toString());
		
		// 2. 타깃(기존 글)을 id로 조회하기
		Article target = articleRepository.findById(id).orElse(null);
		
		// 3. 잘못된 요청을 처리
		if(target == null || id != article.getId()) {
			log.info("id:{}, article:{}",id, article.toString());
			return null;     // 조회한 자료가 없거나 id가 틀리면
		}
		// 4. 없데이트 및 정상으답(ok)
		article.patch(article);
		Article updated = articleRepository.save(article);
		
		return updated;
		
		
	}

	public Article delete(Long id) {

		
		// 1. 타깃(삭제할 글)을 id로 조회하기
		Article target = articleRepository.findById(id).orElse(null);
		
		// 2. 잘못된 요청을 처리
		if(target == null) {
			
			return null;     // 조회한 자료가 없거나 id가 틀리면
		}
		// 3. 삭제 및 정상으답(ok)
		articleRepository.delete(target);
		return target;
	}


	
}
