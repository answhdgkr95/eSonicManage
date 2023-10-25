package com.eSonic.ecm.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsVolumeVO  {
	
	private String esArchiveId;
	private String esVolumeId;
	private String esArchiveName;
	private double esArchiveUsesize;
	private double esArchiveLeftsize;
	private String esArchiveEtc;
	
	private String esVolumeName;
	private int esVolumeUsesize;
	private int esVolumeLeftsize;
	private int esVolumeTotsize;
	private String esVolumeType;
	
	
	private long esFileSize;
	
}
