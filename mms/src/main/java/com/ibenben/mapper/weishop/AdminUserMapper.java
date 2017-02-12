package com.ibenben.mapper.weishop;

import com.ibenben.domain.weishop.AdminUser;
import com.ibenben.domain.weishop.AdminUserWithBLOBs;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(AdminUserWithBLOBs record);

    int insertSelective(AdminUserWithBLOBs record);

    AdminUserWithBLOBs selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(AdminUserWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AdminUserWithBLOBs record);

    int updateByPrimaryKey(AdminUser record);
    
    AdminUserWithBLOBs selectByAdminUser(AdminUser adminUser);
}