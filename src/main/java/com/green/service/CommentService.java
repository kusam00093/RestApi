package com.green.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.CommentDto;
import com.green.entity.Article;
import com.green.entity.Comments;
import com.green.repository.ArticleRepository;
import com.green.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ArticleRepository articleRepository;
	
	
	// 1. 댓글 조회
	public List<CommentDto> comments(Long articleId) {
		/*
		// 1. 댓글을 db에서 조회해서 Entity에 담는다
		List<Comments> commentList = commentRepository.findByArticleId(articleId);
		
		// 2. 엔티티 -> dto 로 변환
		List<CommentDto> dtos = new ArrayList<CommentDto>();
		for (int i = 0; i < commentList.size(); i++) {
			Comments c = commentList.get(i);
			CommentDto dto = CommentDto.createCommentDto(c);
			dtos.add(dto);
		}
		// 3. 결과를 반환
		return dtos;
		*/
		// ArrayList.stream()
		//	.mpa((comment) -> {     // .map() : stream 전용함수 
		//		집합(Collection) 에 element들을 반복으로 조작
		//      .map() vs forEach() = 둘다 배열을 모두 조작한다
		//			.map() : 내부의 element를 값이나 사이즈가 변경할때 사용 : 모두 대문자로 변경
		//			CommnetDto.createCommentDto(comment)		
		//	})
		
		
		return commentRepository.findByArticleId(articleId).stream()    // stream 으로 전환
				.map(comment -> CommentDto.createCommentDto(comment)).collect(Collectors.toList());
	}

	@Transactional  // 오류 발생시 db 롤백하기 위해 (throw(예외) 사용하는 이유)
	public CommentDto create(Long articleId, CommentDto dto) {
		// 1. 게시글 조회 및 조회실패 예외발생
		// 게시글에 존재하지 않는 articleId가 넘어오면 조회결과가 없다 Throw 
		Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시물이 없습니다")); // 조회와 예외처리 동시실시
		
		// 2.댓글 엔티티 생성 -> 저장할 데이터(comments)를 만든다 
		Comments comments = Comments.createComment(dto, article);
		
		// 3.댓글 엔티티를 db에 저장
		Comments saved = commentRepository.save(comments);
		
		// 4. Comments type created -> dto를 변환후 controller 리턴
		// 변환이유가 controller 에서 엔티티 타입을 다루지 않도록 하기 위해
		return CommentDto.createCommentDto(saved);
	}
	@Transactional
	public CommentDto update(Long id, CommentDto dto) {
		// 1. 댓글 조회 및 예외발생
		Comments target = commentRepository.findById(id).orElseThrow(()->
			new IllegalArgumentException("댓글 수정 실패! 수정할 댓글이 없습니다")
				);
		// 2. 댓글 수정 : 조회한 데이터의 내용을 수정(class 안의 내용변경)
		// target : 수정할 원본 데이터
		// dto    : 수정할 입력받으 데이터
		target.patch(dto);    // target <- dto(nickname , body)
		
		
		// 3. db에 저장
		Comments updated = commentRepository.save(target);
		// 4. updated -> commentDto 로 변환하여 결과 리턴
		return CommentDto.createCommentDto(updated);
	}

	@Transactional
	public CommentDto delete(Long id) {
		// 1. 삭제할 댓글 조회
		Comments target = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패!! 삭제할 댓글을 찾을 수 없습니다"));
		// 2. 실제 db에서 삭제
		 commentRepository.delete(target);
		// 3. 삭제 댓글을 dto로 변환한 후 리턴
		
		return CommentDto.createCommentDto(target);
	}
	
		
	
	
	
	
}
