package com.eSonic.ecm.domain;


import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsContentDTO {

	  private String esContentId;
	  private String esArchiveId;
	  private String esVolumeId;
	  private String esFilePath;
	  private int esFileSize;
	  private Date esCreateDate;
	  private Date esLastAccess;
	  private String esCreateUser;
	  private String esLastAccessUser;
	  private String esFileExt;
	  private String esContentClassId;
	  private Date esWriteDate;
	
}
