package com.ibenben.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibenben.enumtuil.ErrorCodeEnum;

public class ResponseFormatUtil{
	public static Map<String, Object> formatResponse(){
		Map<String, Object> map = null ;
		return formatResponse(map);
	}
	
	public static <T> Map<String, Object> formatResponse(List<T> list){
		Map<String, Object> map = new HashMap<String, Object>();
		if(list != null){
			map.put("list", list);
		}
		map.put("result", "ok");
		return map;
	}
	
	public static Map<String, Object> formatResponse(Map<String, Object> map){
		if(map == null){
			map = new HashMap<String, Object>();
		}
		if(map.get("error") != null && map.get("error") instanceof ErrorCodeEnum) {
			ErrorCodeEnum ece = (ErrorCodeEnum) map.get("error");
			HashMap<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("error_code", ece.getValue());
			errorMap.put("error_message", ece.getText());
			errorMap.put("result", "fail");
			return errorMap;
		}
		
		if(map.get("error") != null && map.get("error") instanceof String) {
			HashMap<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("error_code", "");
			errorMap.put("error_message", map.get("error"));
			errorMap.put("result", "fail");
			return errorMap;
		}
		
		map.put("result", "ok");
		return map;
	}
	
	public static <T> Map<String, Object> formatResponseDomain(T t) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("entity", t);
		map.put("result", "ok");
		return map;
	}
}
