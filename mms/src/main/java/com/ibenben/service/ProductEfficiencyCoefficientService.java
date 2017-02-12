package com.ibenben.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibenben.domain.EfficiencyCoefficient;
import com.ibenben.domain.Product;
import com.ibenben.domain.ProductEfficiencyCoefficient;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.mapper.ProductEfficiencyCoefficientMapper;
import com.ibenben.util.DateUtil;
import com.ibenben.util.ListUtil;
import com.ibenben.util.UserUtil;

@Service
public class ProductEfficiencyCoefficientService {

	private static final Logger logger = LoggerFactory.getLogger(ProductEfficiencyCoefficientService.class);
	@Autowired
	private ProductEfficiencyCoefficientMapper productEfficiencyCoefficientMapper;
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private ProductApplyService pApplyService;
	
	
	public List<Map<String, Object>> getProductUnSettedCofficientList(Map<String, Object> params){
		return productEfficiencyCoefficientMapper.getProductUnSettedCofficientList(params);
	}
	
	public List<Map<String, Object>> getProductCofficientList(Map<String, Object> params){
		return productEfficiencyCoefficientMapper.getProductCofficientList(params);
	}
	
	/**
	 * ************************************************************************************
	 * 首次设置人效系数
	 * @param list
	 * @return
	 * @throws MmsException
	 *************************************************************************************
	 */
	@Transactional(rollbackFor={MmsException.class})
	public int addProductCofficients(List<ProductEfficiencyCoefficient> list) throws MmsException{
		if(list.size() == 0){
			return 0;
		}
		List<ProductEfficiencyCoefficient> cloneList = new ArrayList<>();
		for (ProductEfficiencyCoefficient productEfficiencyCoefficient : list) {
			Integer productId = productEfficiencyCoefficient.getProductId();
			Product p = pService.getProductById(productId);
			if(p == null){
				throw new MmsException(ErrorCodeEnum.PRODUCTNOTFOUND);
			}
			Date startDate = null;
			Date endDate = null;
			try {
				if(p.getProductApplyId() != null){
					startDate = DateUtil.getZeroDate(pApplyService.getProductApplyById(p.getProductApplyId()).getCheckTime());
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				endDate = sdf.parse("2199-12-01");
			} catch (ParseException e) {
				logger.error("设置人效系数解析日期异常：" + e.getMessage());
				throw new MmsException(ErrorCodeEnum.DATAERROR);
			}
			productEfficiencyCoefficient.setStartDate(startDate);
			productEfficiencyCoefficient.setStartUser(UserUtil.getCurrentUserName());
			productEfficiencyCoefficient.setEndDate(endDate);
			productEfficiencyCoefficient.setSettingTime(new Date());
			productEfficiencyCoefficient.setIsNow((byte)1);
			cloneList.add(productEfficiencyCoefficient);
		}
		return productEfficiencyCoefficientMapper.insertBatch(cloneList);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int updateProductCoefficients(List<ProductEfficiencyCoefficient> list, Date disableDate){
		if(list.size() == 0){
			return 0;
		}
		List<List<ProductEfficiencyCoefficient>> chunkedLists = ListUtil.chunkList(list, 1000);
		for (List<ProductEfficiencyCoefficient> chunkedList : chunkedLists) {
			productEfficiencyCoefficientMapper.disableCoeffiencts(chunkedList, disableDate);
			productEfficiencyCoefficientMapper.insertBatch(chunkedList);
		}
		return list.size();
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int synchronizeProductCoeffToFacilityCoeff(EfficiencyCoefficient eCoefficient){
		if(null == eCoefficient){
			return 0;
		}
		ProductEfficiencyCoefficient peCoefficient = new ProductEfficiencyCoefficient();
		peCoefficient.setFacilityId(eCoefficient.getFacilityId());
		peCoefficient.setMerchantId(eCoefficient.getMerchantId());
		peCoefficient.setInventoryManagementCoff(eCoefficient.getInventoryManagementCoff());
		peCoefficient.setDocumentManagementCoff(eCoefficient.getDocumentManagementCoff());
		peCoefficient.setDistributionShippingCoff(eCoefficient.getDistributionShippingCoff());
		return productEfficiencyCoefficientMapper.updateByMerchantAndFacility(peCoefficient);
	}
	
}
