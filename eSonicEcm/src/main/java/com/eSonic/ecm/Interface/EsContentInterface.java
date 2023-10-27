package com.eSonic.ecm.Interface;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eSonic.ecm.VO.EsContentVO;
import com.eSonic.ecm.domain.EsContentDTO;
import com.eSonic.ecm.domain.EsInterfaceDTO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.service.EsContentService;

@Controller
@RequestMapping(value = "/interface/content")
public class EsContentInterface {
	private final EsContentService esContentService;

	@Autowired
	public EsContentInterface(EsContentService esContentService) {
		this.esContentService = esContentService;
	}

	/**
	 * TB_ESONIC_CONTENT 단일조회
	 * <p>
	 * Interface 의 파일 다운로드 기능에서 사용
	 * </p>
	 * 
	 * @param esElementId 파일키
	 * @return EsResultDTO esContentDTO : 조회한 파일키 상세정보
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	// @RequestMapping(value = "/search/one")
	@GetMapping("/search/one/{esElementId}")
	@ResponseBody
	public EsResultDTO getContent(@PathVariable String esElementId) throws Exception {

		EsResultDTO esResultDTO = esContentService.getContent(esElementId);

		return esResultDTO;
	}

	/**
	 * TB_ESONIC_CONTENT 신규등록
	 * <p>
	 * Interface 의 파일 등록 기능에서 사용
	 * </p>
	 * 
	 * @param esElementId 파일키
	 * @return EsResultDTO esContentDTO : 조회한 파일키 상세정보
	 * @throws Exception 발생 가능한 예외에 대한 설명.
	 */
	@PostMapping("/insert")
	@ResponseBody
	public EsInterfaceDTO insertEsContent(@RequestBody EsContentVO esContentVO) {
		System.out.println("esContentVO : " + esContentVO);
		EsInterfaceDTO esInterfaceDTO = esContentService.insertEsContentMyBatis(esContentVO);
		System.out.println("getRtnCode : " + esInterfaceDTO.getRtnCode());
		System.out.println("getRtnMsg : " + esInterfaceDTO.getRtnMsg());
		return esInterfaceDTO;
	}

	@PutMapping("/updateput")
	@ResponseBody
	public EsInterfaceDTO updatePut( @RequestBody EsContentVO esContentVO) {
		System.out.println("esContentVO : " + esContentVO);
		EsInterfaceDTO esInterfaceDTO = esContentService.updateputEsContentMyBatis(esContentVO);
		System.out.println("getRtnCode : " + esInterfaceDTO.getRtnCode());
		System.out.println("getRtnMsg : " + esInterfaceDTO.getRtnMsg());
		return esInterfaceDTO;
	}
//
//	@PatchMapping("/updatePatch/{esElementId}")
//	public ResponseEntity<EsContentEntity> updatePatch(@PathVariable String esElementId,
//			@RequestBody EsContentDTO esContentDTO) throws SQLException {
//
//		return ResponseEntity.ok().body(esContentService.updateEsContent(esElementId, esContentDTO));
//	}
//
//	@DeleteMapping("/delete/{esElementId}")
//	public int deleteEsContentById(@PathVariable String esElementId) throws SQLException {
//
//		int a = esContentService.deleteEsContentById(esElementId);
//		System.out.println(a);
//		return a;
//	}
//
//	@DeleteMapping("/delete")
//	public boolean deleteEsContent(@RequestBody EsContentDTO esContentDTO) throws SQLException {
//
//		boolean a = esContentService.deleteEsContent(esContentDTO);
//		System.out.println(a);
//		return a;
//	}
}
