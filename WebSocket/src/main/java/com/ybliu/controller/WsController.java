package com.ybliu.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ybliu.domain.YbliuMessage;
import com.ybliu.domain.YbliuResponse;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月9日 下午6:43:14
* 类说明
*/
@Controller
public class WsController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/welcome")
	@SendTo("/topic/getResponse")
	public YbliuResponse say(YbliuMessage message) throws Exception{
		Thread.sleep(1000);
		return new YbliuResponse("Welcome,"+message.getName()+"!");
	}
	
	@MessageMapping("/chat")
	public void handleChat(Principal principal,String msg){
		if(principal.getName().equals("ybliu")){
			messagingTemplate.convertAndSendToUser("ybliu1", "/queue/notifications", principal.getName()+"-send:"+msg);
		}else{
			messagingTemplate.convertAndSendToUser("ybliu", "/queue/notifications", principal.getName()+"-send"+msg);
		}
	}
}
