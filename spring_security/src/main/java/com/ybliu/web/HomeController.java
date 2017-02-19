package com.ybliu.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ybliu.domain.Msg;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午11:45:01
* 类说明
*/
@Controller
public class HomeController {
	@RequestMapping("/")
	public String index(Model model){
		Msg msg = new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg",msg);
		return "home";
	}
}
