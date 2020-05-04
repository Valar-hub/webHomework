
package com.yichuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yichuan.entity.Message;

/** 
  *MessageDao 聊天信息DAO
  * @Author  gz
  * @CreateDate  2018年1月12日 下午3:55:34 
  * @Description  
  */
public interface MessageDao {
	
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
	public List<Message> queryMessage(@Param("fromUID")String fromUID,@Param("toUID")String toUID);
	
	/**
	 * 删除聊天记录
	 * @param fromUID
	 * @param toUID
	 * @return int
	 */
	public int deleteMessage(@Param("fromUID")String fromUID,@Param("toUID")String toUID);
	
	/**
	 * 批量删除聊天信息
	 * @param UIDlist
	 * @return int
	 */
	public int deleteMoreMessage(@Param("UIDlist")List<String> UIDlist,@Param("UID")String UID);
	
	//public int deleteMoreMessage(List<String> UIDlist);

	
}
