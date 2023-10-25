package com.eSonic.ecm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.eSonic.ecm.VO.EsVolumeVO;

@Mapper
public interface EsVolumeMapper {
	
	 
	EsVolumeVO getUsedVolume(EsVolumeVO esVolumeVO);
	
	int updateUsedVolume(EsVolumeVO esVolumeVO);
	 
}
