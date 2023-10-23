package com.eSonic.ecm.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "TB_ESONIC_ARCHIVE") // EsContentEntity 엔티티를 테이블 명 ESONICCONTENT로 매핑
public class EsArchiveEntity {
	public EsArchiveEntity (){

	}
	@Id
	private String esArchiveId;
	private String esArchiveName;
	private double esArchiveUsesize;
	private double esArchiveLeftsize;
	private String esArchiveEtc;

	
	@Builder
	public EsArchiveEntity (EsArchiveDTO esArchiveDTO){
		this.esArchiveId = esArchiveDTO.getEsArchiveId();
		this.esArchiveName = esArchiveDTO.getEsArchiveName();
		this.esArchiveUsesize = esArchiveDTO.getEsArchiveUsesize();
		this.esArchiveLeftsize = esArchiveDTO.getEsArchiveLeftsize();
		this.esArchiveEtc = esArchiveDTO.getEsArchiveEtc();

	}
}
