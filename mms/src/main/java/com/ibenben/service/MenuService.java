/**
 * 
 */
package com.ibenben.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibenben.domain.Menu;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.mapper.MenuMapper;
import com.ibenben.util.UserUtil;

/**
 * @author sszheng
 *
 * Created on 2016年8月2日 下午6:08:55
 */
@Service
public class MenuService {
	@Autowired
	private MenuMapper menuMapper;
	
	public List<Menu> getMenuList() throws MmsException{
		Map<String, Object> params = new HashMap<String, Object>();
		if(UserUtil.getAdminUser() == null){
			throw new MmsException(ErrorCodeEnum.USERUNLOGIN);
		}
		if(UserUtil.getAdminUser().getIsAdmin() == 1){
			return menuMapper.getMenu("mms");
		}
		if(UserUtil.getAdminUser().getPermissionList().size() == 0){
			return null;
		}
		params.put("permission_ids", UserUtil.getAdminUser().getPermissionList());
		params.put("group_type", "mms");
		return menuMapper.selectByMapParams(params);
	}
	
	public List<Menu> getApiMenuList(List<Integer> ids,String type){
		Map<String,Object> map = new HashMap<String, Object>();
		if(ids.size() == 0)
		  return null;
		map.put("permission_ids", ids);
		map.put("group_type", type);
		return menuMapper.selectByMapParams(map);
	}
	
	public List<Menu> getAllMenu(Map<String,Object> param){
		return menuMapper.getAllMenu(param);
	}
	
	public List<Menu> getThirdMenuList(List<Integer> ids,String type){
		if(ids.size()==0)
			return null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("permission_ids", ids);
		map.put("group_type", type);
		return menuMapper.getThirdMenuList(map);
	}
	
	public List<Menu> getThirdMenu(String type){
		return menuMapper.getThirdMenu(type);
	}
}