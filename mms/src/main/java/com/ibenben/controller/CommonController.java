package com.ibenben.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ibenben.domain.Facility;
import com.ibenben.domain.Menu;
import com.ibenben.domain.Station;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.gis.GeoCoder;
import com.ibenben.gis.GeoPoint;
import com.ibenben.service.FacilityService;
import com.ibenben.service.FileService;
import com.ibenben.service.MenuService;
import com.ibenben.service.ProductService;
import com.ibenben.service.StationService;
import com.ibenben.util.ParamsProcessUtil;
import com.ibenben.util.ResponseFormatUtil;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private FacilityService fService;
	
	@Autowired
	private StationService sService;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "上传文件", notes = "enctype = multipart/form-data", produces="application/json")
	@RequestMapping(value="/file/upload", method=RequestMethod.POST)
	public Map<String, Object> uploadFile(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> pathList = fileService.doUpload(request);
		if(pathList == null || pathList.size() == 0){
			map.put("error", ErrorCodeEnum.UPLOADFAILED);
			return ResponseFormatUtil.formatResponse(map);
		}else if(pathList.size() > 1){
			map.put("error", ErrorCodeEnum.UPLOADMORETHANONEFILE);
			return ResponseFormatUtil.formatResponse(map);
		}
		Map<String, String> fileNameAndPath = pathList.get(0);
		return  ResponseFormatUtil.formatResponse((Map)fileNameAndPath);
	}
	
	@ApiOperation(value = "上传文件", notes = "", produces="application/json")
	@RequestMapping(value="/file/download", method=RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "attach_path", value = "路径", paramType="query",  dataType = "string"),
		@ApiImplicitParam(name = "attach_name", value = "名称", paramType="query", dataType = "string"),
	})
	public void downLoadFile(@RequestParam(name="attach_path") String attach_path, @RequestParam(name="attach_name") String attach_name, HttpServletRequest request, HttpServletResponse response) throws IOException{
		fileService.downLoad(attach_path, attach_name, request, response);
	}
	
	@ApiOperation(value = "获取菜单列表", notes = "enctype = multipart/form-data", produces="application/json")
	@RequestMapping(value="/getMenuList", method=RequestMethod.GET)
	public Map<String, Object> getMenuList() throws MmsException {
		List<Menu> list = menuService.getMenuList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		return ResponseFormatUtil.formatResponse(map) ;
	}
	
	@ApiOperation(value = "删除文件", notes = "", produces="application/json")
	@RequestMapping(value="/file/delete", method=RequestMethod.DELETE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "attach_path", value = "路径", paramType="body",  dataType = "string"),
		@ApiImplicitParam(name = "attach_name", value = "名称", paramType="body", dataType = "string"),
	})
	public Map<String, Object> deleteFile(HttpServletRequest request, @RequestBody(required=false) Map<String, Object> params) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!ParamsProcessUtil.checkMapParamsNotNull(params, new String[]{"attach_path","attach_name"})){
			map.put("error", ErrorCodeEnum.PARAMSERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		String attach_path = (String)params.get("attach_path");
		String attach_name = (String)params.get("attach_name");
		boolean result = fileService.deleteFile(request, attach_path, attach_name);
		if(!result){
			map.put("error", ErrorCodeEnum.DELETEFAIL);
			return ResponseFormatUtil.formatResponse(map);
		}
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value = "清除redis缓存", notes = "", produces="application/json")
	@RequestMapping(value="/redis/clear", method=RequestMethod.GET)
	public Map<String, Object> clearRedis() throws MmsException{
		Map<String, Object> map = new HashMap<String, Object>();
		productService.clearCache();
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value = "清除redis缓存", notes = "", produces="application/json")
	@RequestMapping(value="/addressPoint", method=RequestMethod.GET)
	public Map<String, Object> getAddressPoint(@RequestParam String address) throws MmsException{
		GeoPoint position = GeoCoder.getInstance().getGeoCoder().GetLocationDetails(address);
		if(position == null){
			throw new MmsException(ErrorCodeEnum.GETPOINTFAILED);
		}
		return ResponseFormatUtil.formatResponseDomain(position);
	}
	
	@ApiOperation(value = "更新仓库经纬度", notes = "", produces="application/json")
	@RequestMapping(value="/updateAddressPoint", method=RequestMethod.GET)
	@Transactional
	public Map<String, Object> updateAddressPoint() throws MmsException{
		List<Facility> facilities= fService.getFacilityList(new HashMap<>());
		for (Facility facility : facilities) {
			GeoPoint position = GeoCoder.getInstance().getGeoCoder().GetLocationDetails(facility.getProvinceName() + facility.getCityName() + 
					facility.getDistrictName() + facility.getRealAddress());
			List<Integer> stationIds = sService.getStationId(facility.getFacilityId());
			if(position != null){
				Map<String, Object> condition = new HashMap<>();
				condition.put("facility_id", facility.getFacilityId());
				condition.put("latitude", position.getLat());
				condition.put("longitude", position.getLng());
				fService.editFacility(condition);
				if(stationIds.size() > 0){
					Station staiton = new Station();
					staiton.setStationId(stationIds.get(0));
					staiton.setLatitude(BigDecimal.valueOf(position.getLat()));
					staiton.setLongitude(BigDecimal.valueOf(position.getLng()));
					sService.editStation(staiton);
				}
				
			}else{
				logger.info("仓库获取经纬度失败 仓库:" + JSON.toJSONString(facility));
			}
		}
		
		return ResponseFormatUtil.formatResponse();
	}

}
