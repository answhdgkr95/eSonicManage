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

import com.eSonic.ecm.domain.EsVolumeDTO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.service.EsVolumeService;

@Controller
@RequestMapping(value = "/volume")
public class EsVolumeController {

	private final EsVolumeService esVolumeService;
	@Autowired
	public EsVolumeController(EsVolumeService esVolumeService) {
		this.esVolumeService = esVolumeService;
	}
	
	/**
	 * TB_ESONIC_VOLUME 단일조회
	 * <p>웹 페이지의 저장그룹(Volume) 관리페이지 상세조회 통해 호출</p>
	 * @param  esVolumeId	 저장그룹ID
	 * @return model    	 esVolume : 조회한 Volume 상세정보
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	// @RequestMapping(value = "/search/one")
	@GetMapping("/search/one/{esVolumeId}")
	public String getVolume(@PathVariable String esVolumeId, Model model, SessionStatus status) throws Exception {

		EsResultDTO esResultDTO = esVolumeService.getVolume(esVolumeId);

		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브조회");

		return "cont/archive";
	}

	
	/**
	 * TB_ESONIC_VOLUME 리스트 조회
	 * <p>웹 페이지의 저장그룹(Volume) 관리페이지 전체보기 통해 호출</p>
	 * @param  
	 * @return model   		 esVolumeList : 조회한 Volume 리스트
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@RequestMapping(value = "/search/list")
	public String getVolumeList(EsVolumeDTO esVolumeDTO, Model model, SessionStatus status) throws Exception {

		EsResultDTO esResultDTO = esVolumeService.getVolumeList(esVolumeDTO);

		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브목록조회");
		return "cont/archive";
	}

	/**
	 * TB_ESONIC_VOLUME 신규등록
	 * <p>웹 페이지의 저장그룹(Volume) 관리페이지 신규등록 기능 통해 호출</p>
	 * @param  esVolumeDTO	 신규등록될 Volume 정보
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 입력된 값이 리턴 
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@PostMapping("/insert")
	public String esVolumeInsert(@RequestBody EsVolumeDTO esVolumeDTO, Model model, SessionStatus status) {

		EsResultDTO esResultDTO = esVolumeService.esVolumeInsert(esVolumeDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브등록");
		return "cont/archive";

	}
	
	/**
	 * TB_ESONIC_VOLUME 전체 업데이트
	 * <p>웹 페이지의 저장그룹(Volume) 관리페이지 업데이트 기능 통해 호출</p>
	 * @param  esVolumeId	 업데이트될 Volume의 저장그룹ID
	 * @param  esVolumeDTO	 업데이트될 Volume 정보
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 업데이트 된 후 재조회값이 리턴 
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@PutMapping("/updatePut/{esVolumeId}")
	public String esVolumeUpdatePut(@PathVariable String esVolumeId, @RequestBody EsVolumeDTO esVolumeDTO,
			Model model, SessionStatus status) throws SQLException {

		EsResultDTO esResultDTO = esVolumeService.esVolumeUpdatePut(esVolumeId, esVolumeDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

	
	/**
	 * TB_ESONIC_VOLUME 부분 업데이트
	 * <p>웹 페이지의 저장그룹(Volume) 관리페이지 부분 업데이트 기능 통해 호출</p>
	 * @param  esVolumeId	 업데이트될 Volume의 저장그룹ID
	 * @param  esVolumeDTO	 업데이트될 Volume 정보
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 업데이트 한 값이 리턴 
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@PatchMapping("/updatePatch/{esVolumeId}")
	public String esVolumeUpdatePatch(@PathVariable String esVolumeId, @RequestBody EsVolumeDTO esVolumeDTO,
			Model model, SessionStatus status) throws SQLException {
		EsResultDTO esResultDTO = esVolumeService.esVolumeUpdatePatch(esVolumeId, esVolumeDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

	
	/**
	 * TB_ESONIC_VOLUME 삭제
	 * <p>웹 페이지의 저장그룹(Volume) 관리페이지 부분 업데이트 기능 통해 호출</p>
	 * @param  esVolumeId	 업데이트될 Volume의 저장그룹ID
	 * @return model    	 esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인이 리턴
	 * @return model	     ts : 경과시간
	 * @return model     	 pageName : 페이지이름
	 * @return String		 cont/archive : 호출한 페이지 주소
	 * @throws Exception  	 발생 가능한 예외에 대한 설명.
	 */
	@DeleteMapping("/delete/{esVolumeId}")
	public String esVolumeDelete(@PathVariable String esVolumeId, 
			Model model, SessionStatus status) throws SQLException {
		EsResultDTO esResultDTO = esVolumeService.esVolumeDelete(esVolumeId);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

}
