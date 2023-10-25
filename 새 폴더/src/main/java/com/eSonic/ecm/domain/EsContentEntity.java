package com.eSonic.ecm.domain;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_ESONIC_CONT") // EsContentEntity 엔티티를 테이블 명 ESONICCONTENT로 매핑
public class EsContentEntity {
	@Id // PK
	private String esContentId;
	private String esArchiveId;
	private String esVolumeId;
	private String esFilePath;
	private int esFileSize;
	@Temporal(TemporalType.TIMESTAMP)
	private Date esCreateDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date esLastAccess;
	private String esCreateUser;
	private String esLastAccessUser;
	private String esFileExt;
	private String esContentClassId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date esWriteDate;

	@Builder
	public EsContentEntity(EsContentDTO esContentDTO) {
		this.esContentId = esContentDTO.getEsContentId();
		this.esArchiveId = esContentDTO.getEsArchiveId();
		this.esVolumeId = esContentDTO.getEsVolumeId();
		this.esFilePath = esContentDTO.getEsFilePath();
		this.esFileSize = esContentDTO.getEsFileSize();
		this.esCreateDate = esContentDTO.getEsCreateDate();
		this.esLastAccess = esContentDTO.getEsLastAccess();
		this.esCreateUser = esContentDTO.getEsCreateUser();
		this.esLastAccessUser = esContentDTO.getEsLastAccessUser();
		this.esFileExt = esContentDTO.getEsFileExt();
		this.esContentClassId = esContentDTO.getEsContentClassId();
		this.esWriteDate = esContentDTO.getEsWriteDate();
	}


//	  @Column(name = "name")
//	  private String username;
//
//	  private Integer age;

//	  //자바의  enum을 사용해서 회원의 타입을 구분하였습니다. 일반 회원은 USER, 관리자는
//	  //ADMIN입니다. 자바의 enum을 사용하려면 @Enumerated 어노테이션으로 매핑해야 합니다.
//	  @Enumerated(EnumType.STRING)
//	  private RoleType roleType;
//
//	  //자바의 날짜 타입은 @Temporal을 사용해서 매핑해야 합니다.
//	  @Temporal(TemporalType.TIMESTAMP)
//	  private Date createDate;
//
//	  @Temporal(TemporalType.TIMESTAMP)
//	  private Date lastModifiedDate;
//
//	  
//	//회원을 설명하는 필드는 길이 제한이 없고, 따라서 데이터베이스 varchar 타입 대신에
//	//CLOB 타입으로 저장해야 합니다. @Lob를 사용한다면 CLOB, BLOB 타입을 매핑할 수 있습니다.
//	  @Lob
//	  private String description;
//
//	  
//	  //이러면 변수명 바뀌어도 컬럼명 안바뀜
//	  @Transient
//	  private int temp;
//
//	  public enum RoleType{
//		    ADMIN, USER
//		}
}
