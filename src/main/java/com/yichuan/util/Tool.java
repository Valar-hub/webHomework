
package com.yichuan.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.websocket.Session;

/** 
  *
  * @Author  gz
  * @CreateDate  2017年12月27日 下午4:18:57 
  * @Description  
  */
public class Tool {
	
	
	public static String getName(Session session){
		//System.out.println("获取websocket参数："+session.getQueryString());
		//String[] nameArrary = session.getQueryString().split("=");
		//System.out.println(nameArrary[0]+"\t"+nameArrary[1]+"\t"+nameArrary[2]);
		String name = session.getQueryString().split("=")[1];
		System.out.println("获取前端传过来的用户信息："+name.toString());
		
		/* try {
			name=URLDecoder.decode(name,"utf-8");
			return name;
		} catch (UnsupportedEncodingException e) {
			System.out.println("转码异常");
			e.printStackTrace();
			return "游客000!";
		}*/
		return "admin";
		
	}

}
