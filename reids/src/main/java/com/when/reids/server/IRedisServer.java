package com.when.reids.server;

public interface IRedisServer {

	Double zscore(String key, String member);

	boolean sadd(String key, String member);

	Double zincyby(String key, String member, double increment);

	Double hincrby(String key, String field, double increment);

}
