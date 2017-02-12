package com.ibenben.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;

import java.lang.reflect.Field;


public class ParamsProcessUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ParamsProcessUtil.class);
	
	public static boolean checkMapParamsNotNull(Map<String, Object> params, String[] need_param_keys){
		if(params == null || need_param_keys == null){
			return false;
		}
		for(String key: need_param_keys){
			Object value = params.get(key);
			if(params.get(key) == null){
				return false;
			}
			if(value instanceof String && "".equals(value)){
				return false;
			}
		}
		return true;
	}
	
	public static void checkMapParamsNotNullAndThrows(Map<String, Object> params, String[] need_param_keys) throws MmsException{
		if(params == null || need_param_keys == null){
			throw new MmsException(ErrorCodeEnum.PARAMSEMPTYERROR);
		}
		for(String key: need_param_keys){
			Object value = params.get(key);
			if(params.get(key) == null){
				logger.error("缺少参数");
				logger.error(JSON.toJSONString(params));
				throw new MmsException("缺少参数：" + key);
			}
			if(value instanceof String && "".equals(value)){
				logger.error("缺少参数");
				logger.error(JSON.toJSONString(params));
				throw new MmsException(key + "为空字符串");
			}
		}
	}
	
	public static  boolean checkObjectPropertyNotNull(Object object, String[] need_param_keys) throws IllegalArgumentException, IllegalAccessException{
		if(object == null || need_param_keys == null){
			return false;
		}
		Field[] fields = object.getClass().getDeclaredFields();
		for(String property : need_param_keys){
			boolean unCheck = true; //需要检查的成员变量名未得到匹配
			for (Field field : fields) {
				field.setAccessible(true);
				String filedName = field.getName();
				if(filedName.equals(property)){
					Object obj = field.get(object);
					if(obj == null){
						return false;
					}
					if(obj instanceof String && "".equals(obj)){
						return false;
					}
					unCheck = false;
					break;
				}
			}
			if(unCheck){
				return false;
			}
		}
		return true;
	}
	
	public static void checkObjectPropertyNotNullAndThrows(Object object, String[] need_param_keys) throws IllegalArgumentException, IllegalAccessException, MmsException{
		if(object == null || need_param_keys == null){
			throw new MmsException(ErrorCodeEnum.PARAMSEMPTYERROR);
		}
		Field[] fields = object.getClass().getDeclaredFields();
		for(String property : need_param_keys){
			boolean unCheck = true; //需要检查的成员变量名未得到匹配
			for (Field field : fields) {
				field.setAccessible(true);
				String filedName = field.getName();
				if(filedName.equals(property)){
					Object obj = field.get(object);
					if(obj == null){
						logger.error("缺少参数");
						logger.error(JSON.toJSONString(object));
						throw new MmsException("缺少参数：" + property);
					}
					if(obj instanceof String && "".equals(obj)){
						logger.error("缺少参数");
						logger.error(JSON.toJSONString(object));
						throw new MmsException(property + "为空字符串");
					}
					unCheck = false;
					break;
				}
			}
			if(unCheck){
				logger.error("缺少参数");
				logger.error(JSON.toJSONString(object));
				throw new MmsException("缺少参数:" + property);
			}
		}
		
	}
	
}



