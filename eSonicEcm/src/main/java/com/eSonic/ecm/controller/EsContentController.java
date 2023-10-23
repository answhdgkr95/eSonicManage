package com.eSonic.ecm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.service.EsContentService;

@Controller
@RequestMapping(value = "/content")
public class EsContentController {

	private final EsContentService esContentService;

	@Autowired
	public EsContentController(EsContentService esContentService) {
		this.esContentService = esContentService;
	}
	/**
	 * TB_ESONIC_CONTENT 전체조회
	 * <p>관리자화면 Content 등록현황에서 확인 가능</p>
	 * @param  page	 		 페이지번호
	 * @param  cnt	 		 1페이지당 들어갈 카운트
	 * @return EsResultDTO   List<esContentEntity> : 조회한 정보
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	// @RequestMapping(value = "/search/one")
	@GetMapping("/search/{page}/{cnt}")
	public String getContentList(@PathVariable int page,@PathVariable int cnt,Model model) throws Exception {


		EsResultDTO esResultDTO = esContentService.getContentList(page,cnt);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브조회");

		return "cont/content";
		
	}
	


}
