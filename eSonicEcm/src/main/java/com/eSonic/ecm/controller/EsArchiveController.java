package com.eSonic.ecm.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.eSonic.ecm.domain.EsArchiveDTO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.service.EsArchiveService;

@Controller
@RequestMapping(value = "/archive")
public class EsArchiveController {

	private final EsArchiveService esArchiveService;
	@Autowired
	public EsArchiveController(EsArchiveService esArchiveService) {
		this.esArchiveService = esArchiveService;
	}
	
	/**
	 * TB_ESONIC_ARCHIVE 단일조회
	 * <p>웹 페이지의 저장그룹(Archive) 관리페이지 상세조회 통해 호출</p>
	 * @param  esArchiveId	 저장그룹ID
	 * @return model    	 esArchive : 조회한 Archive 상세정보
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@GetMapping("/search/one/{esArchiveId}")
	public String getArchive(@PathVariable String esArchiveId, Model model, SessionStatus status) throws Exception {

		EsResultDTO esResultDTO = esArchiveService.getArchive(esArchiveId);

		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브조회");

		return "cont/archive";
	}

	
	/**
	 * TB_ESONIC_ARCHIVE 리스트 조회
	 * <p>웹 페이지의 저장그룹(Archive) 관리페이지 전체보기 통해 호출</p>
	 * @param  
	 * @return model   		 esArchiveList : 조회한 Archive 리스트
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@RequestMapping(value = "/search/list")
	public String getArchiveList(EsArchiveDTO esArchiveDTO, Model model, SessionStatus status) throws Exception {

		EsResultDTO esResultDTO = esArchiveService.getArchiveList(esArchiveDTO);

		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브목록조회");
		return "cont/archive";
	}

	/**
	 * TB_ESONIC_ARCHIVE 신규등록
	 * <p>웹 페이지의 저장그룹(Archive) 관리페이지 신규등록 기능 통해 호출</p>
	 * @param  esArchiveDTO	 신규등록될 Archive 정보
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 입력된 값이 리턴 
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@PostMapping("/insert")
	public String esArchiveInsert(@RequestBody EsArchiveDTO esArchiveDTO, Model model, SessionStatus status) {

		EsResultDTO esResultDTO = esArchiveService.esArchiveInsert(esArchiveDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브등록");
		return "cont/archive";

	}
	
	/**
	 * TB_ESONIC_ARCHIVE 전체 업데이트
	 * <p>웹 페이지의 저장그룹(Archive) 관리페이지 업데이트 기능 통해 호출</p>
	 * @param  esArchiveId	 업데이트될 Archive의 저장그룹ID
	 * @param  esArchiveDTO	 업데이트될 Archive 정보
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 업데이트 된 후 재조회값이 리턴 
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@PutMapping("/updatePut/{esArchiveId}")
	public String esArchiveUpdatePut(@PathVariable String esArchiveId, @RequestBody EsArchiveDTO esArchiveDTO,
			Model model, SessionStatus status) throws SQLException {

		EsResultDTO esResultDTO = esArchiveService.esArchiveUpdatePut(esArchiveId, esArchiveDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

	
	/**
	 * TB_ESONIC_ARCHIVE 부분 업데이트
	 * <p>웹 페이지의 저장그룹(Archive) 관리페이지 부분 업데이트 기능 통해 호출</p>
	 * @param  esArchiveId	 업데이트될 Archive의 저장그룹ID
	 * @param  esArchiveDTO	 업데이트될 Archive 정보
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 업데이트 한 값이 리턴 
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@PatchMapping("/updatePatch/{esArchiveId}")
	public String esArchiveUpdatePatch(@PathVariable String esArchiveId, @RequestBody EsArchiveDTO esArchiveDTO,
			Model model, SessionStatus status) throws SQLException {
		EsResultDTO esResultDTO = esArchiveService.esArchiveUpdatePatch(esArchiveId, esArchiveDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

	
	/**
	 * TB_ESONIC_ARCHIVE 삭제
	 * <p>웹 페이지의 저장그룹(Archive) 관리페이지 부분 업데이트 기능 통해 호출</p>
	 * @param  esArchiveId	 업데이트될 Archive의 저장그룹ID
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인이 리턴
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@DeleteMapping("/delete/{esArchiveId}")
	public String esArchiveDelete(@PathVariable String esArchiveId, 
			Model model, SessionStatus status) throws SQLException {
		EsResultDTO esResultDTO = esArchiveService.esArchiveDelete(esArchiveId);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

}
