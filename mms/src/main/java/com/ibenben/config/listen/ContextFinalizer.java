/**
 * 
 */
package com.ibenben.config.listen;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * @author sszheng
 *
 * Created on 2016年10月19日 下午5:19:00
 */
@WebListener
public class ContextFinalizer implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ContextFinalizer.class);
    public void contextInitialized(ServletContextEvent sce) {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                logger.info(String.format("ContextFinalizer:Driver %s deregistered", d));
            } catch (SQLException ex) {
            	logger.error(String.format("ContextFinalizer:Error deregistering driver %s", d) + ":" + ex);
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
        	logger.error("ContextFinalizer:SEVERE problem cleaning up: " + e.getMessage());
        }
    }
}
