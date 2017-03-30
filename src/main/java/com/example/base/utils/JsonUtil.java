package com.example.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {
	
	/**
	 * 会把string类型格式化成空字符串 
	 */
	public static String toJsonString(Object object) {
		return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue, 
				SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.QuoteFieldNames,SerializerFeature.WriteNullNumberAsZero);
	}
	
	public static <T> T toObject(String json, Class<T> clazz) {
		return JSONObject.parseObject(json, clazz);
	}
	
	public static <T> T toObject(String json, TypeReference<T> type) {
		return JSONObject.parseObject(json, type);
	}
}
