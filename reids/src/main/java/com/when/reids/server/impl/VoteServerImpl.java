package com.when.reids.server.impl;

import javax.annotation.Resource;

import com.when.reids.common.Const;
import com.when.reids.server.IRedisServer;
import com.when.reids.server.IVoteServer;

public class VoteServerImpl implements IVoteServer {

	@Resource
	private IRedisServer redisServer;

	@Override
	public boolean addVote(String user, String article) {
		long expireTime = System.currentTimeMillis() - Const.ONE_WEEK_IN_MICROSECONDS;
		// 判断文章的投票截止时间，只能给一周之内的文章投票
		if (redisServer.zscore("time:", article) < expireTime) {
			return false;
		}
		String articleId = article.split(":")[1];
		// 如果用户是第一次为这篇文章头片，则增加这篇文章的投票数量和评分
		if (redisServer.sadd("voted:" + articleId, user)) {
			redisServer.zincyby("score:", article, Const.VOTE_SCORE);
			redisServer.hincrby(article, "votes", 1);
			return true;
		}

		return false;
	}

}
