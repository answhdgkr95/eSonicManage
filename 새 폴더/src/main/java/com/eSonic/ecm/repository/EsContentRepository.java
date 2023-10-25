package com.eSonic.ecm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eSonic.ecm.domain.EsContentEntity;


@Repository
public interface EsContentRepository extends JpaRepository<EsContentEntity, String>{
	
	EsContentEntity findByEsContentId(String esContentId);
	
	long deleteByEsContentId(String esContentId);
	
	
	
	


	

}
