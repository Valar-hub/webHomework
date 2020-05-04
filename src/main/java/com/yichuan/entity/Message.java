
package com.yichuan.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
  * 聊天信息  pojo类
  * @Author  gz
  * @CreateDate  2018年1月12日 下午3:23:46 
  * @Description  
  */
public class Message {
	
	private int MID;          //消息id 自增
	private String MContent;  //消息内容
	//private String MTime;     //发送消息时间
	private Date MTime;     //发送消息时间
	private String MFromUserID;  //发送者id 
	private String MToUserID;    //接收者id 
	
	
	
	public Message( String mContent, Date mTime, String mFromUserID, String mToUserID) {
		super();
		MContent = mContent;
		MTime = mTime;
		MFromUserID = mFromUserID;
		MToUserID = mToUserID;
	}



	public String getMFromUserID() {
		return MFromUserID;
	}
	


	public void setMFromUserID(String mFromUserID) {
		MFromUserID = mFromUserID;
	}
	


	public String getMToUserID() {
		return MToUserID;
	}
	


	public void setMToUserID(String mToUserID) {
		MToUserID = mToUserID;
	}
	


	public Message() {
		super();
	}


	public int getMID() {
		return MID;
	}
	
	public void setMID(int mID) {
		MID = mID;
	}
	
	public String getMContent() {
		return MContent;
	}
	
	public void setMContent(String mContent) {
		MContent = mContent;
	}
	
	public String getMTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(MTime);
	}
	
	

	public void setMTime(Date mTime) {
		MTime = mTime;
	}

	
	
	
	
	

}
