package com.eSonic.ecm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.eSonic.ecm.domain.EsArchiveDTO;
import com.eSonic.ecm.domain.EsArchiveEntity;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.repository.EsArchiveRepository;

import jakarta.persistence.EntityManager;

@Service
public class EsArchiveService {
	EsArchiveRepository esArchiveRepository;
	
	private final EntityManager entityManager = null;
	
	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO getArchive(String esArchiveId) {

		EsResultDTO esResultDTO = null;
		try {
			EsArchiveEntity esArchiveEntity = null;
			esArchiveEntity = esArchiveRepository.findByEsArchiveId(esArchiveId);
			if(esArchiveEntity == null) {

				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("NODATA");
			}else {
				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsArchiveEntity(esArchiveEntity);
				
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
		
		
		
	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO getArchiveList(EsArchiveDTO esArchiveDTO) {
		EsResultDTO esResultDTO = null;
		try {
			List<EsArchiveEntity> esArchiveEntityList = esArchiveRepository.findAll();
			if(esArchiveEntityList == null) {

				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("NODATA");
			}else {

				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsArchiveEntityList(esArchiveEntityList);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esArchiveInsert(EsArchiveDTO esArchiveDTO) {
		EsResultDTO esResultDTO = null;
		try {

			//esElementId 로 기존 데이터 찾아줌
			EsArchiveEntity esArchiveEntity = entityManager.find(EsArchiveEntity.class, esArchiveDTO.getEsArchiveId());
			if(esArchiveEntity!=null) {

				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("ALREADYDATA");
			}else {

				 esArchiveEntity  = EsArchiveEntity.builder().esArchiveDTO(esArchiveDTO).build();
				esArchiveEntity = esArchiveRepository.save(esArchiveEntity);
				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsArchiveDTO(esArchiveDTO);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esArchiveUpdatePut(String esArchiveId, EsArchiveDTO esArchiveDTO) {
		EsResultDTO esResultDTO = null;
		try {

			//esElementId 로 기존 데이터 찾아줌
			EsArchiveEntity esArchiveEntity = entityManager.find(EsArchiveEntity.class, esArchiveId);
			if(esArchiveEntity ==null) {

				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("NODATA");
			}else {

				//DTO에없는 값을 조회한 값과 합침
				esArchiveDTO = setArchiveDTO(esArchiveEntity, esArchiveDTO);
				//Entity 에 build 함으로 값 셋팅
				esArchiveEntity  = EsArchiveEntity.builder().esArchiveDTO(esArchiveDTO).build();
				//저장(없으면 insert)
				esArchiveEntity = esArchiveRepository.save(esArchiveEntity);
				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsArchiveDTO(esArchiveDTO);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg("EXCEPTION");
			esResultDTO.setEsArchiveDTO(esArchiveDTO);
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esArchiveUpdatePatch(String esArchiveId, EsArchiveDTO esArchiveDTO) {
		EsResultDTO esResultDTO = null;
		try {


			//esElementId 로 기존 데이터 찾아줌
			EsArchiveEntity esArchiveEntity = entityManager.find(EsArchiveEntity.class, esArchiveId);
			if(esArchiveEntity ==null) {

				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("NODATA");
			}else {

				//DTO에없는 값을 조회한 값과 합침
				esArchiveDTO = setArchiveDTO(esArchiveEntity, esArchiveDTO);
				//Entity 에 build 함으로 값 셋팅
				esArchiveEntity  = EsArchiveEntity.builder().esArchiveDTO(esArchiveDTO).build();
				//저장(없으면 insert)
				esArchiveEntity = esArchiveRepository.save(esArchiveEntity);
				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsArchiveDTO(esArchiveDTO);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esArchiveDelete(String esArchiveId) {
		EsResultDTO esResultDTO = null;
		try {
			if(0!=esArchiveRepository.deleteByEsArchiveId(esArchiveId)) {
				esResultDTO.setRtnCd("01");
				esResultDTO.setRtnMsg("SUCCESS");
			}else {
				esResultDTO.setRtnCd("00");
				esResultDTO.setRtnMsg("NODATA");
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("02");
			esResultDTO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}
	}
	
	
	
	
	
	//사용을 위한 함수항목
	
	//DTO에 없는 내용을 조회한 Entity 와 합쳐주기 위한 함수
	@SuppressWarnings({ "null" })
	public EsArchiveDTO setArchiveDTO(EsArchiveEntity INesArchiveEntity,EsArchiveDTO OUTesArchiveDTO ) {
		if(INesArchiveEntity== null) {
			OUTesArchiveDTO.setEsArchiveId(INesArchiveEntity.getEsArchiveId());
			//OUTesArchiveDTO.setEsVolumeId(INesArchiveEntity.getEsVolumeId());
			OUTesArchiveDTO.setEsArchiveName(INesArchiveEntity.getEsArchiveName());
			OUTesArchiveDTO.setEsArchiveUsesize(INesArchiveEntity.getEsArchiveUsesize());
			OUTesArchiveDTO.setEsArchiveLeftsize(INesArchiveEntity.getEsArchiveLeftsize());
		
		}else {
			if(check(OUTesArchiveDTO.getEsArchiveId()))OUTesArchiveDTO.setEsArchiveId(INesArchiveEntity.getEsArchiveId());
			//if(check(OUTesArchiveDTO.getEsVolumeId()))OUTesArchiveDTO.setEsVolumeId(INesArchiveEntity.getEsVolumeId());
			if(check(OUTesArchiveDTO.getEsArchiveName()))OUTesArchiveDTO.setEsArchiveName(INesArchiveEntity.getEsArchiveName());
			if(OUTesArchiveDTO.getEsArchiveUsesize() == 0)OUTesArchiveDTO.setEsArchiveUsesize(INesArchiveEntity.getEsArchiveUsesize());
			if(OUTesArchiveDTO.getEsArchiveLeftsize() == 0)OUTesArchiveDTO.setEsArchiveLeftsize(INesArchiveEntity.getEsArchiveLeftsize());

		}
		return OUTesArchiveDTO;
	}
	
	public boolean check(String checkedValue) {
		return checkedValue == "" || checkedValue == null ? true : false;
	}
	
	
	
	
	
}


	
	


