package com.eSonic.ecm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eSonic.ecm.VO.EsContentVO;
import com.eSonic.ecm.domain.EsContentDTO;
import com.eSonic.ecm.domain.EsContentEntity;
import com.eSonic.ecm.domain.EsInterfaceDTO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.mapper.EsContentMapper;
import com.eSonic.ecm.repository.EsContentRepository;

import jakarta.persistence.EntityManager;

@Service
public class EsContentService {
	
	private final EntityManager entityManager = null; 
	
	@Autowired
	private EsContentRepository esContentRepository;
	
	

	//Mybatis
    private final EsContentMapper esContentMapper;
    @Autowired
    public EsContentService(EsContentMapper esContentMapper) {
        this.esContentMapper = esContentMapper;
    }
    
	@SuppressWarnings("finally")
	public EsInterfaceDTO insertEsContentMyBatis(EsContentVO esContentVO) {
		EsInterfaceDTO esInterfaceDTO = new EsInterfaceDTO();
		try {
			 int a = esContentMapper.insertEsContentMyBatis(esContentVO);
			 if(a==1) {
				 esInterfaceDTO.setRtnCode("01");
				 esInterfaceDTO.setRtnMsg("SUCCESS");
				 esInterfaceDTO.setContentKey(esContentVO.getEsContentId());
			 }else {
				 esInterfaceDTO.setRtnCode("00");
				 esInterfaceDTO.setRtnMsg("ALREADYDATA");
				 esInterfaceDTO.setContentKey("0");
			 }
		}catch(Exception e) {

			 esInterfaceDTO.setRtnCode("02");
			 esInterfaceDTO.setRtnMsg("EXCEPTION");
			 esInterfaceDTO.setContentKey("-1");
			 e.printStackTrace();
		}finally {
			
			 return esInterfaceDTO;
		}
		
	}
	
	
	//Controller 사용

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO getContentList(int pageNum, int cnt) {
		EsResultDTO esResultDTO = new EsResultDTO();
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

	@SuppressWarnings({ "null", "finally" })
	public EsInterfaceDTO insertEsContent(EsContentDTO esContentDTO) {
		EsInterfaceDTO esInterfaceDTO  = new EsInterfaceDTO();
		try {
			
			
			
			System.out.println("esContentVO getEsFilePath : " + esContentDTO.getEsFilePath());
			EsContentEntity esContentEntity = new EsContentEntity(esContentDTO);
			EsContentEntity esContentRtnEntity= esContentRepository.save(esContentEntity);
			
			
			if(esContentRtnEntity!=null) {
				esInterfaceDTO.setContentKey(esContentDTO.getEsContentId());
				esInterfaceDTO.setRtnCode("01");
				esInterfaceDTO.setRtnMsg("SUCCESS");
			}else {

				esInterfaceDTO.setContentKey("");
				esInterfaceDTO.setRtnCode("00");
				esInterfaceDTO.setRtnMsg("FAILED");
			}
			
		}catch(Exception e) {
			esInterfaceDTO.setContentKey("");
			esInterfaceDTO.setRtnCode("02");
			esInterfaceDTO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {
			return esInterfaceDTO;
		}
		
	}
	
	
	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO getContent(String esElementId) {
		EsResultDTO esResultDTO = new EsResultDTO();
		try {
			EsContentEntity esContentEntity = esContentRepository.findByEsContentId(esElementId);
			if(esContentEntity==null) {
				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("NODATA");
			}else {
				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsContentEntity(esContentEntity);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}

	

	@SuppressWarnings({ "null", "finally" })
	public EsInterfaceDTO updateEsContent(String esElementId,EsContentDTO esContentDTO) {
		EsInterfaceDTO esInterfaceDTO = null;
		try {
			//esElementId 로 기존 데이터 찾아줌
			EsContentEntity esContentEntity = entityManager.find(EsContentEntity.class, esElementId);
			if(esContentEntity==null) {
				esInterfaceDTO.setRtnCode("00");
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
					esInterfaceDTO.setRtnCode("01");
					esInterfaceDTO.setRtnMsg("SUCCESS");
				}else {
					esInterfaceDTO.setEsContentEntity(rtnEntity);
					esInterfaceDTO.setEsContentDTO(esContentDTO);
					esInterfaceDTO.setRtnCode("00");
					esInterfaceDTO.setRtnMsg("NOUPDATE");
					
				}
			}
			
			
		}catch(Exception e){

			esInterfaceDTO.setRtnCode("02");
			esInterfaceDTO.setRtnMsg("EXCEPTION:" + e.getMessage());
			e.printStackTrace();
			
			
		}finally {

		    return esInterfaceDTO;
		}
		
	}

	@SuppressWarnings({ "null", "finally" })
	public EsInterfaceDTO deleteEsContent(String esContentId) {
		long rtnCnt = 0;
		 EsInterfaceDTO esInterfaceDTO = null;
		try {
			 rtnCnt = esContentRepository.deleteByEsContentId(esContentId);
			 esInterfaceDTO.setContentKey("");
			 esInterfaceDTO.setRtnCode( rtnCnt==1 ? "01" : "00" );
			 esInterfaceDTO.setRtnMsg( rtnCnt==1 ? "SUCCESS" : "NODATA" );
			
		}catch(Exception e) {
			esInterfaceDTO.setContentKey("");
			esInterfaceDTO.setRtnCode("02");
			esInterfaceDTO.setRtnMsg("EXCEPTION:" + e.getMessage());
			e.printStackTrace();
			rtnCnt = -1;
		}finally {
			return esInterfaceDTO;
			
		}
	}
	
	
	//사용을 위한 함수항목

	@SuppressWarnings({ "null" })
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


	
	


