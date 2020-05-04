
package com.yichuan.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.yichuan.entity.User;
import com.yichuan.util.Status;

import net.sf.json.JSONObject;
import sun.print.resources.serviceui;

/**
 *
 * @Author gz
 * @CreateDate 2018年1月3日 上午11:09:31
 * @Description
 */
public class Test {

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}

	/**
	 * 获得指定数目的UUID
	 * @param number  int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] retArray = new String[number];
		for (int i = 0; i < number; i++) {
			retArray[i] = getUUID();
		}
		return retArray;
	}

	public static void main(String[] args) {
		
		
		//Map<Integer, List<Map<Integer, String>>> mapA = new HashMap<>();
		
		Map<Integer, Map<Integer, String>> mapA = new HashMap<>();
		
		//List<Map<Integer, String>> listAA = new ArrayList<>();
		
		Map<Integer, String> mapAa = new HashMap<>();
		mapAa.put(10, "张三01");	
		mapAa.put(20, "张三02");	
		mapAa.put(30, "张三03");	
		mapAa.put(40, "张三04");	
		mapAa.put(50, "张三05");	
		
		Map<Integer, String> mapAb = new HashMap<>();
		mapAb.put(11, "李四01");	
		mapAb.put(12, "李四02");	
		mapAb.put(13, "李四03");	
		mapAb.put(14, "李四04");	
		mapAb.put(15, "李四05");	
		
		Map<Integer, String> mapAc = new HashMap<>();
		mapAc.put(111, "王五01");	
		mapAc.put(112, "王五02");	
		mapAc.put(113, "王五03");	
		mapAc.put(114, "王五04");	
		mapAc.put(115, "王五05");	
		
		mapA.put(1, mapAa);
		mapA.put(2, mapAb);
		mapA.put(3, mapAc);
		
		for (Integer Id : mapA.keySet()) {
			System.out.println("mapA 的key："+Id);
			System.out.println("保存的信息如下：");
			Map<Integer, String> maptemp = mapA.get(Id);
			for (Integer  id: maptemp.keySet()) {
				System.out.println(id+"\t"+maptemp.get(id));
			}
			System.out.println("----------------------------------------------");
			System.out.println();
		}
		
		
	/*	Map<Integer, String> map = new HashMap<>();
		map.put(1, "12");
		map.put(1, "12");
		
		map.remove(2);
		System.out.println(map.size());
		
		List<User> list = new ArrayList<>();
		//Set<User> uSet = new 
		User user =new User();
		user.setId("110");
		user.setPassword("123456");
		user.setUsername("张三");
		
		User user1 =new User();
		user.setId("112");
		user.setPassword("123456");
		user.setUsername("张三");
		
		User user2 =new User();
		user.setId("113");
		user.setPassword("123456");
		user.setUsername("张三");
		
		User user3 =new User();
		user.setId("110");
		user.setPassword("123456");
		user.setUsername("张三");
		
		
		list.add(user);
		list.add(user1);
		list.add(user2);
		
		
		System.out.println(list.contains(user3));
		*/
/*		System.out.println(new Date().toString());
		System.out.println(new Date().toLocaleString());
		System.out.println(new Date().toGMTString());*/
		
	/*	System.out.println((int)((Math.random()*9+1)*100000));
		System.out.println(Math.random());
		System.out.println();
		for (int i = 0; i < 20; i++) {
			System.out.println((int)((Math.random()*9)*100000));
		}*/
	/*	System.out.println();
		List<Integer> list =new ArrayList<>();
		for (int i = 0; i < 2000; i++) {
			int aa=(int)((Math.random()*9+1)*100000);
			list.add(aa);
		    System.out.println(i+"\t"+aa);
		}
		
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			int a =list.get(i);
			for (int j = i+1; j < list.size(); j++) {
				if (a == list.get(j)) {
					flag = true;
				System.out.println("0000000");
				}
			}
		}*/
		
	/*    List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("bb");
        list.add("ee");
        list.add("dd");
        list.add("ff");*/
		
   /*     int temp;
        for (int i = 0; i < list.size(); i++)
        {
            temp = list.get(i);
            for (int j = i + 1; j < list.size(); j++)
            {
                if (temp == list.get(j))
                {
                    System.out.println("第" + (i) + "个跟第" + (j) + "个重复，值是：" + temp);
                }
            }
        }*/
	/*	if (flag) {
			System.out.println("有重复值！");
		}else{
			System.out.println("没有重复值！！");
		}*/
		
		/*
		 * Map<String, String> map = new HashMap<String, String>(); //
		 * 保存用户名和对应的session map.put("01", "张三"); map.put("02", "李四");
		 * System.out.println(map.remove("00"));
		 * System.out.println(map.remove("02"));
		 */

		
	/*	for (int i = 0; i < 10; i++) {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			System.out.println(uuid.length());
		}*/
		 
		
		/*String a = UUID.randomUUID().toString();
		System.out.println(a);
		System.out.println();*/
		
		/*User user = new User();
		user.setStatus(Status.offline);
		
		System.out.println(user.getStatus());*/
		
	}
}
