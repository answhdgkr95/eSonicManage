package com.eSonic.ecm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // BaseEntity클래스의 AuditingEntityListener를 활성화 시키기 위해 추가한다
public class ESonicEcmApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ESonicEcmApplication.class, args);
	}

}
