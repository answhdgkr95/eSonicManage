package com.eSonic.ecm.VO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsContentVO {
	  private String esContentId;
	  private String esArchiveId;
	  private String esFilePath;
	  private long esFileSize;
	  private Date esCreateDate;
	  private Date esLastAccess;
	  private String esCreateUser;
	  private String esLastAccessUser;
	  private String esFileExt;
	  private String esContentClassId;
	  private Date esWriteDate;
}
