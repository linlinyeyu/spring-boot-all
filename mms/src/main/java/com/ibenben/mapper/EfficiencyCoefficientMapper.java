package com.ibenben.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibenben.domain.EfficiencyCoefficient;

public interface EfficiencyCoefficientMapper {
    int deleteByPrimaryKey(Integer efficiencyCoefficientId);
    
    int deleteByMerchantAndFacility(@Param("merchant_id")Integer merchantId, @Param("facility_id")Integer facilityId);

    int insert(EfficiencyCoefficient record);

    int insertSelective(EfficiencyCoefficient record);

    EfficiencyCoefficient selectByPrimaryKey(Integer efficiencyCoefficientId);
    
    List<EfficiencyCoefficient> selectByMapParams(Map<String, Object> params);
    
    List<EfficiencyCoefficient> getMerchantCofficient(Map<String, Object> params);

    int updateByPrimaryKeySelective(EfficiencyCoefficient record);

    int updateByPrimaryKey(EfficiencyCoefficient record);
}