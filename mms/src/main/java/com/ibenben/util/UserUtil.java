package com.ibenben.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;

import com.ibenben.domain.User;

public class UserUtil {
	
	public static String getCurrentUserName() {
		User user = null;
		try{
			user = (User) SecurityUtils.getSubject().getPrincipal();
		}catch(UnavailableSecurityManagerException e){
			return "system";
		}
		if(user == null) {
			return "system";
		} 
		return user.getUserName();
	}
	
	public static User getAdminUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}
	
	public static boolean checkAction(Integer permissionId) {
		User user = UserUtil.getAdminUser();
		if(user == null) {
			return false;
		}
		return UserUtil.getAdminUser().getPermissionList().contains(permissionId);
	}
}