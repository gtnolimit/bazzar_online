package com.bazzar.base.message.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisMessageListener implements MessageListener {
	@Override
	public void onMessage(Message jobId, byte[] pattern) {
		System.out.println("Message received: " + jobId);
	}
}