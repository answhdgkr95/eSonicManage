package com.eSonic.ecm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eSonic.ecm.domain.EsVolumeEntity;


@Repository
public interface EsVolumeRepository extends JpaRepository<EsVolumeEntity, String>{
	
	EsVolumeEntity findByEsVolumeId(String esVolumeId);
	
	

	long deleteByEsVolumeId(String esElementId);
	
	
	


	

}
