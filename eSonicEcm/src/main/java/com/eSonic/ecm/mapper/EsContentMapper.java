package com.eSonic.ecm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.eSonic.ecm.VO.EsContentVO;


@Mapper
public interface EsContentMapper {
	
	 
	
	int insertEsContentMyBatis(EsContentVO esContentVO);
	
	int updateEsContentMyBatis(EsContentVO esContentVO);
	
}
