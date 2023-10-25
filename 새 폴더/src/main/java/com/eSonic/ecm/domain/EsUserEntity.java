package com.eSonic.ecm.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_ESONIC_USER") // EsContentEntity 엔티티를 테이블 명 ESONICCONTENT로 매핑
public class EsUserEntity {
	@Id
	private String esUserId;
	private String esUserName;
	private String esUserPw;
	@Temporal(TemporalType.TIMESTAMP)
	private Date esInsertDt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date esWriteDate;
	private String esInsertUser;
	private String esUpdateUser;
	private String esEtc;

	@Builder
	public EsUserEntity (EsUserDTO esUserDTO){
		this.esUserId = esUserDTO.getEsUserId();
		esUserName = esUserDTO.getEsUserName();
		esUserPw = esUserDTO.getEsUserPw();
		esInsertDt = esUserDTO.getEsInsertDt();
		esWriteDate = esUserDTO.getEsWriteDate();
		esInsertUser = esUserDTO.getEsInsertUser();
		esUpdateUser = esUserDTO.getEsUpdateUser();
		esEtc = esUserDTO.getEsEtc();
	}
	

}
