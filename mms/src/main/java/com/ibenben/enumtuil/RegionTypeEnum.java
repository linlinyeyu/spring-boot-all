/**
 * 
 */
package com.ibenben.enumtuil;

/**
 * @author sszheng
 *
 * Created on 2016年8月1日 下午8:39:37
 */
public enum RegionTypeEnum {
	NATION("NATION", "国家"), 
	PROVINCE("PROVINCE", "省"), 
	CITY("CITY", "市"), 
	DISTRICT("DISTRICT", "区（县）");
	
	private String text;
	private String value;
	
	private RegionTypeEnum(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
	
	public static String getText(String value) {
		for (RegionTypeEnum regionTypeEnum : RegionTypeEnum.values()) {
			if (regionTypeEnum.getValue().equals(value)) {
				return regionTypeEnum.getText();
			}
		}
		return null;
	}
}
