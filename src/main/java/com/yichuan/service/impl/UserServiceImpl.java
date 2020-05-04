
package com.yichuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yichuan.dao.UserDao;
import com.yichuan.entity.User;
import com.yichuan.service.UserService;

/** 
  *
  * @Author  gz
  * @CreateDate  2017年12月25日 下午3:19:09 
  * @Description  
  */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao uDao;

	//查询所有用户信息
	public List<User> queryAll() {
		return uDao.queryAll();
	}

	//查询单个用户信息 登陆
	@Override
	public User queryUser(String username, String password) {
		return uDao.queryUser(username, password);
	}

}
