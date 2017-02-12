package com.ibenben.gis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 基于百度地图web服务APIv1.0 实现 IRouteMatrix接口
 *
 * @author Administrator
 *
 */
public class BaiduV1RouteMatrix implements IRouteMatrix {

	private String apiKey = new BaiduApiKeyPool().getRandomKey();

	@Override
	public double getRouteDistance(GeoPoint startPt, GeoPoint endPt) {

		String url = "http://api.map.baidu.com/direction/v1/routematrix";
		String params = "output=json&origins=" + startPt.getLat() + "," + startPt.getLng() + "&destinations=" + endPt.getLat() + "," + endPt.getLng() + "&ak=" + this.apiKey;

		String result = HttpUtility.sendGet(url, params); // 返回结果
		JSONObject json = JSONObject.parseObject(result); // 转成json字符串
		int status = json.getIntValue("status"); // 获取执行状态
		if (status == 0) // 成功
		{
			JSONArray elements = json.getJSONObject("result").getJSONArray("elements");
			if (elements.size() > 0) {
				JSONObject distance = elements.getJSONObject(0).getJSONObject("distance");
				return distance.getDouble("value");
			}
		} else {
			return 0;
		}
		return 0;
	}

}
