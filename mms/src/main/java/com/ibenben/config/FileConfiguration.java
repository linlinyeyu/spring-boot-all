package com.ibenben.config;

import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibenben.service.ExcelService;

@Configuration
public class FileConfiguration{

    @Bean 
    public FileCleanerCleanup fileCleanerCleanup(){
    	return new FileCleanerCleanup();
    }
    
    @Bean(name="ExcelBuilder")
    public ExcelService excelBuilder(){
    	return new ExcelService();
    }
}

