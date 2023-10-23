package com.eSonic.ecm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eSonic.ecm.domain.EsUserEntity;


@Repository
public interface EsUserRepository extends JpaRepository<EsUserEntity, String>{
	
	EsUserEntity findByEsUserId(String esUserId);

	long deleteByEsUserId(String esUserId);


	

}
