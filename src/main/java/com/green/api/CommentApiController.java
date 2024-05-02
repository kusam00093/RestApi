package com.green.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.CommentDto;
import com.green.service.CommentService;

// 댓글 조회, 댓글 추가, 댓글 수정, 삭제
@RestController
public class CommentApiController {
	
	@Autowired
	private CommentService commentService;
	
	
	
	//1. 댓글 조회(GET)
	@GetMapping("/api/articles/{articleId}/comments")
	public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
		
		// 정보조회를 서비스에게 위임
		List<CommentDto> dtos = commentService.comments(articleId);
		// Repository : status.od + dtos(ArrayList -> json으로 출력(이유 : RestController라서))을 리턴
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
	
	//2. 댓글 생성(POST)
	/*
	http://localhost:9090/api/articles/4/comments
	입력 :  {"article_id":4, "nickname":"ku00093", "body":"미션임파서블" }
	결과 : {"id": null,	"article_id": 4,	"nickname": "ku00093",	"body": "미션임파서블"}
	에러발생 : {id: 4,	"article_id": 4,	"nickname": "ku00093",	"body": "미션임파서블"}
	결과  : 400에러(Bad Request) 발생 - {"id":, 입력 데이터의 키값은 json type "" 안에 저장해야한다
	에러발생 : {"id": 4,	"article_id": 4,	"nickname": "ku00093",	"body": "미션임파서블"}
	결과   : 
	
	
	*/
	@PostMapping("/api/articles/{articleId}/comments")
	public ResponseEntity<CommentDto> create(
			@PathVariable Long articleId,       // {articleId} : 게시글 번호
			@RequestBody CommentDto dto){		// 입력된 자료들 input, select
		
		CommentDto createdDto = commentService.create(articleId, dto);
		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(createdDto);
	}
	//3. 댓글 수정(PATCH)
	//http://localhost:9090/api/comments/10
	// 수정전 데이터 : {"id": 10,	"article_id": 4,	"nickname": "ku00093",	"body": "미션임파서블"}
	// 입력 : {"id": 10,	"article_id": 4,	"nickname": "ku00093",	"body": "미션임파서블11"}
	@PatchMapping("/api/comments/{id}")
	public ResponseEntity<CommentDto> update(
			@PathVariable Long id,
			@RequestBody CommentDto dto  // 수정할 데이터를 가지고 있다(dto)
			){
		CommentDto updateDto = commentService.update(id,dto);
		return ResponseEntity.status(HttpStatus.OK).body(updateDto);
	}
	//4. 댓글 삭제(DELETE)
	@DeleteMapping("/api/comments/{id}")
	public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
		CommentDto deletedDto = commentService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
	}
			
}
