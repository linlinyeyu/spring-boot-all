/**
 * 
 */
package com.ibenben.config.datasource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sszheng
 *
 * Created on 2016年7月22日 上午11:51:17
 */
public class DynamicDataSourceContextHolder {
	private final static Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static List<String> dataSourceNames = new ArrayList<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
//        logger.info("修改数据源" + dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
//        logger.info("恢复数据源");
    }

    public static boolean containsDataSource(String dataSourceName){
        return dataSourceNames.contains(dataSourceName);
    }
}
