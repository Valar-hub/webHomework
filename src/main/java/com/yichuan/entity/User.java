
package com.yichuan.entity;

import com.yichuan.util.Status;

/** 
  * 用户 pojo类
  * @Author  gz
  * @CreateDate  2017年12月25日 下午2:50:26 
  * @Description  
  */
public class User {
	
	private String id;
	
	private String username;
	
	private String password;
	
	/**
	 * 枚举 记录用户在线状态
	 * 设置默认不在线
	 */
	private Status status=Status.offline;
	
	

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getUsername() {
		return username;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}
	


	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", status=" + status + "]";
	}
	

	
	
	

}
