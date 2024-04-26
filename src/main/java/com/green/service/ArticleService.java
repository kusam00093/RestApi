package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	// article 목록조회
	public List<Article> index() {
		// db 저장하기 전에 작업할 코딩
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
	
}
