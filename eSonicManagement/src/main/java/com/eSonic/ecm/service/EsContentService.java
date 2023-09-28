package com.eSonic.ecm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eSonic.ecm.VO.EsContentVO;
import com.eSonic.ecm.domain.EsContentDTO;
import com.eSonic.ecm.domain.EsContentEntity;
import com.eSonic.ecm.domain.EsContentDTO;
import com.eSonic.ecm.domain.EsContentEntity;
import com.eSonic.ecm.domain.EsInterfaceDTO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.repository.EsContentRepository;

import jakarta.persistence.EntityManager;

@Service
public class EsContentService {
	
	private final EntityManager entityManager = null; 
	
	@Autowired
	private EsContentRepository esContentRepository;
	
	
	//Mybatis
	public int insertEsContentMyBatis(EsContentVO esContentVO) {
		int rtnInt = 0;
		
		
		
		return rtnInt;
	}
	
	
	
	//Controller 사용
	
	public EsResultDTO getContentList(int pageNum, int cnt) {
		EsResultDTO esResultDTO = null;
		try {
			// 페이지 요청 생성 (페이지 번호는 0부터 시작)
			Pageable pageable = PageRequest.of(pageNum-1, cnt);
			// 페이지 요청을 사용하여 모든 제품 조회
			Page<EsContentEntity> page = esContentRepository.findAll(pageable);
			// 페이지의 내용 가져오기
			List<EsContentEntity> products = page.getContent();
			esResultDTO.setRtnCd("1");
			esResultDTO.setRtnMsg("SUCCESS");
			esResultDTO.setEsContentEntityList(products);
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("-100");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}
	
	
	//Interface 사용
	
	public EsResultDTO getContent(String esElementId) {
		EsResultDTO esResultDTO = null;
		try {
			EsContentEntity esContentEntity = esContentRepository.findByEsContentId(esElementId);
			if(esContentEntity==null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
			}else {
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsContentEntity(esContentEntity);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("-100");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}
	
	public EsInterfaceDTO insertEsContent(EsContentDTO esContentDTO) {
		EsInterfaceDTO esInterfaceDTO = null;

		try {
			EsContentEntity esContentEntity = new EsContentEntity(esContentDTO);
			EsContentEntity rtnEntity = esContentRepository.save(esContentEntity);
			
			if(!check(rtnEntity.getEsContentId())) {
				esInterfaceDTO.setContentKey(rtnEntity.getEsContentId());
				esInterfaceDTO.setRtnCode("1");
				esInterfaceDTO.setRtnMsg("SUCCESS");
			}else {

				esInterfaceDTO.setContentKey("");
				esInterfaceDTO.setRtnCode("-1");
				esInterfaceDTO.setRtnMsg("FAILED");
			}
			
		}catch(Exception e) {
			esInterfaceDTO.setContentKey("");
			esInterfaceDTO.setRtnCode("-100");
			esInterfaceDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {
			return esInterfaceDTO;
		}
		
	}

	public EsInterfaceDTO updateEsContent(String esElementId,EsContentDTO esContentDTO) {
		EsInterfaceDTO esInterfaceDTO = null;
		try {
			//esElementId 로 기존 데이터 찾아줌
			EsContentEntity esContentEntity = entityManager.find(EsContentEntity.class, esElementId);
			if(esContentEntity==null) {
				esInterfaceDTO.setRtnCode("0");
				esInterfaceDTO.setRtnMsg("NODATA");
			}else {
				//찾은 데이터와 받은 데이터 합치기
				esContentDTO = setContetDTO(esContentEntity,esContentDTO);
				//합친 데이터 Entity 셋팅
				esContentEntity = new EsContentEntity(esContentDTO);
				//save 를 이용하여 업데이트 진행
				EsContentEntity rtnEntity = esContentRepository.save(esContentEntity);
				esInterfaceDTO.setEsContentEntity(rtnEntity);
				esInterfaceDTO.setEsContentDTO(esContentDTO);
				if(rtnEntity.getEsWriteDate() == esContentDTO.getEsWriteDate()) {
					esInterfaceDTO.setRtnCode("1");
					esInterfaceDTO.setRtnMsg("SUCCESS");
				}else {
					esInterfaceDTO.setEsContentEntity(rtnEntity);
					esInterfaceDTO.setEsContentDTO(esContentDTO);
					esInterfaceDTO.setRtnCode("-1");
					esInterfaceDTO.setRtnMsg("NOUPDATE");
					
				}
			}
			
			
		}catch(Exception e){
			
			
			
		}finally {

		    return esInterfaceDTO;
		}
		
	}
		
	public EsInterfaceDTO deleteEsContent(String esContentId) {
		long rtnCnt = 0;
		 EsInterfaceDTO esInterfaceDTO = null;
		try {
			 rtnCnt = esContentRepository.deleteByEsContentId(esContentId);
			 esInterfaceDTO.setContentKey("");
			 esInterfaceDTO.setRtnCode(""+rtnCnt);
			 esInterfaceDTO.setRtnMsg( rtnCnt==1 ? "SUCCESS" : "NODATA" );
			
		}catch(Exception e) {
			 esInterfaceDTO.setContentKey("");
			 esInterfaceDTO.setRtnCode("-1");
			 esInterfaceDTO.setRtnMsg( e.getMessage() );
			e.printStackTrace();
			rtnCnt = -1;
		}finally {
			return esInterfaceDTO;
			
		}
	}
	
	
	//사용을 위한 함수항목

	//DTO에 없는 내용을 조회한 Entity 와 합쳐주기 위한 함수
	public EsContentDTO setContetDTO(EsContentEntity INesContentEntity,EsContentDTO OUTesContentDTO ) {
		if(INesContentEntity== null) {
			OUTesContentDTO.setEsContentId(INesContentEntity.getEsContentId());
			OUTesContentDTO.setEsArchiveId(INesContentEntity.getEsArchiveId());
			OUTesContentDTO.setEsVolumeId(INesContentEntity.getEsVolumeId());
			OUTesContentDTO.setEsContentClassId(INesContentEntity.getEsContentClassId());
			OUTesContentDTO.setEsCreateDate(INesContentEntity.getEsCreateDate());
			OUTesContentDTO.setEsCreateUser(INesContentEntity.getEsCreateUser());
			OUTesContentDTO.setEsFileExt(INesContentEntity.getEsFileExt());
			OUTesContentDTO.setEsFilePath(INesContentEntity.getEsFilePath());
			OUTesContentDTO.setEsFileSize(INesContentEntity.getEsFileSize());
			OUTesContentDTO.setEsLastAccess(INesContentEntity.getEsLastAccess());
			OUTesContentDTO.setEsLastAccessUser(INesContentEntity.getEsLastAccessUser());
			OUTesContentDTO.setEsWriteDate(INesContentEntity.getEsWriteDate());
		
		}else {
		    if(check(OUTesContentDTO.getEsContentId())) OUTesContentDTO.setEsContentId(INesContentEntity.getEsContentId());
		    if(check(OUTesContentDTO.getEsArchiveId())) OUTesContentDTO.setEsArchiveId(INesContentEntity.getEsArchiveId());
		    if(check(OUTesContentDTO.getEsVolumeId())) OUTesContentDTO.setEsVolumeId(INesContentEntity.getEsVolumeId());
			if(check(OUTesContentDTO.getEsContentClassId())) OUTesContentDTO.setEsContentClassId(INesContentEntity.getEsContentClassId());
		    if(check(OUTesContentDTO.getEsCreateDate().toString())) OUTesContentDTO.setEsCreateDate(INesContentEntity.getEsCreateDate());
		    if(check(OUTesContentDTO.getEsCreateUser())) OUTesContentDTO.setEsCreateUser(INesContentEntity.getEsCreateUser());
		    if(check(OUTesContentDTO.getEsFileExt())) OUTesContentDTO.setEsFileExt(INesContentEntity.getEsFileExt());
		    if(check(OUTesContentDTO.getEsFilePath())) OUTesContentDTO.setEsFilePath(INesContentEntity.getEsFilePath());
		    if(OUTesContentDTO.getEsFileSize() == 0) OUTesContentDTO.setEsFileSize(INesContentEntity.getEsFileSize());
		    if(check(OUTesContentDTO.getEsLastAccess().toString())) OUTesContentDTO.setEsLastAccess(INesContentEntity.getEsLastAccess());
		    if(check(OUTesContentDTO.getEsLastAccessUser())) OUTesContentDTO.setEsLastAccessUser(INesContentEntity.getEsLastAccessUser());
		    if(check(OUTesContentDTO.getEsWriteDate().toString())) OUTesContentDTO.setEsWriteDate(INesContentEntity.getEsWriteDate());			}
		return OUTesContentDTO;
	}
	
	public boolean check(String checkedValue) {
			return checkedValue == "" || checkedValue == null ? true : false;
		}
		
		

}


	
	


