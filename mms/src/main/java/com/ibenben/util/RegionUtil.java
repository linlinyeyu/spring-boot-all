package com.ibenben.util;

import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.enumtuil.RegionTypeEnum;
import com.ibenben.exception.MmsException;

public class RegionUtil {
	public static String getRegionTypeByOutRegionType(byte outRegionType) throws MmsException{
		String regionType;
		switch (outRegionType) {
		case 0:
			regionType = RegionTypeEnum.NATION.getValue();
			break;
		case 1:
			regionType = RegionTypeEnum.PROVINCE.getValue();
			break;
		case 2:
			regionType = RegionTypeEnum.CITY.getValue();
			break;
		case 3:
			regionType = RegionTypeEnum.DISTRICT.getValue();
			break;
		default:
			throw new MmsException(ErrorCodeEnum.OUTREGIONTYPEERROR);
		}
		return regionType; 
	}
}
