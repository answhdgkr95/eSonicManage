package com.eSonic.ecm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eSonic.ecm.domain.EsUserDTO;
import com.eSonic.ecm.domain.EsUserEntity;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.repository.EsUserRepository;

import jakarta.persistence.EntityManager;

@Service
public class EsUserService {
	

	private final EntityManager entityManager = null;
	@Autowired
	private EsUserRepository esUserRepository;
	
	public String getUser(String esUserId) {
		String rtnPassword = "";
		try {
			EsUserEntity esUserEntity = esUserRepository.findByEsUserId(esUserId);
			if(esUserEntity ==null) {

				rtnPassword = "NODATA : 0";
			}else {

				rtnPassword = "SUCCESS : " + esUserEntity.getEsUserPw();
			}
			
		}catch(Exception e) {
			rtnPassword = "EXCEPTION : " + e.getMessage();
		}
		finally {

			return rtnPassword;
		}
		
		
		
	}
	
	public EsResultDTO getUserList(EsUserDTO esUserDTO) {
		EsResultDTO esResultDTO = null;
		try {
			List<EsUserEntity> esUserEntity = esUserRepository.findAll();
			if(esUserEntity==null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
				esResultDTO.setEsUserEntityList(esUserEntity);
				
			}else {
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsUserEntityList(esUserEntity);
				
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("2");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}	}
	
	public EsResultDTO esUserInsert(EsUserDTO esUserDTO) {
		EsResultDTO esResultDTO = null;
		try {
			EsUserEntity esUserEntity = esUserRepository.findByEsUserId(esUserDTO.getEsUserId());
			if(esUserEntity !=null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("ALREADYDATA");
			}else {
				esUserEntity  = EsUserEntity.builder().esUserDTO(esUserDTO).build();
				esUserEntity = esUserRepository.save(esUserEntity);
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("2");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {
			return esResultDTO;
		}
	}
	
	public EsResultDTO esUserUpdatePut(String esUserId, EsUserDTO esUserDTO) {
		EsResultDTO esResultDTO = null;
		try {

			//esElementId 로 기존 데이터 찾아줌
			EsUserEntity esUserEntity = entityManager.find(EsUserEntity.class, esUserId);
			if(esUserEntity ==null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
			}else {
				//DTO에없는 값을 조회한 값과 합침
				esUserDTO = setUserDTO(esUserEntity, esUserDTO);
				//Entity 에 build 함으로 값 셋팅
				esUserEntity  = EsUserEntity.builder().esUserDTO(esUserDTO).build();
				//저장(없으면 insert)
				esUserEntity = esUserRepository.save(esUserEntity);
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
			}
			
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("2");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}
	
	public EsResultDTO esUserUpdatePatch(String esUserId, EsUserDTO esUserDTO) {
		EsResultDTO esResultDTO = null;
		try {

			//esElementId 로 기존 데이터 찾아줌
			EsUserEntity esUserEntity = entityManager.find(EsUserEntity.class, esUserId);
			if(esUserEntity==null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
			}else {
				//DTO에없는 값을 조회한 값과 합침
				EsUserDTO INesUserDTO = setUserDTO(esUserEntity, esUserDTO);
				//Entity 에 build 함으로 값 셋팅
				esUserEntity  = EsUserEntity.builder().esUserDTO(INesUserDTO).build();
				//저장(없으면 insert)
				esUserEntity = esUserRepository.save(esUserEntity);
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
			}
			
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("2");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}
	
	public EsResultDTO esUserDelete(String esUserId) {
		EsResultDTO esResultDTO = null;
		try {
			if(0 != esUserRepository.deleteByEsUserId(esUserId)) {
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
			}else {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("2");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}
	
	
	
	
	
	//사용을 위한 함수항목
	
	//DTO에 없는 내용을 조회한 Entity 와 합쳐주기 위한 함수
	public EsUserDTO setUserDTO(EsUserEntity INesUserEntity,EsUserDTO OUTesUserDTO ) {
		if(check(OUTesUserDTO.getEsUserId()))OUTesUserDTO.setEsUserId(INesUserEntity.getEsUserId());
		if(check(OUTesUserDTO.getEsUserName()))OUTesUserDTO.setEsUserName(INesUserEntity.getEsUserName());
		if(check(OUTesUserDTO.getEsUserPw()))OUTesUserDTO.setEsUserPw(INesUserEntity.getEsUserPw());
		if(check(OUTesUserDTO.getEsInsertUser()))OUTesUserDTO.setEsInsertUser(INesUserEntity.getEsInsertUser());
		if(check(OUTesUserDTO.getEsInsertDt().toString()))OUTesUserDTO.setEsInsertDt(INesUserEntity.getEsInsertDt());
		
		return OUTesUserDTO;
	}
	
	public boolean check(String checkedValue) {
		return checkedValue == "" || checkedValue == null ? true : false;
	}
	
}


	
	


