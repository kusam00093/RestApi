package com.green.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.dto.UserDto;
import com.green.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserApiController {

	
	//로그인체크 -> 로그인 안되었으면 ->/login
	// ->회원가입(/signup) -> /user(db저장) -> /login ->
	// 아이디/암호 -> 체크 -> 로그인 성공하면 .loginSuccesUrl("/")로 이동
	// 아이디.암호 -> 체크 -> 로그인 실패
	
	
	
    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserDto request) {
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
