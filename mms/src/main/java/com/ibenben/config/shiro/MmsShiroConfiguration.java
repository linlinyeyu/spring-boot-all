/**
 * 
 */
package com.ibenben.config.shiro;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.ibenben.domain.ShiroFilter;
import com.ibenben.mapper.ShiroFilterMapper;

/**
 * @author sszheng
 *
 * Created on 2016年7月12日 下午7:59:04
 */
@Configuration
@DependsOn("dynamicDataSourceAspect")
public class MmsShiroConfiguration {
	private final Logger logger = LoggerFactory.getLogger(MmsShiroConfiguration.class);
	private final static String LOGINURL = "/user/unlogin";
	private final static String UNAUTHORIZEDURL = "/user/unauthorized";
	private final static String SUCCESSURL = "/facade";
	
    @Bean
    public EhCacheManager getEhCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
        return em;  
    }  

    @Bean(name = "mmsShiroRealm")
    public MmsShiroRealm myShiroRealm(EhCacheManager cacheManager) {  
    	MmsShiroRealm realm = new MmsShiroRealm(); 
    	System.out.println(realm.isAuthenticationCachingEnabled());
        realm.setCacheManager(cacheManager);
        System.out.println(realm.isAuthenticationCachingEnabled());
        return realm;
    }  

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MmsShiroRealm mmsShiroRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(mmsShiroRealm);
        dwsm.setCacheManager(getEhCacheManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean, ShiroFilterMapper shiroFilterDao){
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        List<ShiroFilter> shiroFilterList = shiroFilterDao.findAll();
        for(ShiroFilter sf : shiroFilterList) {
        	filterChainDefinitionMap.put(sf.getKey(),sf.getValue());
        }
        logger.debug("设置过滤条件");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, ShiroFilterMapper shiroFilterDao) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new MmsShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(LOGINURL);
        shiroFilterFactoryBean.setSuccessUrl(SUCCESSURL);
        shiroFilterFactoryBean.setUnauthorizedUrl(UNAUTHORIZEDURL);

        loadShiroFilterChain(shiroFilterFactoryBean, shiroFilterDao);
        return shiroFilterFactoryBean;
    }

}
