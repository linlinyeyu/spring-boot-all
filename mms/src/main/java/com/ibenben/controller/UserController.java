/**
 * 
 */
package com.ibenben.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.ibenben.domain.Facility;
import com.ibenben.domain.GroupType;
import com.ibenben.domain.Merchant;
import com.ibenben.domain.User;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.service.GroupTypeService;
import com.ibenben.service.PermissionService;
import com.ibenben.service.RoleService;
import com.ibenben.service.UserService;
import com.ibenben.service.UserSessionService;
import com.ibenben.service.UserTokenService;
import com.ibenben.util.GetIpAddrUtil;
import com.ibenben.util.Md5Util;
import com.ibenben.util.ParamsProcessUtil;
import com.ibenben.util.ResponseFormatUtil;
import com.ibenben.util.UserUtil;
import com.ibenben.util.page.PageInfoExt;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author sszheng
 *
 * Created on 2016年7月26日 下午1:28:11
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final long TIMEOUT = 1000 * 60 * 60 * 6;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserSessionService userSessionService;
	@Autowired
	private PermissionService perService;
	@Autowired
	private GroupTypeService groupTypeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private UserTokenService userTokenService;
	
    @RequestMapping(value="/unlogin", method=RequestMethod.GET)
    public Map<String, Object> unlogin(){
		logger.error("访问需认证的资源，用户未登录");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error",ErrorCodeEnum.USERUNLOGIN);
		return ResponseFormatUtil.formatResponse(map);
    }
	
    @RequestMapping(value="/unauthorized", method=RequestMethod.GET)
    public Map<String, Object> unauthorized(){
		logger.error("访问需授权的资源，用户未授权");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error",ErrorCodeEnum.UNAUTHORIZEDURL);
		return ResponseFormatUtil.formatResponse(map);
    }
	
    @ApiOperation(value="用户登陆",notes="",produces="application/json")
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public Map<String, Object> login(@Valid @RequestBody User user,BindingResult bindingResult,RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
        if(bindingResult.hasErrors()){
        	Map<String, Object> map = new HashMap<String, Object>();
    		map.put("error",ErrorCodeEnum.PARAMSERROR);
    		return ResponseFormatUtil.formatResponse(map);
        }
        String username = user.getUserName();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();
        try {  
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.logout();
            currentUser.login(token);
            currentUser.getSession().setTimeout(TIMEOUT);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");  
        }catch(UnknownAccountException uae){  
            logger.error("对用户[" + username + "]进行登录验证..验证未通过,未知账户");  
        }catch(IncorrectCredentialsException ice){  
            logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");  
        }catch(LockedAccountException lae){  
            logger.error("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");  
        }catch(ExcessiveAttemptsException eae){  
            logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");  
        }catch(AuthenticationException ae){  
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
            logger.error("对用户[" + username + "]进行登录验证..验证未通过");  
        }
        //验证是否登录成功  
        if(currentUser.isAuthenticated()){  
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            String ip = GetIpAddrUtil.getIpAddr(request);
            String token1 = userSessionService.insertUserSession(ip);
            user.setUserId(UserUtil.getAdminUser().getUserId());
            user.setLastLoginTime(new Date());
            userService.updateUser(user);
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token", token1);
    		return ResponseFormatUtil.formatResponse(map);
        }else{  
            token.clear();
            Map<String, Object> map = new HashMap<String, Object>();
            user.setGroupType("mms");
            if(userService.selectByName(user) == null){
            	map.put("error", ErrorCodeEnum.USERNOTEXSIS);
            	return ResponseFormatUtil.formatResponse(map);
            }
    		map.put("error",ErrorCodeEnum.PASSWORDERROR);
    		return ResponseFormatUtil.formatResponse(map);
        }  
    }
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)  
    public Map<String, Object> logout(RedirectAttributes redirectAttributes ){ 
        SecurityUtils.getSubject().logout();
		return ResponseFormatUtil.formatResponse();
    }
	
	
	@RequestMapping(value="/checkUserAction",method=RequestMethod.GET)
	@ApiOperation(value="获取当前用户权限", notes="permission_codes")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "permission_codes", value = "权限(多个权限用逗号隔开)", paramType="query",  dataType = "string"),
	})
	public Map<String, Object> getProductQcPageList(@RequestParam(value="action_codes") String actionCode) {
        List<String> permissionCodes = Splitter.on(",").trimResults().splitToList(actionCode);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if(UserUtil.getAdminUser() != null && UserUtil.getAdminUser().getIsAdmin() == 1){
        	for(String permissionCode:permissionCodes){
        		map.put(permissionCode, true);
        	}
        	return ResponseFormatUtil.formatResponse(map);
        }
        for(String permissionCode: permissionCodes) {
        	Integer permissionId = userService.getPermissionId(permissionCode,"mms");
        	map.put(permissionCode, UserUtil.checkAction(permissionId));
        }
        return ResponseFormatUtil.formatResponse(map);
	}
	
	@RequestMapping(value="/getUserName", method=RequestMethod.GET)  
	public String findUserName() {
		return UserUtil.getCurrentUserName();
	}
	
	@ApiOperation(value="用户列表",notes="参数:user_name,status,user_id")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Map<String,Object> getUserList(@RequestParam Map<String,Object> param){
		List<Map<String,Object>> list = userService.getUserList(param);
		return ResponseFormatUtil.formatResponse(list);
	}
	
	@ApiOperation(value="用户列表",notes="参数:user_name,status,user_id,page,pageSize")
	@RequestMapping(value="/list/page",method=RequestMethod.GET)
	public PageInfoExt<Map<String,Object>> getUserPageList(@RequestParam Map<String,Object> param){
		List<Map<String,Object>> list = userService.getUserList(param);
		PageInfoExt<Map<String,Object>> pExt = new PageInfoExt<>(list);
		return pExt;
	}
	
	@ApiOperation(value="单个用户列表",notes="参数:user_id")
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public Map<String,Object> getUserInfo(@RequestParam(value="user_id") Integer userId){
		Map<String,Object> map = new HashMap<String, Object>();
		User user = userService.getUserInfo(userId);
		map.put("user",user);
		map.put("facility_list", userService.getFacilityIds(userId));
		map.put("merchant_list", userService.getMerchantIds(userId));
		if(user.getIsAdmin() != null && user.getIsAdmin() == 1){
			map.put("permission_list", perService.getAllPermission(null));
		}else{
			map.put("permission_list", userService.getPermissionIds(userId,null));
		}
		map.put("role_list", userService.getRoleList(userId, null));
		
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@RequestMapping(value="/current/info",method=RequestMethod.GET)
	public Map<String,Object> getCurrentUserInfo(){
		Map<String,Object> map = new HashMap<String, Object>();
		
		Integer userId = UserUtil.getAdminUser().getUserId();
		System.out.println(userId);
		User user = userService.getUserInfo(userId );
		map.put("user",user);
		map.put("facility_list", userService.getFacilityIds(userId));
		map.put("merchant_list", userService.getMerchantIds(userId));
		if(user.getIsAdmin() != null && user.getIsAdmin() == 1){
			map.put("permission_list", perService.getAllPermission(null));
		}else{
			map.put("permission_list", userService.getPermissionIds(userId,null));
		}
		map.put("role_list", userService.getRoleList(userId, null));
		
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value="编辑用户",notes="参数：user_id..",produces="application/json")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public Map<String,Object> editUser(@RequestBody Map<String,Object> param) throws MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		userService.editUser(param);
		userService.editUserFacility(param);
		userService.editUserMerchant(param);
		userService.editUserRole(param);
		userService.editUserPermission(param);
		
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value="重置密码",notes="user_id",produces="application/json")
	@RequestMapping(value="/reset/password",method=RequestMethod.POST)
	public Map<String,Object> resetUserPassword(@RequestBody Map<String,Object> param) throws NoSuchAlgorithmException, MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		String pass = Md5Util.md5("888888", 32);
		param.put("password", pass);
		int t = userService.resetUserPassword(param);
		
		if(t >0 ){
			map.put("info", "重置密码成功，密码为888888");
			return ResponseFormatUtil.formatResponse(map);
		}
		map.put("errpr", ErrorCodeEnum.UPDATEFAILED);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	/**
	 * 删除用户
	 * @throws MmsException 
	 */
	@ApiOperation(value="删除用户",notes="参数：user_id,status",produces="application/json")
	@RequestMapping(value="/delUser",method=RequestMethod.POST)
	public Map<String,Object> delUser(@RequestBody Map<String,Object> param) throws MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		param.put("last_close_time", new Date());
		
		int t = userService.delUser(param);
		if(t > 0){
			map.put("count", t);
			return ResponseFormatUtil.formatResponse(map);
		}
		map.put("error", ErrorCodeEnum.DELETEFAIL);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	/**
	 * 恢复用户
	 */
	@ApiOperation(value="恢复用户",notes="参数:user_id,status",produces="application/json")
	@RequestMapping(value="/recover",method=RequestMethod.POST)
	public Map<String,Object> recoverUser(@RequestBody Map<String,Object> param){
		Map<String,Object> map = new HashMap<String, Object>();
		param.put("last_open_time", new Date());
		
		int t = userService.recoverUser(param);
		
		if(t >0 ){
			map.put("count", t);
			return ResponseFormatUtil.formatResponse(map);
		}
		map.put("error", ErrorCodeEnum.UPDATEFAILED);
		return ResponseFormatUtil.formatResponse(map);
	}
	/**
	 * 清除邮箱
	 * @throws MmsException 
	 */
	@ApiOperation(value="清除邮箱",notes="参数:user_id")
	@RequestMapping(value="/clear/email",method=RequestMethod.POST)
	public Map<String,Object> clearUserEmail(@RequestBody Map<String,Object> param,HttpServletRequest request) throws MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		
		int t = userService.clearUserEmail(param);
		logger.debug("Info::ResetEmail, user_id="+UserUtil.getCurrentUserName()+"clear_user_id="+param.get("user_id")+"ip="+GetIpAddrUtil.getIpAddr(request));
		if(t>0){
			map.put("count", t);
			return ResponseFormatUtil.formatResponse(map);
		}
		map.put("error", ErrorCodeEnum.UPDATEFAILED);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value="检测邮箱是否绑定",notes="参数:token")
	@RequestMapping(value="/checkEmail",method=RequestMethod.GET)
	public Map<String,Object> checkEmailIsBind(@RequestParam String token){
		Map<String,Object> map = new HashMap<String, Object>();
		User user = userService.getUserByToken(token);
		if(user.getEmailStatus() == null){
			map.put("error", ErrorCodeEnum.EMAILNOTFOUNT);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(user.getEmailStatus().equals("CHECKING")){
			map.put("error", ErrorCodeEnum.EMAILNOTVERIFY);
			return ResponseFormatUtil.formatResponse(map);
		} else if(user.getEmailStatus().equals("FROZEN")){
			map.put("error", ErrorCodeEnum.EMAILNOTBIND);
			return ResponseFormatUtil.formatResponse(map);
		}
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value="绑定邮箱",notes="参数:email",produces="application/json")
	@RequestMapping(value="/bindEmail",method=RequestMethod.POST)
	public Map<String,Object> bindEmail(@RequestBody Map<String,Object> param) throws MmsException, NoSuchAlgorithmException, ParseException{
		Map<String,Object> map = new HashMap<String, Object>();
		if(! checkEmail(param.get("email").toString())){
			map.put("error", ErrorCodeEnum.EMAILFORMERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		
		User user = UserUtil.getAdminUser();
		user.setEmail(param.get("email").toString().trim());
		user.setEmailStatus("CHECKING");
		userService.updateUser(user);
		userTokenService.updateUserTokenOutValidByType(user.getUserId(),"email_bind");

		if(! user.getEmailStatus().equals("CHECKING")){
			map.put("error", ErrorCodeEnum.EMAILNOTATCHECKINT);
			return ResponseFormatUtil.formatResponse(map);
		}
		
		Date lastTokenTime = userTokenService.getUserLastValidTokenTimeByType(user.getUserId(),"email_bind");
		String url = userService.getUrl("mmsVerifyEmail");
		map = userService.send(user,"email_bind",lastTokenTime,url);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	private boolean checkEmail(String email){
		String reg = "([a-z0-9A-Z]+[-|_|\\.]?)+@(yiran\\.com|ibenben\\.com)";
		return email.matches(reg);
	}
	
	@ApiOperation(value="验证",notes="参数:token_type,user_token",produces="application/json")
	@RequestMapping(value="/verifyEmail",method=RequestMethod.POST)
	public Map<String,Object> verifyEmail(@RequestBody Map<String,Object> param,HttpServletRequest request) throws MmsException, NoSuchAlgorithmException{
		ParamsProcessUtil.checkMapParamsNotNullAndThrows(param, new String[]{"user_token","token_type"});
		String userToken = param.get("user_token").toString();
		Integer userId = Integer.valueOf(userToken.substring(userToken.length()-4, userToken.length()));
		param.put("user_id", userId);
		Map<String,Object> map = userService.verifyToken(param,request);
		if(map.get("result").equals("fail"))
			return map;
		if(param.get("token_type").equals("email_bind"))
			userService.verifyUserEmail(param);
		if(param.get("token_type").equals("password_reset")){
			String password = Md5Util.md5("888888", 32);
			param.put("password", password);
			userService.resetUserPassword(param);
		}
		return map;
	}
	
	@ApiOperation(value="发送重置密码邮件",notes="参数:email,user_name",produces="application/json")
	@RequestMapping(value="/sendResetPass",method=RequestMethod.POST)
	public Map<String,Object> sendRestPasswordEmail(@RequestBody Map<String,Object> param) throws MmsException, NoSuchAlgorithmException, ParseException{
		Map<String,Object> map = new HashMap<String, Object>();
		ParamsProcessUtil.checkMapParamsNotNullAndThrows(param, new String[]{"email","user_name"});
		if(! param.get("user_name").toString().matches("([a-zA-Z0-9_\\.\\-])+")){
			map.put("error", ErrorCodeEnum.PARAMSERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(! checkEmail(param.get("email").toString())){
			map.put("error", ErrorCodeEnum.EMAILFORMERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		User user = userService.getUserByEmail(param);
		if(user == null){
			map.put("error", ErrorCodeEnum.USERNOTEXSIS);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(user.getStatus().equals("DELETE")){
			map.put("error", ErrorCodeEnum.USERDELETE);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(user.getEmailStatus().equals("CHECKING")){
			map.put("error", ErrorCodeEnum.EMAILCHECKING);
			return ResponseFormatUtil.formatResponse(map);
		}
		Date tokenTime = userTokenService.getUserLastValidTokenTimeByType(user.getUserId(), "password_reset");
		String url = userService.getUrl("mmsVerifyEmail");
		map = userService.send(user,"password_reset",tokenTime,url);
		return map;
	}
	
	/**
	 * 修改密码
	 * @throws NoSuchAlgorithmException 
	 * @throws MmsException 
	 */
	@ApiOperation(value="修改密码",notes="old_password,new_password",produces="application/json")
	@RequestMapping(value="/modifyPass",method=RequestMethod.POST)
	public Map<String,Object> modifyPassword(@RequestBody Map<String,Object> param) throws NoSuchAlgorithmException, MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		if(param.get("old_password").equals(param.get("new_password"))){
			map.put("error", ErrorCodeEnum.PASSWORDSAME);
			return ResponseFormatUtil.formatResponse(map);
		}
		User user = UserUtil.getAdminUser();
		if(! user.getPassword().equals(param.get("old_password").toString())){
			map.put("error", ErrorCodeEnum.PASSWORDERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		user.setPassword(param.get("new_password").toString());
		user.setLastUpdatedPasswordTime(new Date());
		userService.updateUser(user);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@RequestMapping(value="current/merchant/list", method=RequestMethod.GET)
	public Map<String,Object> getCurrentMerchantList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Merchant> merchantList = userService.getMerchantByUser(UserUtil.getAdminUser().getUserId());
		List<Facility> facilityList = userService.getFacilityByUserMerchant(UserUtil.getAdminUser().getUserId());
		map.put("merchant_list", merchantList);
		map.put("facility_list", facilityList);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@RequestMapping(value="getGroupList",method=RequestMethod.GET)
	public Map<String,Object> getGroupList(@RequestParam Map<String,Object> params) {
		List<GroupType> list = groupTypeService.getGroupList(params);
		return ResponseFormatUtil.formatResponse(list);
	}
	
	@RequestMapping(value="getUserGroupList",method=RequestMethod.GET)
	public Map<String,Object> getUserGroupList(@RequestParam(required=false, defaultValue="-1") int user_id) {
		List<Map<String,Object>> userList = groupTypeService.getUserGroupList(user_id);
		return ResponseFormatUtil.formatResponse(userList);
	}
	
	@RequestMapping(value="getUserSelectFacilityList",method=RequestMethod.GET)
	public Map<String, Object> getUserSelectFacilityList(@RequestParam(required=false, defaultValue="-1") int user_id) {
		List<String> facilityIdList = new ArrayList<String>();
		if(user_id != -1) {
			facilityIdList = userService.getFacilityIdsByUser(user_id, 0);
		}
		return ResponseFormatUtil.formatResponse(facilityIdList);
	}
	
	@RequestMapping(value="getUserSelectMerchantList",method=RequestMethod.GET)
	public Map<String, Object> getUserSelectMerchantList(@RequestParam(required=false, defaultValue="-1") int user_id) {
		List<String> merchantIdList = new ArrayList<String>();
		if(user_id != -1) {
			merchantIdList = userService.getMerchantIdsByUser(user_id, 0);
		}
		return ResponseFormatUtil.formatResponse(merchantIdList);
	}
	
	@RequestMapping(value="getUserSelectRoleList",method=RequestMethod.GET)
	public Map<String, Object> getUserSelectRoleList(@RequestParam(required=false, defaultValue="-1") int user_id) {
		List<String> roleIdList = new ArrayList<String>();
		if(user_id != -1) {
			roleIdList = roleService.getRoleIdByUser(user_id);
		}
		return ResponseFormatUtil.formatResponse(roleIdList);
	}
	
	@RequestMapping(value="getUserSelectShieldPermissionList",method=RequestMethod.GET)
	public Map<String, Object> getUserSelectShieldPermissionList(@RequestParam(required=false, defaultValue="-1") int user_id) {
		List<String> permissionIdList = new ArrayList<String>();
		if(user_id != -1) {
			permissionIdList = permissionService.getShieldPermissionIdByUser(user_id);
		}
		return ResponseFormatUtil.formatResponse(permissionIdList);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="setUser",method=RequestMethod.POST)
	public Map<String, Object> setUser(@RequestBody Map<String,Object> params) throws NoSuchAlgorithmException {
		Map<String,Object> map = new HashMap<String, Object>();
		User user = JSONObject.parseObject(params.get("user").toString(), User.class);
		List<String> groupList = (List<String>) params.get("group_list");
		List<Integer> facilityList = (List<Integer>) params.get("facility_list");
		List<Integer> merchantList = (List<Integer>) params.get("merchant_list");
		List<Integer> roleList = (List<Integer>) params.get("role_list");
		List<Integer> permissionList = (List<Integer>) params.get("permission_list");
		
		map = userService.addOrUpdateUser(user,groupList,facilityList,merchantList,roleList,permissionList);
		
		return map;
	}
}
