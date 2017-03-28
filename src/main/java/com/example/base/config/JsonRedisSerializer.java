package com.example.base.config;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRedisSerializer extends Jackson2JsonRedisSerializer<Object> {

	public JsonRedisSerializer() {
		super(Object.class);
		ObjectMapper om = new ObjectMapper();  
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
		setObjectMapper(om);
	}
}
