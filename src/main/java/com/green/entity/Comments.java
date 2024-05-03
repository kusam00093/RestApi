package com.green.entity;

import com.green.dto.CommentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity                     // 클래스를 테이블로 생성한다
@Data                       // @Getter + @Setter + @ToString + hashCode, equlas
@NoArgsConstructor          // 기본생성자
@AllArgsConstructor
@SequenceGenerator(name="COMMENTS_SEQ_GENERATOR",sequenceName = "COMMENTS_SEQ",allocationSize = 1,initialValue = 1)
public class Comments {
	
	@Id                             // 기본키
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_SEQ_GENERATOR")                
	private Long   id;              //id
	
	@ManyToOne                      //다대일 설정(Comments <-> Article )
	@JoinColumn(name="article_id")  //외래키(부모키 Article_id 칼럼)
	private Article   article;      // 연결될 entity 객체의 이름
	
	//@Column(name="nick_name",nullable = false, length = 255)
	@Column
	private String nickname;        //nickname
	@Column
	private String body;            //body
	
	public static Comments createComment(CommentDto dto,  Article article) {

		if(dto.getId() != null) {
			throw new IllegalArgumentException("댓글 생성 실패!! 댓글의 id가 없어야 합니다");
		
		}
		// dto.getArticleId() : 입력받은 게시글의 id
		// article.getId() : 조회한 게시글의 id
		if(dto.getArticleId() != article.getId()) {
			throw new IllegalArgumentException("댓글 생성 실패!! 게시글의 id가 잘못되었습니다");
			
		}
		
		return new Comments(
				dto.getId(),                     // 입력받은댓글 아이디
				article, 						 // 부모 게시글 정보
				dto.getNickname(), 				 // 입력받은 댓글 닉네임
				dto.getBody()					 // 입력받은댓글 내용
				);
	}

	public void patch(CommentDto dto) {
		if(this.id  != dto.getId()) {
			System.out.println(";asdjghflkasdhflkja"+this.id);
			System.out.println(";asdjghflkasdhflkja"+dto.getId());
			throw new IllegalArgumentException("댓글 수정 실패!! 잘못된 아이디가 입력되었습니다");
			
		}
		if(dto.getNickname() != null) {
			this.nickname = dto.getNickname();
		}
		if(dto.getBody() != null) {
			this.body = dto.getBody();
			
		}
	}
	
	
}
