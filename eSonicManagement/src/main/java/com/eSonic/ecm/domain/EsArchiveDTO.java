package com.eSonic.ecm.domain;

import java.sql.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsArchiveDTO {

	private String esArchiveId;
	private String esVolumeId;
	private String esArchiveName;
	private double esArchiveUsesize;
	private double esArchiveLeftsize;
	private String esArchiveEtc;
	
}
