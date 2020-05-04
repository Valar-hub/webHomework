
package com.yichuan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yichuan.entity.User;


/** 
  *UserDao
  * @Author  gz
  * @CreateDate  2017年12月25日 下午2:51:48 
  * @Description  
  */
public interface UserDao {
	
	/**
	 * 查询所有用户信息
	 * @return List<User>
	 */
	public List<User> queryAll();
	
	/**
	 * 查询单个用户(用于登陆验证)
	 * @param userName 用户名
	 * @param password 密码
	 * @return User
	 */
	public User queryUser(@Param("username")String username,@Param("password")String password);

}
