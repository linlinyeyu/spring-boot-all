package com.ibenben.mapper;

import com.ibenben.domain.User;
import com.ibenben.domain.UserSession;

public interface UserSessionMapper {
    int deleteByPrimaryKey(Integer sessionId);

    int insert(UserSession record);

    int insertSelective(UserSession record);

    UserSession selectByPrimaryKey(Integer sessionId);

    int updateByPrimaryKeySelective(UserSession record);

    int updateByPrimaryKey(UserSession record);
    
    Integer getUserSession(User user);
    
    UserSession getUserSessionByToken(String token);
}