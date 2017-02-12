package com.ibenben.gis;

/**
 * 地理编码\逆地理编码
 * IGeoCoder接口实现的管理
 * @author Administrator
 *
 */
public class GeoCoder {

	private static GeoCoder _instance=null;
	private IGeoCoder _geoCoder=null;
	private IRouteMatrix _routeMatrix=null;
	
	/**
	 * 私有构造函数
	 */
	private GeoCoder()
	{
		_geoCoder=new BaiduV2GeoCoder();
		_routeMatrix=new BaiduV1RouteMatrix();
	}
	
	/**
	 * 获取地理编码对象对象
	 * @return
	 */
	public static GeoCoder getInstance()
	{
		if(_instance==null)
		{
			_instance=new GeoCoder();
		}
		return _instance;
	}
	
	/**
	 * 获取地理编码接口
	 * @return
	 */
	public IGeoCoder getGeoCoder()
	{
		return _geoCoder;
	}
	
	/**
	 * 路线规划，路线查询接口
	 * @return
	 */
	public IRouteMatrix getRouteMatrix()
	{
		return _routeMatrix;
	}
	
	
}
