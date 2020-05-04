
package com.yichuan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yichuan.entity.Message;
import com.yichuan.entity.User;



/** 
  * UserService 用户信息 接口
  * @Author  gz
  * @CreateDate  2017年12月25日 下午3:18:00 
  * @Description  
  */
public interface UserService {
	
	/**
	 * 查询所有用户信息
	 * @return List<User>
	 */
	public List<User> queryAll();
	
	
	/**
	 * 查询单个用户(用于登陆验证)
	 * @param username 用户名
	 * @param password 密码
	 * @return User
	 */
	public User queryUser(String username,String password);
	

	
	

}
