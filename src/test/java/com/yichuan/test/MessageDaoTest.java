
package com.yichuan.test;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yichuan.dao.MessageDao;
import com.yichuan.entity.Message;

/** 
  *MessageDao 测试
  * @Author  gz
  * @CreateDate  2018年1月18日 上午9:06:46 
  * @Description  主要用于测试对应的mapper文件中多种不同的insert标签配置情况下的测试结果
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mybatis.xml"})
public class MessageDaoTest {
	 private static Logger logger = Logger.getLogger(MessageDaoTest.class);
	@Autowired
	private MessageDao mDao;

	//insert 标签没有任何其他属性的配置 ，返回受影响的行数，不能返回主键ID
	@Test
	public void test() {
		Message m = new Message();
		m.setMContent("测试01");
		m.setMTime(new Date());
	    m.setMFromUserID("10");
	    m.setMToUserID("4");
	    int num = mDao.saveMessage(m);
	    System.out.println("新增后返回受影响的行数是："+num);
	    System.out.println("新增后的返回主键ID是："+m.getMID());
	}
	
	   //返回受影响的行数 和 主键ID
		@Test
		public void test1() {
			Message m = new Message("测试01", new Date(), "1", "4");
			/*Message m = new Message();
			m.setMContent("测试01");
			m.setMTime(new Date());
		    m.setMFromUserID(1);
		    m.setMToUserID(4);*/
		    int num = mDao.saveMessage(m);
		    System.out.println("新增后返回受影响的行数是："+num);
		    System.out.println("新增后的返回主键ID是："+m.getMID());
		}
		
		@Test
		public void test3(){
			String fromUID="2d4525cb9d35478db7d6dbea5cda346a";
			int num = mDao.deleteMessage(fromUID, "4");
			System.out.println(num);
			if (num != 0) {
				logger.info("日志：" + new Date().toLocaleString() + "聊天信息删除成功！！！！");
			} else {
				System.err.println("没有匹配的聊天信息可删除。");
				logger.info("日志：" + new Date().toLocaleString() + "无聊天信息");
			}
		}

		//测试批量删除
		@Test
		public void test4(){
			List<String> list = new ArrayList<>();
			list.add("789a81918b5045d0bc239d43c3aaaaea");
			list.add("74daaffffef14e989a53a3b385909e0a");
			list.add("59226a4f0f50425db8758cb1aa78a3fa");
			list.add("22257e63843449629ed9cb35dd58a87d");
			list.add("44f66c61e01a4c10b2fb4ea2384cf8e7");
			list.add("2dba3389e7604c9cad35fc034786a815");
		
			int num = mDao.deleteMoreMessage(list,"4");
			System.out.println(num);
			if (num != 0) {
				logger.info("日志：" + new Date().toLocaleString() + "聊天信息删除成功！！！！");
			} else {
				System.err.println("没有匹配的聊天信息可删除。");
				logger.info("日志：" + new Date().toLocaleString() + "无聊天信息");
			}
		}
	
}
