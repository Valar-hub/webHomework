
package com.yichuan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yichuan.entity.Message;

/** 
  * MessageService 聊天信息接口
  * @Author  gz
  * @CreateDate  2018年1月12日 下午4:00:41 
  * @Description  
  */
public interface MessageService {
	
    /**
     * 查询所有聊天信息
     * @return List<Message>
     */
	public List<Message> queryAll();
	
	/**
	 * 新增聊天记录
	 * @param message
	 * @return int
	 */
	public int saveMessage(Message message);
	
	/**
	 * 查询聊天信息
	 * @param fromUID 发送者ID
	 * @param toUID   接收者ID
	 * @return List<Message>
	 */
	public List<Message> queryMessage(String fromUID,String toUID);
	

	/**
	 * 删除聊天记录
	 * @param fromUID
	 * @param toUID
	 * @return int
	 */
	public int deleteMessage(String fromUID,String toUID);
	
	/**
	 * 批量删除聊天信息
	 * @param UIDlist
	 * @return int
	 */
	public int deleteMoreMessage(List<String> UIDlist,String UID);

}
