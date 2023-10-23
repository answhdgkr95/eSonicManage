package com.eSonic.ecm.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsVolumeVO  {
	
	private String esArchiveId;
	private String esVolumeId;
	private String esArchiveName;
	private String esArchiveUsesize;
	private String esArchiveLeftsize;
	private String esArchiveEtc;
	
	private String esVolumeName;
	private String esVolumeUsesize;
	private String esVolumeLeftsize;
	private String esVolumeTotsize;
	private String esVolumeType;
	
	
	
	private String esFileSize;

	private String esCreateFileSize;
	private String esRemoveFileSize;
	
	private String esContentId;
}
