package com.eSonic.ecm.repository;

import org.springframework.data.repository.CrudRepository;

import com.eSonic.ecm.domain.RedisEntity;

public interface RedisRepository  extends CrudRepository<RedisEntity, Long>{

}
