package com.ibenben.util;

import java.util.ArrayList;
import java.util.List;


public class ListUtil {
	public static <T> List<List<T>> chunkList(List<T> list, int size){
		List<List<T>> newList = new ArrayList<List<T>>();
		if(list != null && list.size() > 0){
			int count = list.size() / size;
			int extra = list.size() % size;
			for(int i = 0; i < count; i++){
				List<T> sub = list.subList(i * size, (i + 1) * size);
				newList.add(sub);
			}
			if(extra > 0){
				List<T> sub = list.subList(count * size, count * size + extra);
				newList.add(sub);
			}
		}
		return newList;
	} 
	
	public static<T> List<T> intersect(List<T> aList, List<T> bList){
		List<T> result = new ArrayList<T>();
		for (T t : bList) {
			if(aList.contains(t)){
				result.add(t);
			}
		}
		return result;
	}
	
	public static<T> List<T> subtract(List<T> aList, List<T> bList){
		List<T> result = new ArrayList<T>();
		for (T t : aList) {
			if(!bList.contains(t)){
				result.add(t);
			}
		}
		return result;
	}
}
