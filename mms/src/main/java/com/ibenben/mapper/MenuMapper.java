package com.ibenben.mapper;

import java.util.List;
import java.util.Map;

import com.ibenben.domain.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<Menu> selectByMapParams(Map<String,Object> param);
    
    List<Menu> getMenu(String type);
    
    List<Menu> getAllMenu(Map<String,Object> param);
    
    List<Menu> getThirdMenuList(Map<String,Object> param);
    
    List<Menu> getThirdMenu(String type);
}