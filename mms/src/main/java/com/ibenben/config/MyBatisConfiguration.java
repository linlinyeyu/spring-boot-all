/**
 * 
 */
package com.ibenben.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;
/**
 * @author sszheng
 *
 * Created on 2016年7月12日 下午3:51:32
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.ibenben.mapper")
public class MyBatisConfiguration implements TransactionManagementConfigurer{
	
	@Autowired
	private DataSource ds;
	
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource ds) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setTypeAliasesPackage("tk.mybatis.springboot.model");
        
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        properties.setProperty("cacheEnabled", "true");
        properties.setProperty("lazyLoadingEnabled", "false");
        properties.setProperty("aggressiveLazyLoading", "true");
        
        pageHelper.setProperties(properties);
        
        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	bean.setConfigLocation(resolver.getResource("classpath:mybatis.xml"));
            bean.setMapperLocations(resolver.getResources("classpath:mybatis/**/*Mapper.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name="transactionManager")
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(ds);
    }
}
