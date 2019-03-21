package com.when.reids.server.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.when.reids.server.IRedisServer;

public class RedisServerImpl implements IRedisServer {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Double zscore(String key, String member) {
		ZSetOperations<String, Object> operations = redisTemplate.opsForZSet();
		return operations.score(key, member);
	}

	@Override
	public boolean sadd(String key, String member) {
		SetOperations<String, Object> operations = redisTemplate.opsForSet();
		return operations.add(key, member) > 1L;
	}

	@Override
	public Double zincyby(String key, String member, double increment) {
		ZSetOperations<String, Object> operations = redisTemplate.opsForZSet();
		return operations.incrementScore(key, member, increment);
	}

	@Override
	public Double hincrby(String key, String field, double increment) {
		HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
		return operations.increment(key, field, increment);
	}

}
