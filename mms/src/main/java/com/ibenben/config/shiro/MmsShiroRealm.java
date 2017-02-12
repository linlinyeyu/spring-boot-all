/**
 * 
 */
package com.ibenben.config.shiro;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibenben.domain.User;
import com.ibenben.mapper.RoleMapper;
import com.ibenben.mapper.UserMapper;
import com.ibenben.service.UserService;


/**
 * @author sszheng
 *
 * Created on 2016年7月12日 下午8:03:45
 */
public class MmsShiroRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(MmsShiroRealm.class);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserService userService;
	
	/**
     * 权限认证，为当前登录的Subject授予角色和权限 
     * @see 经测试：本例中该方法的调用时机为需授权资源被访问时 
     * @see 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache 
     * @see 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        String loginName = (String)super.getAvailablePrincipal(principalCollection);

        User userParam = new User();
        userParam.setUserName(loginName);
        userParam.setGroupType("mms");
        
      //不能直接使用AdminUserService(aop无效,不能改变数据源)
    	User user = userMapper.selectByUser(userParam);
    	
        if(user != null){
        	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        	
        	return info;
        }
        
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken authenticationToken) throws AuthenticationException {
    	UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;  
    	User userParam = new User();
    	userParam.setUserName(token.getUsername());
    	userParam.setGroupType("mms");
    	
    	//不能直接使用AdminUserService(aop无效,不能改变数据源)
    	User user = userMapper.selectByUser(userParam);
    	if(user == null){
    		return null;
    	}
    	
    	List<Integer> merchantIds = this.toIntList(userService.getMerchantIdsByUser(user.getUserId(), user.getIsAdmin()));
    	user.setMerchantList(merchantIds);
    	
    	List<Integer> facilityIds = this.toIntList(userService.getFacilityIdsByUser(user.getUserId(), user.getIsAdmin()));
    	user.setFacilityList(facilityIds);
    	user.setGroupType("mms");
    	List<Integer> roleIds = roleMapper.getRoleIdsByUser(user);
    	if(roleIds.size() > 0) {
    		List<Integer> permissionIds = roleMapper.selectByIds(roleIds);
    		List<Integer> per = new ArrayList<Integer>();
    		per.addAll(user.getPermissionList());
    		per.addAll(permissionIds);
    		per = new ArrayList<Integer>(new LinkedHashSet<>(per));
			user.setPermissionList(per);
    	}
    	
//    	AdminUser user = adminUser;
//    	user.setLastLogin(new Date());
//    	adminUsermapper.updateByPrimaryKey(user);
    	
        if(user != null){
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
        return null;
    }
    
    private List<Integer> toIntList(List<String> list) {
    	List<Integer> intList = new ArrayList<Integer>();
    	for(String str : list) {
		  int i = Integer.parseInt(str);
		  intList.add(i);
    	}
    	return intList;
    }
    
    
}