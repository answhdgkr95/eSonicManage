package com.eSonic.ecm.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsResultDTO {
	String rtnCd;
	String rtnMsg;
	
	
	
	EsContentDTO esContentDTO;
	List<EsContentDTO> esContentDTOList;
	EsUserDTO esUserDTO;
	List<EsUserDTO> esUserDTOList;
	EsArchiveDTO esArchiveDTO;
	List<EsArchiveDTO> esArchiveDTOList;
	EsVolumeDTO esVolumeDTO;
	List<EsVolumeDTO> esVolumeDTOList;
	

	EsContentEntity esContentEntity;
	List<EsContentEntity> EsContentEntityList;
	EsUserEntity esUserEntity;
	List<EsUserEntity> esUserEntityList;
	EsArchiveEntity esArchiveEntity;
	List<EsArchiveEntity> EsArchiveEntityList;
	EsVolumeEntity esVolumeEntity;
	List<EsVolumeEntity> EsVolumeEntityList;

}
