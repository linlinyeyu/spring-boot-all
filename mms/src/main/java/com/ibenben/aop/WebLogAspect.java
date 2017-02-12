/**
 * 
 */
package com.ibenben.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibenben.domain.Action;
import com.ibenben.service.ActionService;
import com.ibenben.util.UserUtil;

/**
 * @author sszheng
 *
 * Created on 2016年7月13日 下午5:21:52
 */
@Aspect
@Component
public class WebLogAspect  {
	private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
	@Autowired
	private ActionService actionService;
	
	private static ThreadLocal<Long> threadLong = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.ibenben.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null) {
        	return ;
        }
        HttpServletRequest request = attributes.getRequest();
        
        if(joinPoint.getSignature().getName().contains("edit")){
        	addAction(request,joinPoint,"edit");
        }
        if(joinPoint.getSignature().getName().contains("add")){
        	addAction(request,joinPoint,"add");
        }	
        if(joinPoint.getSignature().getName().contains("del")){
        	addAction(request,joinPoint,"del");

        }
    }
    
    private void addAction(HttpServletRequest request,JoinPoint joinPoint,String type) {
    	Action action = new Action();
        action.setUrl(request.getRequestURI());
        action.setMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());   
        action.setCreatedTime(new Date());
        action.setCreatedUser(UserUtil.getCurrentUserName());
        if(joinPoint.getSignature().getDeclaringTypeName().contains("api")) {
        	action.setIsApi(1);
        }else{
        	action.setIsApi(0);
        }
        action.setType(type);
    	actionService.addAction(action);
    	threadLong.set(action.getId());
    }
    
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
    	if(ret != null){
    		if(ret.toString().contains("fail")){
        		Action action = new Action();
        		action.setError(ret.toString().substring(0,50));
        		action.setId(threadLong.get());
        		actionService.editAction(action);
        	}
    	}   	
    }
}
