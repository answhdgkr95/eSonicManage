package com.eSonic.ecm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eSonic.ecm.domain.EsArchiveEntity;

@Repository
public interface EsArchiveRepository extends JpaRepository<EsArchiveEntity, String>{
	
	EsArchiveEntity findByEsArchiveId(String esArchiveId);
	
	long deleteByEsArchiveId(String esArchiveId);
	
	
	


	

}
