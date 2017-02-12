package com.ibenben.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibenben.domain.Facility;
import com.ibenben.domain.Merchant;
import com.ibenben.domain.User;
import com.ibenben.domain.UserToken;
import com.ibenben.enumtuil.ErrorCodeEnum;
import com.ibenben.exception.MmsException;
import com.ibenben.mapper.ApiUrlMapper;
import com.ibenben.mapper.FacilityMapper;
import com.ibenben.mapper.MerchantMapper;
import com.ibenben.mapper.PermissionMapper;
import com.ibenben.mapper.UserMapper;
import com.ibenben.util.GetIpAddrUtil;
import com.ibenben.util.Md5Util;
import com.ibenben.util.ResponseFormatUtil;
import com.ibenben.util.UserUtil;
import com.ibenben.util.page.PageUtil;
@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PermissionMapper perMapper;
	
	@Autowired
	private UserTokenService userTokenService;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private ApiUrlMapper urlMapper;
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Autowired
	private FacilityMapper facilityMapper;
	@Transactional(rollbackFor={Exception.class})
	public Map<String,Object> addOrUpdateUser(User user,List<String> groupList, List<Integer> facilityList, List<Integer> merchantList, List<Integer> roleList, List<Integer> permissionList) throws NoSuchAlgorithmException {
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("group_types", groupList);
		param.put("facility_ids", facilityList);
		param.put("merchant_ids", merchantList);
		param.put("role_ids", roleList);
		param.put("permission_ids", permissionList);
		if(user.getUserId() != null && user.getUserId() != 0) {
			param.put("user_id", user.getUserId());
			userMapper.delUserGroup(param);
			userMapper.updateByPrimaryKeySelective(user);
			userMapper.delUserFacility(param);
			userMapper.delUserMerchant(param);
			userMapper.delUserRole(param);
			userMapper.delUserPermission(param);
		} else {
			if(! user.getUserName().matches("([a-zA-Z0-9_\\.\\-])+")){
				map.put("error", ErrorCodeEnum.USERNAEMERROR);
				return ResponseFormatUtil.formatResponse(map);
			}
			if(isExist(user.getUserName())){
				map.put("error", ErrorCodeEnum.USERNAMEEXIST);
				return ResponseFormatUtil.formatResponse(map);
			}
			user.setIsAdmin((byte) 0);
			user.setEmailStatus("FROZEN");
			user.setPassword(Md5Util.md5("888888", 32));
			user.setCreatedTime(new Date());
			user.setParentId(UserUtil.getAdminUser().getUserId());
			userMapper.insert(user);
			param.put("user_id", user.getUserId());
		}
		userMapper.editUserGroup(param);
		userMapper.editUserFacility(param);
		userMapper.editUserMerchant(param);
		if(roleList != null && roleList.size() > 0)
			userMapper.editUserRole(param);
		if(permissionList != null && permissionList.size()> 0)
			userMapper.editUserPermission(param);
		return ResponseFormatUtil.formatResponse(map);
		
	}
	
	
	public Integer getPermissionId(String pc,String gt){
		return perMapper.getPermissionIds(pc,gt);
	}
	
	public List<Map<String,Object>> getUserList(Map<String,Object> param){
		List<Integer> merchantIds = UserUtil.getAdminUser().getMerchantList();
		if(merchantIds.size() != 0){
			List<String> userNames = userMapper.getUserByMerchant(merchantIds);
			param.put("user_names", userNames);
		}
		userMapper.updateGroupConcatMaxLen();
		PageUtil.setPageCondition(param);
		return userMapper.getUserList(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int editUser(Map<String,Object> param) throws MmsException{
		return userMapper.editUser(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int editUserFacility(Map<String,Object> param)throws MmsException{
		return userMapper.editUserFacility(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int editUserMerchant(Map<String,Object> param)throws MmsException{
		return userMapper.editUserMerchant(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int editUserRole(Map<String,Object> param)throws MmsException{
		return userMapper.editUserRole(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int editUserPermission(Map<String,Object> param)throws MmsException{
		return userMapper.editUserPermission(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int resetUserPassword(Map<String,Object> param)throws MmsException{
		return userMapper.resetUserPassword(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int delUser(Map<String,Object> param)throws MmsException{
		return userMapper.delUser(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int clearUserEmail(Map<String,Object> param)throws MmsException{
		return userMapper.clearUserEmail(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int addUser(Map<String,Object> param)throws MmsException{
		return userMapper.addUser(param);
	}
	
	public User getUserInfo(Integer id){
		return userMapper.selectByPrimaryKey(id);
	}
	
	public List<Map<String,Object>> getFacilityIds(Integer id){
		return userMapper.getFacilityIds(id);
	}
	
	public List<Map<String,Object>> getMerchantIds(Integer id){
		return userMapper.getMerchantIds(id);
	}
	
	public List<Map<String,Object>> getPermissionIds(Integer id,String gt){
		return userMapper.getPermissionIds(id,gt);
	}
	
	public int recoverUser(Map<String,Object> param){
		return userMapper.recoverUser(param);
	}
	
	public Map<String,Object> getUserIdByToken(String token){
		return userMapper.getUserIdByToken(token);
	}
	
	public List<Map<String,Object>> getRoleList(Integer id,String gt){
		return userMapper.getRoleList(id,gt);
	}
	
	public User selectByUser(User u){
		return userMapper.selectByUser(u);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int updateUser(User user) throws MmsException{
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	public User getUserByToken(String token){
		return userMapper.getUserByToken(token);
	}
	
	public List<String> getUserGroupType(User user){
		return userMapper.getUserGroupType(user);
	}
	
	public User selectByApiUser(User user){
		return userMapper.selectByApiUser(user);
	}
	
	public List<String> getMerchantIdsByUser(int userId, int isAdmin) {
		if(isAdmin == 1) {
			return merchantMapper.getMerchantIds();
		}
		return userMapper.getMerchantIdsByUser(userId);
	}
	
	public List<String> getFacilityIdsByUser(int userId, int isAdmin) {
		if(isAdmin == 1) {
			return facilityMapper.getFacilityIds();
		}
		return userMapper.getFacilityIdsByUser(userId);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int verifyUserEmail(Map<String,Object> param) throws MmsException{
		return userMapper.verifyUserEmail(param);
	}
	
	public User getUserByEmail(Map<String,Object> param){
		return userMapper.getUserByEmail(param);
	}
	
	public String getUrl(String desc){
		return urlMapper.getUrl(desc);
	}
	
	public List<String> getGroupByUser(User user){
		return userMapper.getGroupByUser(user);
	}
	
	public Map<String,Object> verifyToken(Map<String,Object> param,HttpServletRequest request) throws MmsException{
		Map<String,Object> map = new HashMap<String, Object>();
		userTokenService.updateOutTimeToken();
		if(! param.get("user_token").toString().matches("[a-z0-9]+")){
			logger.debug("danger:user_id="+param.get("user_id")+",ip="+GetIpAddrUtil.getIpAddr(request)+",url="+GetIpAddrUtil.getUrl(request));
			map.put("error", ErrorCodeEnum.ILLEGALSIGN);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(! param.get("user_id").toString().matches("[0-9]+")){
			logger.debug("danger:user_id="+param.get("user_id")+",ip="+GetIpAddrUtil.getIpAddr(request)+",url="+GetIpAddrUtil.getUrl(request));
			map.put("error", ErrorCodeEnum.ILLEGALSIGN);
			return ResponseFormatUtil.formatResponse(map);
		}
		UserToken ut = userTokenService.getToken(param);
		if(ut == null){
			map.put("error", ErrorCodeEnum.ILLEGALOPERATE);
			return ResponseFormatUtil.formatResponse(map);
		}
		if(ut.getIsValid() == 0){
			map.put("error", ErrorCodeEnum.TOKENUNEFFECT);
			return ResponseFormatUtil.formatResponse(map);
		}
		userTokenService.updateOutValidToken(param);
		return ResponseFormatUtil.formatResponse(map);
	}
	
	public Map<String,Object> send(User user,String type,Date lastTokenTime,String url) throws NoSuchAlgorithmException, MmsException, ParseException{
		MimeMessage message = mailSender.createMimeMessage();
		Map<String,Object> map = new HashMap<String, Object>();
		if(lastTokenTime == null){
			lastTokenTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 00:00:00");
		}
		if(new Date().getTime()/1000 - lastTokenTime.getTime()/1000 < 60){
			Long leftTime = 60 - (new Date().getTime() - lastTokenTime.getTime());
			map.put("error_code","11111");
			map.put("error_message", "您的操作太频繁，请等待"+leftTime+"秒后操作");
			map.put("result", "fail");
			return ResponseFormatUtil.formatResponse(map);
		}
		
		String sendToken = Md5Util.md5(user.getUserId().toString()+new SimpleDateFormat("HHmmss").format(new Date()), 32).substring(2, 10);
		sendToken += String.format("%04d", user.getUserId());
		Date expiredTime = new Date((new Date().getTime()+5*60*1000));
		userTokenService.updateUserTokenOutValidByType(user.getUserId(), type);
		int t = userTokenService.addUserToken(user.getUserId(),sendToken,expiredTime,type);
		
		if(!(t>0)){
			map.put("error", ErrorCodeEnum.TOKENCREATEEX);
			return ResponseFormatUtil.formatResponse(map);
		}
		String verifyUrl = url+"?token_type="+type+"&user_token="+sendToken;
		try{
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom("verification@ibenben.com");
			helper.setTo(user.getEmail());
			helper.setSubject(user.getUserName()+",请在五分钟内验证您的邮箱");
			helper.setText("<h2>验证码："+sendToken+"</h2> <br><strong>您也可以点击链接进行验证：</strong><a target='_blank' href='"+verifyUrl+"'>"+verifyUrl+"</a>",true);
			mailSender.send(message);
			return ResponseFormatUtil.formatResponse(map);
		}catch(MessagingException e){
			map.put("error", ErrorCodeEnum.SENDMAILFAIL);
			return ResponseFormatUtil.formatResponse(map);
		}
	}
	
	public List<Merchant> getMerchantByUser(int userId) {
		if(UserUtil.getAdminUser().getIsAdmin() == 1) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", "OK");
			return merchantMapper.getAllMerchantList(map);
		}
		return userMapper.getMerchantByUser(userId);
	}
	
	public List<Facility> getFacilityByUserMerchant(int userId) {
		if(UserUtil.getAdminUser().getIsAdmin() == 1) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", "OK");
			return facilityMapper.getFacilityList(map);
		}
		return userMapper.getFacilityByUserMerchant(userId);
	}
	
	public boolean isExist(String userName){
		User user = new User();
		user.setUserName(userName);
		if(userMapper.selectByUser(user) != null){
			return true;
		}
		return false;
	}
	
	public User selectByName(User user){
		return userMapper.selectByName(user);
	}
}


