package com.ibenben.mapper.weishop;

import com.ibenben.domain.weishop.AdminSessions;

public interface AdminSessionsMapper {
    int deleteByPrimaryKey(Integer sessionId);

    int insert(AdminSessions record);

    int insertSelective(AdminSessions record);

    AdminSessions selectByPrimaryKey(Integer sessionId);

    int updateByPrimaryKeySelective(AdminSessions record);

    int updateByPrimaryKey(AdminSessions record);
}