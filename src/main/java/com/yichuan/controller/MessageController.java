
package com.yichuan.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yichuan.entity.Message;
import com.yichuan.service.MessageService;

/**
 *
 * @Author gz
 * @CreateDate 2018年1月12日 下午4:11:19
 * @Description
 */
@Controller
@RequestMapping(value = "/Message")
public class MessageController {
	 private static Logger logger=Logger.getLogger(MessageService.class);

	@Autowired
	private MessageService mService;

	@RequestMapping(value = "/queryAll")
	public String queryAll(HttpServletRequest request) {
		System.out.println("进入message controller类...............");
		List<Message> mList = mService.queryAll();
		request.setAttribute("messages", mList);
		return "NotFound";
	}

	@ResponseBody
	@RequestMapping(value = "/queryChatMessage", method = RequestMethod.GET)
	public List<Message> queryChatMessage(String fromUID, String toUID) {
		/*
		 * System.out.println("获取的参数信息：");
		 * System.out.println("fromUID:"+fromUID);
		 * System.out.println("toUID:"+toUID);
		 */
		List<Message> mList = mService.queryMessage(fromUID, toUID);
		/*
		 * System.out.println("获取的相应的聊天信息："); for (Message message : mList) {
		 * System.out.println(message); }
		 */
		return mList;
	}

	@RequestMapping("/save")
	public String saveMessage() {
		Message message = new Message("测试controller", new Date(), "4", "1");
		int num = mService.saveMessage(message);
		System.out.println("获取的返回值是：" + num);
		return null;
	}

}
