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

import com.eSonic.ecm.domain.EsUserDTO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.service.EsUserService;

@Controller
@RequestMapping(value = "/user")
public class EsUserController {

	private final EsUserService esUserService;

	@Autowired
	public EsUserController(EsUserService esUserService) {
		this.esUserService = esUserService;
	}

	/**
	 * TB_ESONIC_USER 단일조회
	 * */
	@GetMapping("/search/one/{esUserId}")
	public String getUser(@PathVariable String esUserId, Model model, SessionStatus status) throws Exception {

		EsResultDTO esResultDTO = esUserService.getUser(esUserId);
		

		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브조회");

		return "cont/archive";
	}

	/**
	 * TB_ESONIC_USER 리스트 조회
	 * <p>
	 * 웹 페이지의 저장그룹(User) 관리페이지 전체보기 통해 호출
	 * </p>
	 * 
	 * @param
	 * @return model esUserList : 조회한 User 리스트
	 * @return model ts : 경과시간
	 * @return model pageName : 페이지이름
	 * @return String cont/archive : 호출한 페이지 주소
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	@RequestMapping(value = "/search/list")
	public String getUserList(EsUserDTO esUserDTO, Model model, SessionStatus status) throws Exception {

		EsResultDTO esResultDTO = esUserService.getUserList(esUserDTO);

		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브목록조회");
		return "cont/archive";
	}

	/**
	 * TB_ESONIC_USER 신규등록
	 * <p>
	 * 웹 페이지의 저장그룹(User) 관리페이지 신규등록 기능 통해 호출
	 * </p>
	 * 
	 * @param esUserDTO 신규등록될 User 정보
	 * @return model esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 입력된 값이 리턴
	 * @return model ts : 경과시간
	 * @return model pageName : 페이지이름
	 * @return String cont/archive : 호출한 페이지 주소
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	@PostMapping("/insert")
	public String esUserInsert(@RequestBody EsUserDTO esUserDTO, Model model, SessionStatus status) {

		EsResultDTO esResultDTO = esUserService.esUserInsert(esUserDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브등록");
		return "cont/archive";

	}

	/**
	 * TB_ESONIC_USER 전체 업데이트
	 * <p>
	 * 웹 페이지의 저장그룹(User) 관리페이지 업데이트 기능 통해 호출
	 * </p>
	 * 
	 * @param esUserId  업데이트될 User의 저장그룹ID
	 * @param esUserDTO 업데이트될 User 정보
	 * @return model esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 업데이트 된 후 재조회값이 리턴
	 * @return model ts : 경과시간
	 * @return model pageName : 페이지이름
	 * @return String cont/archive : 호출한 페이지 주소
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	@PutMapping("/updatePut/{esUserId}")
	public String esUserUpdatePut(@PathVariable String esUserId, @RequestBody EsUserDTO esUserDTO, Model model,
			SessionStatus status) throws SQLException {

		EsResultDTO esResultDTO = esUserService.esUserUpdatePut(esUserId, esUserDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

	/**
	 * TB_ESONIC_USER 부분 업데이트
	 * <p>
	 * 웹 페이지의 저장그룹(User) 관리페이지 부분 업데이트 기능 통해 호출
	 * </p>
	 * 
	 * @param esUserId  업데이트될 User의 저장그룹ID
	 * @param esUserDTO 업데이트될 User 정보
	 * @return model esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인, 업데이트 한 값이 리턴
	 * @return model ts : 경과시간
	 * @return model pageName : 페이지이름
	 * @return String cont/archive : 호출한 페이지 주소
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	@PatchMapping("/updatePatch/{esUserId}")
	public String esUserUpdatePatch(@PathVariable String esUserId, @RequestBody EsUserDTO esUserDTO, Model model,
			SessionStatus status) throws SQLException {
		EsResultDTO esResultDTO = esUserService.esUserUpdatePatch(esUserId, esUserDTO);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

	/**
	 * TB_ESONIC_USER 삭제
	 * <p>
	 * 웹 페이지의 저장그룹(User) 관리페이지 부분 업데이트 기능 통해 호출
	 * </p>
	 * 
	 * @param esUserId 업데이트될 User의 저장그룹ID
	 * @return model esResultDTO : 리턴될 항목으로 성공여부, 실패시 원인이 리턴
	 * @return model ts : 경과시간
	 * @return model pageName : 페이지이름
	 * @return String cont/archive : 호출한 페이지 주소
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	@DeleteMapping("/delete/{esUserId}")
	public String esUserDelete(@PathVariable String esUserId, Model model, SessionStatus status) throws SQLException {
		EsResultDTO esResultDTO = esUserService.esUserDelete(esUserId);
		model.addAttribute("esResultDTO", esResultDTO);
		model.addAttribute("ts", System.currentTimeMillis());
		model.addAttribute("pageName", "아카이브업데이트");
		return "cont/archive";
	}

}
