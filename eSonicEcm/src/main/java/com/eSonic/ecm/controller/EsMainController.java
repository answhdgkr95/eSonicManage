package com.eSonic.ecm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eSonic.ecm.VO.EsUserVO;

@Controller
public class EsMainController {
	
	@RequestMapping(value="/main")
	public String mainPage(Model model) {
		
		List<EsUserVO> tList = new ArrayList<>();

		for(int i = 1; i < 6; i++) {
			EsUserVO uservo = new EsUserVO();
			uservo.setUserName("user"+i);
			uservo.setUserId("userId"+i);
			uservo.setUserAge(10+i);
			tList.add(uservo);
		}
		
		model.addAttribute("tList", tList);
		
		return "main";
	}

}
