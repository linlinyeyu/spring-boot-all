
package com.ibenben.enumtuil;

/**
 * @author dfang
 *
 */
public enum StatusEnum {
	INIT("INIT","未审核"),
	OK("OK","已审核"),
	DELETE("DELETE","不可用"),
	APPLIED ("APPLIED","已申请"),
	PACKAGINGCREATED ("PACKAGINGCREATED","已创建包装方案"),
	PRODUCTCREATED ("PRODUCTCREATED","已创建成品"),
	PRODUCTCHECKED("PRODUCTCHECKED","成品已审核"),
	FINISH("FINISH","已完结");
	
	private String value;
	private String text;
	
	private StatusEnum(String value, String text){
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
		if(value == null){
			return null;
		}
		for (StatusEnum statusEnum : StatusEnum.values()) {
			if (statusEnum.getValue().equals(value.toUpperCase())) {
				return statusEnum.getText();
			}
		}
		return null;
	}
}
