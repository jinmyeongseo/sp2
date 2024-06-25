package com.sp.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.app.domain.Score;
import com.sp.app.service.ScoreService;

@Controller
@RequestMapping("/score/*")
public class ScoreController {
	@Autowired
	private ScoreService service;
	
	@RequestMapping("list") // GET, POST 모두 처리
	public String list(
			@RequestParam(name="page", defaultValue = "1") int current_page,
			@RequestParam(name = "schType", defaultValue = "hak") String schType,
			@RequestParam(name="kwd", defaultValue = "") String kwd,
			HttpServletRequest req,
			Model model) throws Exception {
		
		return "score/list";
	}
	
	@GetMapping("write")
	public String scoreForm(Model model) throws Exception {
		
		model.addAttribute("mode", "write");
		
		return "score/write";
	}
	
	@PostMapping("write")
	public String scoreSubmit(Score dto, Model model) throws Exception {
		
		try {
			service.insertScore(dto);
		} catch (DuplicateKeyException e) {
			// 기본키 중복에 의한 제약 위반
			model.addAttribute("message", "학번중복으로 등록이 실패 했습니다.");
			model.addAttribute("mode", "write");
			return "score/write";
		} catch (DataIntegrityViolationException e) {
			// 데이터 형식 오류, 참조키, NOT NULL 등의 제약 조건 위반
			model.addAttribute("message", "제약조건 위반으로 등록이 실패 했습니다.");
			model.addAttribute("mode", "write");
			return "score/write";
		} catch (Exception e) {
			// 기타
			model.addAttribute("message", "등록이 실패 했습니다.");
			model.addAttribute("mode", "write");
			return "score/write";
		}
		
		return "redirect:/score/list";
	}
}
