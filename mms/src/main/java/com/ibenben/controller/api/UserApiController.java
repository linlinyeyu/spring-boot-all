package com.ibenben.controller.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONException;
import com.ibenben.controller.UserController;
import com.ibenben.domain.Facility;
import com.ibenben.domain.Menu;
import com.ibenben.domain.Merchant;
import com.ibenben.domain.User;
import com.ibenben.domain.UserSession;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.service.AllowedIpService;
import com.ibenben.service.FacilityService;
import com.ibenben.service.MenuService;
import com.ibenben.service.MerchantService;
import com.ibenben.service.PartyService;
import com.ibenben.service.PermissionService;
import com.ibenben.service.RoleService;
import com.ibenben.service.UserFacilityService;
import com.ibenben.service.UserMerchantService;
import com.ibenben.service.UserService;
import com.ibenben.service.UserSessionService;
import com.ibenben.service.UserTokenService;
import com.ibenben.util.GetIpAddrUtil;
import com.ibenben.util.Md5Util;
import com.ibenben.util.ParamsProcessUtil;
import com.ibenben.util.ResponseFormatUtil;
import com.ibenben.util.UserUtil;

@RestController
@RequestMapping(value="/api/user")
public class UserApiController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserSessionService userSessionService;
	@Autowired
	private PermissionService perService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserSessionService usService;
	@Autowired
	private UserTokenService userTokenService;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private UserFacilityService userFacilityService;
	@Autowired
	private UserMerchantService userMerchantService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private AllowedIpService ipService;
	@Autowired
	private PartyService partyService;
	
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
    public Map<String, Object> login(@RequestBody User user,HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
        List<String> groupTypes = userService.getUserGroupType(user);
        if(! groupTypes.contains(user.getGroupType())){
        	map.put("error", ErrorCodeEnum.NOTINGROUP);
        	return ResponseFormatUtil.formatResponse(map);
        }
        User userParam = userService.selectByApiUser(user);
        if(userParam == null){
        	map.put("error", ErrorCodeEnum.PASSWORDERROR);
        	return ResponseFormatUtil.formatResponse(map);
        }
        if(user.getIp() != null) {
        	if(userParam.getIpType().equals("COMPANY")){
        		List<String> list = ipService.getIpList();
        		if(! list.contains(user.getIp())){
        			map.put("error", ErrorCodeEnum.IPNOTLOGIN);
        			return ResponseFormatUtil.formatResponse(map);
        		}
        	}
        }
        String ip = GetIpAddrUtil.getIpAddr(request);
        userParam.setLastLoginTime(new Date());
        userService.updateUser(userParam);
        String token1 = userSessionService.insertApiUserSession(userParam,ip,user.getGroupType());
        map.put("token", token1);
		return ResponseFormatUtil.formatResponse(map); 
    }
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)  
    public Map<String, Object> logout(RedirectAttributes redirectAttributes ){ 
        SecurityUtils.getSubject().logout();
		return ResponseFormatUtil.formatResponse();
    }
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkUserAction",method=RequestMethod.POST)
	@ApiOperation(value="获取当前用户权限", notes="permission_codes,group_type,token")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "permission_codes", value = "权限(多个权限用逗号隔开)", paramType="query",  dataType = "string"),
	})
	public Map<String, Object> checkUserAction(@RequestBody Map<String,Object> param) throws MmsException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ParamsProcessUtil.checkMapParamsNotNullAndThrows(param, new String[]{"group_type","token","permission_codes"});
        User user = userService.getUserByToken(param.get("token").toString());
        if(user == null){
        	map.put("error", ErrorCodeEnum.TOKENERROR);
        	return ResponseFormatUtil.formatResponse(map);
        }
        UserSession us = usService.getUserSessionByToken(param.get("token").toString());
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
        map.put("user_name", user.getUserName());
        try {
        	if(user.getIsAdmin() == 1){
        		for(String permissionCode: (List<String>)param.get("permission_codes")) {
    	        	map.put(permissionCode, true);
    	        }
        	}else{
        		String groupType = param.get("group_type").toString();
        		List<Integer> permissionIds = perService.getPermissionIdsByUserId(user.getUserId(),groupType);
				List<Integer> rolePerIds = roleService.getRolePermissionsByUser(user.getUserId(),groupType);
				List<Integer> per = new ArrayList<Integer>();
	    		per.addAll(permissionIds);
	    		per.addAll(rolePerIds);
	    		per = new ArrayList<Integer>(new LinkedHashSet<>(per));
    	        for(String permissionCode: (List<String>)param.get("permission_codes")) {
    	        	Integer permissionId = userService.getPermissionId(permissionCode,groupType);
    	        	map.put(permissionCode, per.contains(permissionId));
    	        }
        	}
			return ResponseFormatUtil.formatResponse(map);
		}catch(JSONException e){
			throw new MmsException(ErrorCodeEnum.PARAMSERROR);
		}catch (NumberFormatException e) {
			throw new MmsException(ErrorCodeEnum.PARAMSERROR);
		}
	}
	
	@ApiOperation(value="获取用户信息",notes="参数:token,group_type")
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public Map<String,Object> getUserInfo(@RequestParam Map<String,Object> param) throws MmsException{
		ParamsProcessUtil.checkMapParamsNotNullAndThrows(param, new String[]{"group_type","token"});
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			Map<String,Object> user = userService.getUserIdByToken(param.get("token").toString());
			if(user == null ||user.isEmpty()){
				map.put("error", ErrorCodeEnum.USERNOTEXSIS);
				return ResponseFormatUtil.formatResponse(map);
			}
			UserSession us = usService.getUserSessionByToken(param.get("token").toString());
			if(us.getExpireTime().getTime() < new Date().getTime()){
				map.put("error", ErrorCodeEnum.TOKENOUTTIME);
				return ResponseFormatUtil.formatResponse(map);
			}
			Integer userId = Integer.valueOf(user.get("user_id").toString());
			User userp = new User();
			userp.setUserId(userId);
			List<String> groupTypes = userService.getUserGroupType(userp);
			if(! groupTypes.contains(param.get("group_type").toString())){
				map.put("error", ErrorCodeEnum.NOTINGROUP);
				return ResponseFormatUtil.formatResponse(map);
			}
			map.put("user", user);
			map.put("facility_list", userService.getFacilityIds(userId));
			map.put("merchant_list", userService.getMerchantIds(userId));
			if(user.get("is_admin") != null && (Integer)user.get("is_admin") == 1){
				map.put("permission_list", perService.getAllPermission(param.get("group_type").toString()));
			}else{
				List<Integer> permissionIds = perService.getPermissionIdsByUserId(userId,param.get("group_type").toString());
				List<Integer> rolePerIds = roleService.getRolePermissionsByUser(userId,param.get("group_type").toString());
				List<Integer> per = new ArrayList<Integer>();
	    		per.addAll(permissionIds);
	    		per.addAll(rolePerIds);
	    		per = new ArrayList<Integer>(new LinkedHashSet<>(per));
	    		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    		if(per.size() >0 )
	    			list = perService.getPermissionByIds(per);
				map.put("permission_list", list);
			}
			map.put("role_list", userService.getRoleList(userId,param.get("group_type").toString()));
			
			return ResponseFormatUtil.formatResponse(map);
		}catch(JSONException e){
			throw new MmsException(ErrorCodeEnum.PARAMSERROR);
		}catch (NumberFormatException e) {
			throw new MmsException(ErrorCodeEnum.PARAMSERROR);
		}
	}
	
	@ApiOperation(value="根据user_name获取用户信息",notes="参数:user_name")
	@RequestMapping(value="/byName",method=RequestMethod.GET)
	public Map<String,Object> getUserInfoByName(@RequestParam(value="user_name") String name){
		Map<String,Object> map = new HashMap<String, Object>();
		User userp = new User();
		userp.setUserName(name);
		User user = userService.selectByUser(userp);
		if(user == null){
			map.put("error", ErrorCodeEnum.USERNOTEXSIS);
			return ResponseFormatUtil.formatResponse(map);
		}
		List<Integer> roleIds = roleService.getRoleIdsByUser(user);
		List<Integer> facilityIds = facilityService.getFacilityByUser(user);
		List<Integer> merchantIds = merchantService.getMerchantByUser(user);
		List<String> groupTypes = userService.getGroupByUser(user);
		user.setRoleList(roleIds);
		user.setFacilityList(facilityIds);
		user.setMerchantList(merchantIds);
		user.setGroupTypes(groupTypes);
		map.put("email", user.getEmail());
		Map<String,Object> smap = new HashMap<String, Object>();
		smap.put("entity", map);
		return ResponseFormatUtil.formatResponse(smap);
	}
	
	@ApiOperation(value="获取菜单",notes="参数:token,group_type")
	@RequestMapping(value="/getMenuList",method=RequestMethod.GET)
	public Map<String,Object> getMenuList(@RequestParam Map<String,Object> param) throws MmsException{
		ParamsProcessUtil.checkMapParamsNotNullAndThrows(param, new String[]{"group_type","token"});
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> user = userService.getUserIdByToken(param.get("token").toString());
		if(user == null ||user.isEmpty()){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		Integer userId = Integer.valueOf(user.get("user_id").toString());
		User userp = new User();
		userp.setUserId(userId);
		List<String> groupTypes = userService.getUserGroupType(userp);
		if(! groupTypes.contains(param.get("group_type").toString())){
			map.put("error", ErrorCodeEnum.NOTINGROUP);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserSession us = usService.getUserSessionByToken(param.get("token").toString());
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
		
		List<Menu> list = getTypeMenuList(param,user);
		map.put("data", list);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	private List<Menu> getTypeMenuList(Map<String,Object> param,Map<String,Object> user){
		List<Menu> thirdMenus = menuService.getThirdMenu(param.get("group_type").toString());
		List<Menu> list = new ArrayList<Menu>();
		if(Integer.valueOf(user.get("is_admin").toString()) == 1){
			list = menuService.getAllMenu(param);
		}else{
			List<Integer> permissionIds = perService.getApiPermissionIds(param);
			List<Integer> roleIds = roleService.getApiRoleIds(param);
			List<Integer> per = new ArrayList<Integer>();
			per.addAll(permissionIds);
			if(roleIds.size() > 0) {
	    		List<Integer> rolePerIds = roleService.selectByIds(roleIds);
	    		per.addAll(rolePerIds);
	    		per = new ArrayList<Integer>(new LinkedHashSet<>(per));
	    	}
			list = menuService.getApiMenuList(per,param.get("group_type").toString());
		}
		for(Menu menu:list){
			for(Menu childMenu:menu.getChildMenus()){
				List<Menu> tempMenu = new ArrayList<Menu>();
				for(Menu thirdMenu:thirdMenus){
					if(thirdMenu.getParentId().equals(childMenu.getMenuId()))
						tempMenu.add(thirdMenu);
				}
				childMenu.setChildMenus(tempMenu);
			}
		}
		return list;
	}
	
	@ApiOperation(value="检测邮箱是否绑定",notes="参数:token")
	@RequestMapping(value="/checkEmail",method=RequestMethod.GET)
	public Map<String,Object> checkEmailIsBind(@RequestParam String token){
		Map<String,Object> map = new HashMap<String, Object>();
		User user = userService.getUserByToken(token);
		if(user == null){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserSession us = usService.getUserSessionByToken(token);
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
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
	
	@ApiOperation(value="绑定邮箱",notes="参数:email,token,url",produces="application/json")
	@RequestMapping(value="/bindEmail",method=RequestMethod.POST)
	@Transactional
	public Map<String,Object> bindEmail(@RequestBody Map<String,Object> param) throws MmsException, NoSuchAlgorithmException, ParseException{
		Map<String,Object> map = new HashMap<String, Object>();
		if(! checkEmail(param.get("email").toString())){
			map.put("error", ErrorCodeEnum.EMAILFORMERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		User user = userService.getUserByToken(param.get("token").toString());
		if(user == null){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserSession us = usService.getUserSessionByToken(param.get("token").toString());
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
		user.setEmail(param.get("email").toString().trim());
		user.setEmailStatus("CHECKING");
		userService.updateUser(user);
		userTokenService.updateUserTokenOutValidByType(user.getUserId(),"email_bind");
		
		if(! user.getEmailStatus().equals("CHECKING")){
			map.put("error", ErrorCodeEnum.EMAILNOTATCHECKINT);
			return ResponseFormatUtil.formatResponse(map);
		}
		
		Date lastTokenTime = userTokenService.getUserLastValidTokenTimeByType(user.getUserId(),"email_bind");
		return userService.send(user,"email_bind",lastTokenTime,param.get("url").toString());
	}
	
	private boolean checkEmail(String email){
		String reg = "([a-z0-9A-Z]+[-|_|\\.]?)+@(yiran\\.com|ibenben\\.com)";
		return email.matches(reg);
	}
	
	@ApiOperation(value="验证接口",notes="参数:token_type,user_token",produces="application/json")
	@RequestMapping(value="/verifyEmail",method=RequestMethod.POST)
	public Map<String,Object> verifyEmail(@RequestBody Map<String,Object> param,HttpServletRequest request) throws MmsException, NoSuchAlgorithmException{
		ParamsProcessUtil.checkMapParamsNotNullAndThrows(param, new String[]{"token_type","user_token"});
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
	
	@ApiOperation(value="发送重置密码邮件",notes="参数:email,user_name,url",produces="application/json")
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
		map = userService.send(user,"password_reset",tokenTime,param.get("url").toString());
		return map;
	}
	
	@ApiOperation(value="获取用户仓库",notes="参数:token")
	@RequestMapping(value="/facility/list",method=RequestMethod.GET)
	public Map<String, Object> getUserFacilities(@RequestParam String token) throws MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		User user = userService.getUserByToken(token);
		if(user == null){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserSession us = usService.getUserSessionByToken(token);
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
		List<Facility> fList = userFacilityService.getUserFacilityList(user.getUserId());
		if(fList.size() == 0){
			throw new MmsException(ErrorCodeEnum.USERFACILITYNOTEXIST);
		}
		return ResponseFormatUtil.formatResponse(fList);
	}
	
	@ApiOperation(value="获取用户商户",notes="参数:token")
	@RequestMapping(value="/merchant/list",method=RequestMethod.GET)
	public Map<String, Object> getUserMerchants(@RequestParam String token) throws MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		User user = userService.getUserByToken(token);
		if(user == null){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserSession us = usService.getUserSessionByToken(token);
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
		List<Merchant> mList = userMerchantService.getUserMerchantList(user.getUserId());
		if(mList.size() == 0){
			throw new MmsException(ErrorCodeEnum.USERMERCHANTNOTEXIST);
		}
		return ResponseFormatUtil.formatResponse(mList);
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
	/**
	 * 验证token是否过期
	 */
	@ApiOperation(value="验证token是否过期",notes="token")
	@RequestMapping(value="/isOutTime",method=RequestMethod.GET)
	public Map<String,Object> isTokenOutTime(@RequestParam String token){
		Map<String,Object> map = new HashMap<String, Object>();
		UserSession us = usService.getUserSessionByToken(token);
		if(us == null){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("response", 1);
			return ResponseFormatUtil.formatResponse(map);
		}
		map.put("response", 0);
		return ResponseFormatUtil.formatResponse(map);
	}
	/**
	 * 修改密码
	 * @throws NoSuchAlgorithmException 
	 * @throws MmsException 
	 */
	@ApiOperation(value="修改密码",notes="old_password,new_password,token",produces="application/json")
	@RequestMapping(value="/modifyPass",method=RequestMethod.POST)
	public Map<String,Object> modifyPassword(@RequestBody Map<String,Object> param) throws NoSuchAlgorithmException, MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		if(param.get("old_password").equals(param.get("new_password"))){
			map.put("error", ErrorCodeEnum.PASSWORDSAME);
			return ResponseFormatUtil.formatResponse(map);
		}
		User user = userService.getUserByToken(param.get("token").toString());
		
		if(user == null){
			map.put("error", ErrorCodeEnum.TOKENERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserSession us = usService.getUserSessionByToken(param.get("token").toString());
		if(us.getExpireTime().getTime() < new Date().getTime()){
			map.put("error", ErrorCodeEnum.TOKENOUTTIME);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(! user.getPassword().equals(param.get("old_password").toString())){
			map.put("error", ErrorCodeEnum.PASSWORDERROR);
			return ResponseFormatUtil.formatResponse(map);
		}
		user.setPassword(param.get("new_password").toString());
		user.setLastUpdatedPasswordTime(new Date());
		userService.updateUser(user);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	@ApiOperation(value="获取ip列表",notes="参数:token")
	@RequestMapping(value="/getIpList",method=RequestMethod.GET)
	public Map<String,Object> getIpList(@RequestParam String token){
		List<String> list = ipService.getIpList();
		return ResponseFormatUtil.formatResponse(list);
	}
	
	@ApiOperation(value="获取当前用户业务组织",notes="参数:token")
	@RequestMapping(value="/getParty",method=RequestMethod.GET)
	public Map<String,Object> getParty(@RequestParam String token){
		List<Map<String,Object>> list = partyService.getPartyByUser(token);
		return ResponseFormatUtil.formatResponse(list);
	}
}
