package com.green.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.entity.Person;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ExampleController {
	
//	@GetMapping("/thymeleaf/example")
//	public String thymeleafExample(Model model) {     // 뷰로 데이터를 넘겨주는 모델객체
//		Person examplePerson = new Person();
//		examplePerson.setId(1L);
//		examplePerson.setName("페이커");
//		examplePerson.setAge(11);
//		examplePerson.setHobbies(List.of("운동","영화"));
//		
//		model.addAttribute("person",examplePerson);     // Person 객체 저장
//		model.addAttribute("today",LocalDate.now());
//		
//		return "example";    // example.html 라는 뷰 조회
//		
//	}
	
	@GetMapping("/thymeleaf/example")
	public ModelAndView thymeleafExample() {
		
		Person examplePerson = new Person();
		examplePerson.setId(1L);
		examplePerson.setName("페이커");
		examplePerson.setAge(21);
		examplePerson.setHobbies(List.of("운동","영화","축구"));
		ModelAndView mv = new ModelAndView();
		mv.addObject("person",examplePerson);
		mv.addObject("today",LocalDate.now());
		mv.setViewName("example");
		return mv;
		
	}


}
