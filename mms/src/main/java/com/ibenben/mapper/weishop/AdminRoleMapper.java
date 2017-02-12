package com.ibenben.mapper.weishop;

import java.util.List;
import java.util.Map;

import com.ibenben.domain.weishop.AdminRole;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKeyWithBLOBs(AdminRole record);

    int updateByPrimaryKey(AdminRole record);
    
    List<AdminRole> selectByMapParams(Map<String, Object> params);
    
    List<AdminRole> selectByIds(List<String> ids);
}