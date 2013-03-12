/*
 * Copyright 2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bazzar.base.dao.redis;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

@Named
public class ItemRepository {

	@Autowired
	private RedisTemplate<String, Object> template;

	private final RedisAtomicLong jobIdCounter;

	@Inject
	public ItemRepository(RedisTemplate<String, Object> template) {
		this.template = template;

		if (template != null) {
			jobIdCounter = new RedisAtomicLong(KeyUtils.globalUid(),
			        template.getConnectionFactory());
		} else {
			jobIdCounter = null;
		}
	}

	public Long addJob(JSONObject json) {
		Long jobId = jobIdCounter.incrementAndGet();
		final Map<String, Object> jobParams = new HashMap<String, Object>();

		jobParams.put("id", jobId);
		jobParams.put("params", json.toString());
		jobParams.put("status", "pending");
		template.opsForHash().putAll(jobId.toString(), jobParams);

		return jobId;
	}

	public void updateJob(Map<Object, Object> jobParams) {
		template.opsForHash().putAll(jobParams.get("id").toString(), jobParams);
	}

	public Map<Object, Object> getJob(Long jobId) {
		return template.opsForHash().entries(jobId.toString());
	}

	public boolean isJobValid(Long jobId) {
		return template.hasKey(jobId.toString());
	}

	public Long sendJob(Long jobId) {
		return template.convertAndSend("pubsub:queue", jobId.toString());
	}

}