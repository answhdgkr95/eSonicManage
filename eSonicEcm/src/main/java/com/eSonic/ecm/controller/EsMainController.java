package com.eSonic.ecm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.domain.EsUserDTO;
import com.eSonic.ecm.service.EsUserService;

@Controller
public class EsMainController {
	
	private final EsUserService esUserService;
	
	@Autowired
	public EsMainController(EsUserService esUserService) {
		this.esUserService = esUserService;
	}
	
	@RequestMapping(value="/main")
	public String mainPage(Model model) {
		
		EsUserDTO esUserDTO = new EsUserDTO();
		
		EsResultDTO esResultDTO = esUserService.getUserList(esUserDTO);
		
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브목록조회");
		
		
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String loginPage(Model model) {
		
		return "login";
	}

}
