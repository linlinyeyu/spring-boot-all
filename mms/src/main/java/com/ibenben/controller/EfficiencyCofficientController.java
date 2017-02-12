package com.ibenben.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ibenben.domain.EfficiencyCoefficient;
import com.ibenben.exception.MmsException;
import com.ibenben.service.EfficiencyCofficientService;
import com.ibenben.util.ParamsProcessUtil;
import com.ibenben.util.ResponseFormatUtil;
import com.ibenben.util.page.PageInfoExt;
import com.ibenben.util.page.PageUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/efficiency/cofficient")
public class EfficiencyCofficientController {
	
	@Autowired
	private EfficiencyCofficientService eCofficientService;
	
	@RequestMapping(value="/list/page", method=RequestMethod.GET)
	@ApiOperation(value="仓库级别人效系数列表(分页)", notes="可选参数：merchant_id")
	public PageInfoExt<EfficiencyCoefficient> getEfficiencyCoefficentPageList(@RequestParam Map<String, Object>params){
		PageUtil.setPageCondition(params);
		PageInfoExt<EfficiencyCoefficient> ext = new PageInfoExt<>(eCofficientService.getEfficiencyCoefficientList(params));
		return ext;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ApiOperation(value="仓库级别人效系数列表", notes="可选参数：merchant_id")
	public Map<String, Object> getEfficiencyCoefficentList(@RequestParam Map<String, Object>params){
		return ResponseFormatUtil.formatResponse(eCofficientService.getEfficiencyCoefficientList(params));
	}
	
	@RequestMapping(value="/set", method=RequestMethod.POST)
	@ApiOperation(value="更新仓库级别系数", notes="可选参数：merchant_id")
	public Map<String, Object> updateEfficiencyCoefficent(@RequestBody Map<String, Object>params) throws MmsException{
		ParamsProcessUtil.checkMapParamsNotNullAndThrows(params, new String[]{"list"});
		List<EfficiencyCoefficient> list = JSON.parseArray(params.get("list").toString(), EfficiencyCoefficient.class);
		Map<String, Object> map = new HashMap<>();
		int count = eCofficientService.updateEfficiencyCoefficent(list);
		map.put("count", count);
 		return ResponseFormatUtil.formatResponse(map);
	}
}
