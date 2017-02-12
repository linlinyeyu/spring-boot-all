package com.ibenben.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibenben.domain.EfficiencyCoefficient;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.mapper.EfficiencyCoefficientMapper;
import com.ibenben.util.DateUtil;
import com.ibenben.util.UserUtil;

@Service
public class EfficiencyCofficientService {
	@Autowired
	private EfficiencyCoefficientMapper coefficientMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(EfficiencyCofficientService.class);
	
	public List<EfficiencyCoefficient> getEfficiencyCoefficientList(Map<String, Object> params){
		return coefficientMapper.getMerchantCofficient(params);
	}
	
	public EfficiencyCoefficient getByMerchantAndFacilityId(Integer merchantId, Integer facilityId) throws MmsException{
		Map<String, Object> params = new HashMap<>();
		params.put("merchant_id", merchantId);
		params.put("facility_id", facilityId);
		params.put("is_now", 1);
		List<EfficiencyCoefficient> list = coefficientMapper.selectByMapParams(params);
		if(list.size() > 1){
			logger.error("根据merchant_id和facility_id获取非唯一的仓库级别人效系数");
			throw new MmsException(ErrorCodeEnum.DATAERROR);
		}
		if(list.size() == 0){
			throw new MmsException(ErrorCodeEnum.COEFFICIENTNOTFOUND);
		}
		return list.get(0);
	}
	/**
	 * ************************************************************************************
	 * 修改仓库级人效系数
	 * 1、删除原商户仓库的人效系数
	 * 2、新增该商户仓库的人效系数
	 * 3、更新所有改商户及仓库对应将生效的成品人效系数
	 * @param eCofficients
	 * @return
	 * @throws MmsException 
	 *************************************************************************************
	 */
	@Transactional(rollbackFor={MmsException.class})
	public int updateEfficiencyCoefficent(List<EfficiencyCoefficient> eCofficients) throws MmsException{
		if(eCofficients.size() == 0){
			return 0;
		}
		for (EfficiencyCoefficient eCoefficient : eCofficients) {
			Integer facilityId = eCoefficient.getFacilityId();
			Integer merchantId = eCoefficient.getMerchantId();
			coefficientMapper.deleteByMerchantAndFacility(merchantId, facilityId);
			try {
				eCoefficient.setStartDate(DateUtil.getZeroDate(new Date()));
				eCoefficient.setStartUser(UserUtil.getCurrentUserName());
				eCoefficient.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2199-12-01"));
				eCoefficient.setIsNow((byte)1);
			} catch (Exception e) {
				logger.error("修改仓库级人效系数日期解析错误"+e.getMessage());
				throw new MmsException(ErrorCodeEnum.DATAERROR);
			}
			coefficientMapper.insertSelective(eCoefficient);
		}
		return eCofficients.size();
	}
}
