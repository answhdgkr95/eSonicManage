package com.eSonic.ecm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsInterfaceDTO {

	String contentKey;
	String rtnMsg;
	String rtnCode;
	EsContentDTO esContentDTO;
	EsContentEntity esContentEntity;
}
