package com.yichuan.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回类
 * 作用是：给客户端返回服务器执行完操作后是否成功的信息
 * @author gongZheng
 * @Date 2017年8月17日下午3:14:31
 * @version 1.0
 */
public class Msg {
	
	//状态码 返回100 成功； 返回200 失败
	private int code;
	
	//提示信息
	private String msg;
	
	//保存要返回给客户端数据信息
    private Map<String, Object> result = new HashMap<String,Object>();
	
	// 定义两个方法，表示成功和失败
    /**
     * 处理成功 
     * @return Msg对象
     */
	public static Msg success() {
		Msg msg = new Msg();
		msg.setCode(100);
		msg.setMsg("操作成功！！！");
		return msg;
	}
	
	/**
     * 处理失败
     * @return Msg对象
     */
	public static Msg fail() {
		Msg msg = new Msg();
		msg.setCode(200);
		msg.setMsg("操作失败！！！");
		return msg;
	}
	
	
	public Msg add(String key,Object value){
		this.getResult().put(key, value);
		return this;//返回正在调用此方法的对象
	}
    
    
    
    
    public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getResult() {
		return result;
	}
	

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	
	
	
	

    
    
    
}
