package com.ibenben.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

public class StringUtil {
	public static boolean isStringNullOrEmpty(String s){
		if(s == null || s.length() == 0){
			return true;
		}
		return false;
	}
	
	public static List<Integer> CollStringToIntegerLst(List<String> inList){
        List<Integer> iList =new ArrayList<Integer>(inList.size());
        CollectionUtils.collect(inList, new Transformer(){
        	public java.lang.Object transform(java.lang.Object input){
        		return new Integer((String)input);
        	}
        }, iList );
        return iList;
    }
}
