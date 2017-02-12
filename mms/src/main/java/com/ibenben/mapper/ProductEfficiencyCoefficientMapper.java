package com.ibenben.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibenben.domain.ProductEfficiencyCoefficient;
import com.ibenben.domain.ProductExceptionEfficiencyCoefficient;
import com.ibenben.domain.ProductPackaging;


public interface ProductEfficiencyCoefficientMapper {
    int deleteByPrimaryKey(Long productEfficiencyCoefficientId);

    int insert(ProductEfficiencyCoefficient record);

    int insertSelective(ProductEfficiencyCoefficient record);
    
    int insertBatch(List<ProductEfficiencyCoefficient> list);

    ProductEfficiencyCoefficient selectByPrimaryKey(Long productEfficiencyCoefficientId);
    
    List<Map<String, Object>> getProductUnSettedCofficientList(Map<String, Object> params);
    
    List<Map<String, Object>> getProductCofficientList(Map<String, Object> params);

    int updateByPrimaryKeySelective(ProductEfficiencyCoefficient record);

    int updateByPrimaryKey(ProductEfficiencyCoefficient record);
    
    int updateByMerchantAndFacility(ProductEfficiencyCoefficient pCoefficient);
    
    int disableCoeffiencts(@Param("list") List<ProductEfficiencyCoefficient> coefficients, @Param("disableDate") Date disableDate);
    
    int selectProductEfficiencyByPackaging(ProductPackaging pp);
    
    int insertProductEfficiencyCoefficient(ProductExceptionEfficiencyCoefficient peec);
}