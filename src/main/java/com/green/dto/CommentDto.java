package com.green.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.entity.Comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long   id;          // 댓글 아이디
	@JsonProperty("article_id")
	private Long   articleId;   // article의 부모글 id
	
	private String nickname;    // 작성자 별명
	private String body;        // 댓글 내용
	



	// CommentDto  <- Comments(db에서 조회한)
	public static CommentDto
		createCommentDto(Comments comments) {
		
		return new CommentDto(
				comments.getId(),
				comments.getArticle().getId(),
				comments.getNickname(),
				comments.getBody()
				);
	}
}
