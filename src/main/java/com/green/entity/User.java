package com.green.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usersjpa")
// entity 는 테이블 명과 동일하나 테이블 oracle 은 user 테이블을 생성 불가
// table 명을 변경해야 한다 (usersjpa)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// UserDetails 인테페이스를 구현하여 User 엔티티를 생성한다
public class User implements UserDetails {
	
	// 칼럼부분으 표시
	@Column(name ="id", updatable= false)
	@Id      // 기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // db 정책
	private Long id;
	
	@Column(name="email",nullable=false, unique=true)
	private String email;
	
	@Column(name="password", nullable=false)
	private String  password;
	
	// 생성자
	// @Builder
	// User user = User.builder()
	//				   .email()
	//				   .password()
	//				   .build()
	
	@Builder
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	
	
	// implement 할 함수 들
	@Override   // 권한 반환
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("user"));
	}

	@Override   // 사용자의 비밀번호 
	public String getPassword() {
		return password;
	}

	@Override   // 사용자 아이디(이메일)
	public String getUsername() {   // <input name="username"
		return email;
	}

	@Override   // 계정 만료 여부 반환
	public boolean isAccountNonExpired() {
		// 계정이 만료 되었는지를 확인하는 로직
		return true;   // true -> 만료되지 않음
	}

	@Override   // 계정 잠금 여부 반환
	public boolean isAccountNonLocked() {
		// 계정 잠금여부를 확인하는 로직
		return true; //true = 잠금되지 않음
	}

	@Override   // 패스워드 만료여부 반환
	public boolean isCredentialsNonExpired() {
		// 패스워드가 만료되었는지 확인하는 로직
		return true;  // true -> 만료되지 않음
	}

	@Override
	public boolean isEnabled() {  //계정 사용가능 여부 반환
		// 계정 사용 가능 여부 로직
		return true; // true -> 사용가능
	}



}
