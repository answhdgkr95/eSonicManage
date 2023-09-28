package com.eSonic.ecm.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsUserDTO {
	private String esUserId;

	private String esUserName;

	private String esUserPw;
	
	private Date esInsertDt;

	private Date esWriteDate;
	
	private String esInsertUser;
	
	private String esUpdateUser;
	
	private String esEtc;
	
	

}
