package com.eSonic.ecm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "TB_ESONIC_VOL") // EsContentEntity 엔티티를 테이블 명 ESONICCONTENT로 매핑
public class EsVolumeEntity {
	@Id
	private String esVolumeId;
	private String esVolumeName;
	private int esVolumeUsesize;
	private int esVolumeLeftsize;
	private int esVolumeTotsize;
	private String esVolumeType;
	
	@Builder
	public  EsVolumeEntity (EsVolumeDTO esVolumeDTO){
		
		this.esVolumeId = esVolumeDTO.getEsVolumeId();
		this.esVolumeName = esVolumeDTO.getEsVolumeName();
		this.esVolumeUsesize = esVolumeDTO.getEsVolumeUsesize();
		this.esVolumeLeftsize = esVolumeDTO.getEsVolumeLeftsize();
		this.esVolumeTotsize = esVolumeDTO.getEsVolumeTotsize();
		this.esVolumeType = esVolumeDTO.getEsVolumeType();

	}

}
