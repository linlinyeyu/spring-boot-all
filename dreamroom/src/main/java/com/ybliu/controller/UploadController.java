package com.ybliu.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
*@author ybliu
*@version 创建时间 :2016年12月15日下午10:49:08
*
*/
@Controller
public class UploadController {
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public @ResponseBody String upload(MultipartFile file){
		try{
			FileUtils.writeByteArrayToFile(new File("e:/upload/"+file.getOriginalFilename()),file.getBytes());
			return "OK";
		}catch(IOException e){
			e.printStackTrace();
			return "wrong";
		}
	}
}
