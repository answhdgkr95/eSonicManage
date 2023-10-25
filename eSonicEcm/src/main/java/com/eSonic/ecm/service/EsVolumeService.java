package com.eSonic.ecm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eSonic.ecm.domain.EsVolumeDTO;
import com.eSonic.ecm.domain.EsVolumeEntity;
import com.eSonic.ecm.mapper.EsVolumeMapper;
import com.eSonic.ecm.VO.EsResultVO;
import com.eSonic.ecm.VO.EsVolumeVO;
import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.repository.EsVolumeRepository;

import jakarta.persistence.EntityManager;

@Service
public class EsVolumeService {
	EsVolumeRepository esVolumeRepository;
	
	private final EntityManager entityManager = null;
	

	//Mybatis

    private final EsVolumeMapper esVolumeMapper;
    public EsVolumeService(EsVolumeMapper esVolumeMapper) {
        this.esVolumeMapper = esVolumeMapper;
    }
    

	@SuppressWarnings({ "finally" })
	public EsResultVO getUsedVolume(EsVolumeVO esVolumeVO) {
		
		EsResultVO esResultVO = new EsResultVO();
		try {
			EsVolumeVO esVolumeRtnVO = esVolumeMapper.getUsedVolume(esVolumeVO);
			esResultVO.setEsVolumeVO(esVolumeRtnVO);
			if(esVolumeRtnVO ==null) {
				esResultVO.setRtnCd("00");
				esResultVO.setRtnMsg("NODATA");
			}else {

				esResultVO.setRtnCd("01");
				esResultVO.setRtnMsg("SUCCESS");
			}
			
		}catch(Exception e) {
			esResultVO.setRtnCd("02");
			esResultVO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultVO;
		}
		
		
	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultVO updateVolume(EsVolumeVO esVolumeVO) {
		EsResultVO esResultVO = new EsResultVO();
		try {
//			//남은용량이 얼마나 있는지 체크해야함 
//			if(Integer.parseInt(esVolumeMapper.checkVolume(esVolumeVO)) < 0) {
//				
//				//잔여스토리지를 찾아서 다시 해야할거같은데???
//
//				esResultVO.setRtnCd("03");
//				esResultVO.setRtnMsg("NOSPACE : " + esVolumeVO.getEsVolumeId());
//				return esResultVO;
//			}
			int i = esVolumeMapper.updateVolume(esVolumeVO);
			
			
			
			if(i ==0) {
				esResultVO.setRtnCd("00");
				esResultVO.setRtnMsg("NODATA");
			}else {

				esResultVO.setRtnCd("01");
				esResultVO.setRtnMsg("SUCCESS");
			}
			
		}catch(Exception e) {
			esResultVO.setRtnCd("02");
			esResultVO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultVO;
		}
		
	}
	
	@SuppressWarnings({ "null", "finally" })
	public EsResultVO updateVolumeReplace(EsVolumeVO esVolumeVO) {
		EsResultVO esResultVO = new EsResultVO();
		try {
			System.out.println("volume : " + esVolumeMapper.checkVolume(esVolumeVO));
			//남은용량이 얼마나 있는지 체크해야함 
			if(Integer.parseInt(esVolumeMapper.checkVolume(esVolumeVO)) < 0) {
				
				//잔여스토리지를 찾아서 다시 해야할거같은데???
				esResultVO.setRtnCd("03");
				esResultVO.setRtnMsg("NOSPACE : " + esVolumeVO.getEsVolumeId());
				return esResultVO;
			}
			int i = esVolumeMapper.updateVolume(esVolumeVO);
			
			
			
			if(i ==0) {
				esResultVO.setRtnCd("00");
				esResultVO.setRtnMsg("NODATA");
			}else {

				esResultVO.setRtnCd("01");
				esResultVO.setRtnMsg("SUCCESS");
			}
			
		}catch(Exception e) {
			esResultVO.setRtnCd("02");
			esResultVO.setRtnMsg("EXCEPTION");
			e.printStackTrace();
		}
		finally {

			return esResultVO;
		}
		
	}
	
	
	public int updateUsedVolume(EsVolumeVO esVolumeVO) {
		return esVolumeMapper.updateVolume(esVolumeVO);
	}
	
	
	
	
	//JPA
	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO getVolume(String esVolumeId) {

		EsResultDTO esResultDTO = null;
		try {
			EsVolumeEntity esVolumeEntity = esVolumeRepository.findByEsVolumeId(esVolumeId);
			if(esVolumeEntity==null) {

				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
			}else {

				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsVolumeEntity(esVolumeEntity);
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

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO getVolumeList(EsVolumeDTO esVolumeDTO) {
		EsResultDTO esResultDTO = null;
		try {
			List<EsVolumeEntity> esVolumeEntityList = esVolumeRepository.findAll();
			if(esVolumeEntityList==null) {

				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
			}else {

				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCCESS");
				esResultDTO.setEsVolumeEntityList(esVolumeEntityList);
			}
			
		}catch(Exception e) {
			esResultDTO.setRtnCd("2");
			esResultDTO.setRtnMsg(e.getMessage());
			e.printStackTrace();
		}
		finally {

			return esResultDTO;
		}	}

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esVolumeInsert(EsVolumeDTO esVolumeDTO) {
		EsResultDTO esResultDTO = null;
		try {
			EsVolumeEntity esVolumeEntity  = EsVolumeEntity.builder().esVolumeDTO(esVolumeDTO).build();
			if(esVolumeEntity!=null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("ALREADYDATA");
			}else {
				esVolumeEntity = esVolumeRepository.save(esVolumeEntity);
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

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esVolumeUpdatePut(String esVolumeId, EsVolumeDTO esVolumeDTO) {
		EsResultDTO esResultDTO = null;
		try {

			//esElementId 로 기존 데이터 찾아줌
			EsVolumeEntity esVolumeEntity = entityManager.find(EsVolumeEntity.class, esVolumeId);
			if(esVolumeEntity == null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
				
			}else {
				//DTO에없는 값을 조회한 값과 합침
				esVolumeDTO = setVolumeDTO(esVolumeEntity, esVolumeDTO);
				//Entity 에 build 함으로 값 셋팅
				esVolumeEntity  = EsVolumeEntity.builder().esVolumeDTO(esVolumeDTO).build();
				//저장(없으면 insert)
				esVolumeEntity = esVolumeRepository.save(esVolumeEntity);
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

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esVolumeUpdatePatch(String esVolumeId, EsVolumeDTO esVolumeDTO) {
		EsResultDTO esResultDTO = null;
		try {

			//esElementId 로 기존 데이터 찾아줌
			EsVolumeEntity esVolumeEntity = entityManager.find(EsVolumeEntity.class, esVolumeId);
			if(esVolumeEntity == null) {
				esResultDTO.setRtnCd("0");
				esResultDTO.setRtnMsg("NODATA");
				
			}else {
				//DTO에없는 값을 조회한 값과 합침
				esVolumeDTO = setVolumeDTO(esVolumeEntity, esVolumeDTO);
				//Entity 에 build 함으로 값 셋팅
				esVolumeEntity  = EsVolumeEntity.builder().esVolumeDTO(esVolumeDTO).build();
				//저장(없으면 insert)
				esVolumeEntity = esVolumeRepository.save(esVolumeEntity);
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

	@SuppressWarnings({ "null", "finally" })
	public EsResultDTO esVolumeDelete(String esVolumeId) {
		EsResultDTO esResultDTO = null;
		try {
			if(0!=esVolumeRepository.deleteByEsVolumeId(esVolumeId)) {
				esResultDTO.setRtnCd("1");
				esResultDTO.setRtnMsg("SUCESS");
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
	@SuppressWarnings({ "null" })
	public EsVolumeDTO setVolumeDTO(EsVolumeEntity INesVolumeEntity,EsVolumeDTO OUTesVolumeDTO ) {
		if(INesVolumeEntity== null) {
			OUTesVolumeDTO.setEsVolumeId(INesVolumeEntity.getEsVolumeId());
			OUTesVolumeDTO.setEsVolumeId(INesVolumeEntity.getEsVolumeId());
			OUTesVolumeDTO.setEsVolumeName(INesVolumeEntity.getEsVolumeName());
			OUTesVolumeDTO.setEsVolumeTotsize(INesVolumeEntity.getEsVolumeTotsize());
			OUTesVolumeDTO.setEsVolumeUsesize(INesVolumeEntity.getEsVolumeUsesize());
			OUTesVolumeDTO.setEsVolumeLeftsize(INesVolumeEntity.getEsVolumeLeftsize());
		
		}else {
			if(check(OUTesVolumeDTO.getEsVolumeId()))OUTesVolumeDTO.setEsVolumeId(INesVolumeEntity.getEsVolumeId());
			if(check(OUTesVolumeDTO.getEsVolumeId()))OUTesVolumeDTO.setEsVolumeId(INesVolumeEntity.getEsVolumeId());
			if(check(OUTesVolumeDTO.getEsVolumeName()))OUTesVolumeDTO.setEsVolumeName(INesVolumeEntity.getEsVolumeName());
			if(OUTesVolumeDTO.getEsVolumeTotsize() == 0)OUTesVolumeDTO.setEsVolumeTotsize(INesVolumeEntity.getEsVolumeTotsize());
			if(OUTesVolumeDTO.getEsVolumeUsesize() == 0)OUTesVolumeDTO.setEsVolumeUsesize(INesVolumeEntity.getEsVolumeUsesize());
			if(OUTesVolumeDTO.getEsVolumeLeftsize() == 0)OUTesVolumeDTO.setEsVolumeLeftsize(INesVolumeEntity.getEsVolumeLeftsize());

		}
		return OUTesVolumeDTO;
	}
	
	public boolean check(String checkedValue) {
		return checkedValue == "" || checkedValue == null ? true : false;
	}
	
	
	
	
	
	
}


	
	


