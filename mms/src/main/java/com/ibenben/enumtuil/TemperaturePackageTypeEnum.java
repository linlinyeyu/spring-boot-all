package com.ibenben.enumtuil;

public enum TemperaturePackageTypeEnum {
	ROOMTEMPERATUREBOXPACKAGE("room_temperature_box_package","常温箱装"),
	ROOMTEMPERATURELOOSEPACKAGE("room_temperature_loose_package","常温裸包"),
	ROOMTEMPERATUREORIGINALPACKAGE("room_temperature_original_package","常温原装"),
	HEATPRESERVATIONLOOSEPACKAGE("heat_preservation_loose_package","保温裸包"),
	HEATPRESERVATIONBOXPACKAGE("heat_preservation_box_package","保温箱装"),
	HEATPRESERVATIONORIGINALPACKAGE("heat_preservation_original_package","保温原装");
	private String text;
	private String value;
	private TemperaturePackageTypeEnum(String value, String text) {
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
		for (TemperaturePackageTypeEnum temperaturePackageTypeEnum : TemperaturePackageTypeEnum.values()) {
			if (temperaturePackageTypeEnum.getValue().equals(value)) {
				return temperaturePackageTypeEnum.getText();
			}
		}
		return null;
	}
}
