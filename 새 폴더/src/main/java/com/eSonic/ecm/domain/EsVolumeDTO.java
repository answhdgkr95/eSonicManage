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
public class EsVolumeDTO {
	private String esVolumeId;
	private String esVolumeName;
	private int esVolumeUsesize;
	private int esVolumeLeftsize;
	private int esVolumeTotsize;
	private String esVolumeType;
	
	

}
