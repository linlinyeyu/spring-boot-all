package com.ibenben.mapper;

import java.util.List;

import com.ibenben.domain.Merchant;
import com.ibenben.domain.UserMerchantKey;

public interface UserMerchantMapper {
    int deleteByPrimaryKey(UserMerchantKey key);

    int insert(UserMerchantKey record);

    int insertSelective(UserMerchantKey record);
    
    List<Merchant> getUserMerchantList(Integer userId);
}