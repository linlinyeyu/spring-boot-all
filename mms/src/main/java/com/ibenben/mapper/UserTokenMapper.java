package com.ibenben.mapper;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibenben.domain.UserToken;

public interface UserTokenMapper {
    int deleteByPrimaryKey(Long userTokenId);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(Long userTokenId);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);
    
    int updateUserTokenOutValidByType(@Param(value="user_id")Integer id,@Param(value="token_type")String type);
    
    Date getUserLastValidTokenTimeByType(@Param(value="user_id")Integer id,@Param(value="token_type")String type);
    
    int addUserToken(@Param(value="user_id")Integer id,@Param(value="token")String token,@Param(value="expired_time")Date time,@Param(value="token_type")String type);
    
    int updateOutTimeToken();
    
    UserToken getToken(Map<String,Object> param);
    
    int updateOutValidToken(Map<String,Object> param);
}