package com.ybliu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ybliu.domain.DemoObj;

@RestController
@RequestMapping(value="/rest")
public class DemoRestController {
	@RequestMapping(value="/getjson",produces={"application/json;charset=UTF-8"})
	public DemoObj getjson (DemoObj obj){
		return new DemoObj(obj.getId()+1,obj.getName()+"yy");
	}
	
	@RequestMapping(value="/getxml",produces={"application/xml;charset=UTF-8"})
	public DemoObj getxml(DemoObj obj){
		return new DemoObj(obj.getId()+1,obj.getName()+"yy");
	}
}
