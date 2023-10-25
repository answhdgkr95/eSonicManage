package com.eSonic.ecm.domain;

import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Getter;


@Getter
@RedisHash(value = "post")
public class RedisEntity {
    @Id
    private Long id;

    private String title;

    private String content;

    public RedisEntity(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    
}
