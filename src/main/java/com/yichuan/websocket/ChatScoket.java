
package com.yichuan.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ThresholdingOutputStream;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.yichuan.entity.Message;
import com.yichuan.entity.User;
import com.yichuan.service.MessageService;
import com.yichuan.util.Status;

import net.sf.json.JSONArray;

/**
 * 在线聊天
 * 
 * @Author gz
 * @CreateDate 2017年12月26日 上午11:41:37
 * @Descriptions
 */
@ServerEndpoint(value = "/ChatOnLine", configurator = HttpSessionConfigurator.class)
public class ChatScoket {

	// 此处尤其要注意，无法使用注解注入service层接口，只能是在spring-mybatis.xml配置文件中配置service的bean，然后通过此方法获取service层服务
	private MessageService mService = (MessageService) ContextLoader.getCurrentWebApplicationContext()
			.getBean("mService");

	private static Logger logger = Logger.getLogger(ChatScoket.class);

	private static int onlineCount = 0; // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private User user;// 保存当前会话的用户信息

	/* 以用户id作为key值 保存用户的session对象 */
	private static Map<String, Session> map = new HashMap<String, Session>(); // 保存用户名和对应的session
	private static Map<String, Session> adminMap = new HashMap<String, Session>(); // 保存管理员和对应的session
	private static List<User> userlist = new ArrayList<User>(); // 在线列表访客信息
	// private static Set<User>
	private HttpSession httpSession; // request的session 用于获取登录用户的信息
	private String message = null; // 保存系统提示信息
	private String chatContent = null; // 保存聊天信息
	private String msgString = null; // 保存格式化处理后的信息
	private String toUID = null;// 接受者ID

	public ChatScoket() {
		// logger.info("日志：" + new Date().toLocaleString() +
		// "打开聊天通道.................");
		logger.info("--------------------------------初始化ChatScoket类，打开新的通讯管道-----------------");
	}



	@OnOpen
	public void onpen(Session session, EndpointConfig config) {
		/*System.out.println("获取session ID："+session.getId());
		System.out.println("获取session ID："+session.getRequestURI());*/
		// logger.info("日志：" + new Date().toLocaleString() +
		// "开启新的session会话.................");

		// 获取当前登录用户的session从而获取用户信息
		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		this.user = (User) httpSession.getAttribute("user"); // 从httpSession中获取当前登录的用户
		this.user.setStatus(Status.online);// 设置当前用户在线状态
		// System.out.println("当前登录用户信息："+this.user);

		/* 1.判断当前登录是否是客服登陆 */
		if ("admin".equals(this.user.getUsername())) { // 1.1 如果是管理员
			this.adminMap.put(this.user.getId(), session);
			this.message = "欢迎客服<font color='blue'>  " + this.user.getUsername() + "</font> 使用服务平台，祝工作愉快!";
			// 推送消息给当前登录的管理员 欢迎字样 在线人数 在线用户
			this.msgString = getMsgJson(userlist, message, onlineCount, null, null, null, null);
			singleChat(session, msgString);

			// 广播消息给所有在线人员(除管理员)
			this.message = "<font color='green'>客服   " + this.user.getUsername() + "  已经登录上线。</font>";
			this.chatContent = "您好，有什么可以帮助您?";
			this.msgString = getMsgJson(null, message, 0, chatContent, "left", "4", this.user.getId());
			broadcast(map, this.msgString);
		} else { // 1.2 如果当前不是管理员
			addUserInfo(session);// 添加用户相关信息,防止用户重复登录

			/*
			 * this.map.put(this.user.getId(), session); // 保存在线用户和对应的session
			 * this.userlist.add(this.user); // 保存在线用户信息 this.onlineCount++; //
			 * 记录在线人数
			 */

			/* 2. 判断是否有客服已经登陆在线 */
			Session adminSession = adminMap.get("4");// 获取客服的session
			if (adminSession != null) { // 2.1 游客登录 有客服在线
				// 向客服推送消息 （在线人数 和 在线人名称 上线提醒（后期删除））
				this.message = "用户<font color='blue'>  " + this.user.getUsername() + "</font> 已登录在线 ";
				this.msgString = getMsgJson(userlist, message, onlineCount, null, null, null, null);
				singleChat(adminSession, msgString);

				// 向当前登录用户推送消息
				this.message = "欢迎<font color='blue'>  " + this.user.getUsername() + "</font> 使用在线咨询服务";
				this.chatContent = "您好，有什么可以帮助您?";
				this.msgString = getMsgJson(null, message, 0, chatContent, "left", "4", this.user.getId());
				singleChat(session, msgString);
			} else { // 2.1如果没有客服在线 向当前访客推送消息
				this.message = "对不起，客服<font color='red'>不在线</font>，有事请留言，谢谢!";
				this.msgString = getMsgJson(null, message, 0, null, null, null, null);
				singleChat(session, msgString);
			}

		}

	}

	@OnMessage
	public void onmessage(Session session, String msg) {
		// System.out.println("接收到客户端信息：" + msg);
		JSONObject jsStr = JSONObject.parseObject(msg);// 转换成json数据
		this.chatContent = jsStr.getString("message");// 获取聊天内容
		String type = jsStr.getString("type"); // 获取对话类别 1 表示客服给游客发消息 ；2
												// 表示游客给客服发消息
		Session toSession = null;// 用于保存通话对方的session

		if ("1".equals(type)) { // 表示客服给游客发消息
			System.out.println("客服给游客发消息时，userlist中的游客信息：" + userlist.toString());
			System.out.println("客服给游客发消息时，map中的游客信息：" + map.toString());
			this.toUID = jsStr.getString("ToID");// 获取接收者访客的ID
			// 将聊天信息保存到数据库
			saveChatInfo(chatContent, user.getId(), toUID);
			toSession = map.get(toUID);// 获取游客session
			this.msgString = getMsgJson(null, null, 0, chatContent, "left", user.getId(), toUID);// other
			singleChat(toSession, msgString);// 将消息推送给游客
			this.msgString = getMsgJson(null, null, onlineCount, chatContent, "left", user.getId(), toUID);// myself
			singleChat(session, msgString);// 将消息推送给自己
		} else if ("2".equals(type)) { // 游客给客服发消息
			// 将聊天信息保存到数据库
			saveChatInfo(chatContent, user.getId(), "4");
			toSession = adminMap.get("4");
			if (toSession != null) { // 如果客服在线
				this.msgString = getMsgJson(null, null, 0, chatContent, "right", user.getId(), "4"); // myself
				singleChat(session, msgString);// 将消息推送给当前游客自身
				this.msgString = getMsgJson(null, null, onlineCount, chatContent, "right", user.getId(), "4");// other
				singleChat(toSession, msgString);// 将消息推送给客服

			} else { // 如果客服不在线
				this.msgString = getMsgJson(null, null, 0, chatContent, "right", user.getId(), "4");// myself
				singleChat(session, msgString);// 将消息推送给当前游客自身
				this.message = "对不起，客服<font color='red'>不在线</font>，有事请留言，谢谢!";// 系统消息
				this.msgString = getMsgJson(null, message, 0, null, null, null, null);// 只推送系统提示消息
				singleChat(session, msgString);// 将消息推送给当前游客自身
			}
		}
	}

	/* 当用户登陆后或生成了游客信息保存到session后，刷新页面时会发送新的请求 */
	@OnClose
	public void close() {
		System.out.println(new Date().toLocaleString() + "\t" + this.user.getUsername() + "\t执行@OnClose方法");

		if ("admin".equals(this.user.getUsername())) { // 如果是管理员退出，则给所有人发消息
			//if (this.adminMap.remove(this.user.getId()) != null) { // 如果清除信息成功(如果map中不存在对应的信息，而执行清除操作，返回null值),则执行相应的操作
				this.adminMap.remove(this.user.getId());
				this.message = "客服   " + this.user.getUsername() + "   <font color='red'>已下线</font> ,有事请留言，谢谢。";
				this.msgString = getMsgJson(null, message, 0, null, null, null, null);
				broadcast(map, this.msgString);
				// 遍历所有查找所有的游客，并删除聊天记录
				/*
				 * boolean flag = false; List<String> UIDlist = new
				 * ArrayList<String>(); for (String key : map.keySet()) { if
				 * (key.length() == 32) { UIDlist.add(key); flag = true; } } if
				 * (flag) { deleteMoreMessage(UIDlist,this.user.getId()); }
				 */
			//}
		} else { // 如果游客退出，只给管理员发消息
			//当清除用户信息操作成功时，返回true。注意：此处清除的是当前用户对象，当当前用户登录时userlist中已经存在该用户信息，
			//那么当前用户相关信息就不会被保存，故当此处清除用户信息失败(说明当前用户没有保存)，也不会清除用户相关信息
			if (this.userlist.remove(this.user)) {
				this.userlist.remove(this.user);
				this.map.remove(this.user.getId());
				this.user.setStatus(Status.online);// 设置当前用户在线状态

				System.out.println("用户退出后，userlist中用户信息是：");
				for (User user : userlist) {
					System.out.println(user);
				}
				System.out.println();

				onlineCount--;
				// this.message = "用户 " + this.user.getUsername() + " <font
				// color='bule'>已下线</font><br/>";
				this.msgString = getMsgJson(userlist, null, onlineCount, null, null, null, null);
				Session session = adminMap.get("4");// 获取管理员session
				if (session != null) {
					singleChat(session, msgString);
				}

				// 如果是未注册的游客，则删除聊天信息
				if (user.getId().length() == 32) {
					deleteChatInfo(user.getId(), "4");
				}
			}
		}
		// 删除聊天信息
		logger.info("日志：" + new Date().toLocaleString() + "  用户  " + this.user.getUsername() + " 关闭聊天通道！");
	}

	@OnError
	public void onError(Session session, Throwable t) {
		this.message = "<font color='red'>连接断开，消息发送失败!</font>";
		this.msgString = getMsgJson(null, message, 0, null, "error", null, null);
		singleChat(session, msgString);
		logger.info("日志：" + new Date().toLocaleString() + " @OnError 聊天通道连接错误！");
	}

	/**
	 * 格式化数据 用于将相关信息推送到页面显示
	 * 
	 * @param
	 *            在线这列表
	 * @param message
	 *            系统提示消息
	 * @param onlineCount
	 *            当前在线人数
	 * @param chatContent
	 *            聊天内容
	 * @param type
	 *            消息类型 用于确定消息显示在左边还是右边
	 * @param formUID
	 *            发送者ID
	 * @param toUID
	 *            接收者ID
	 * @return String json格式的string类型数据
	 */
	public String getMsgJson(List<User> userlist, String message, int onlineCount, String chatContent, String type,
			String formUID, String toUID) {
		JSONObject jsonMsg = new JSONObject();
		JSONArray userJson = null;
		if (userlist != null) {
			userJson = JSONArray.fromObject(userlist);
			/*
			 * for (int i = 0; i < userJson.size(); i++) {
			 * System.out.println(userJson.getString(i)); }
			 */
		}
		jsonMsg.put("message", message);
		jsonMsg.put("userList", userJson);
		jsonMsg.put("count", onlineCount);
		jsonMsg.put("chatContent", chatContent);
		jsonMsg.put("type", type);
		jsonMsg.put("formUID", formUID);
		jsonMsg.put("toUID", toUID);
		return jsonMsg.toString();
	}

	/**
	 * 广播消息
	 * 
	 * @param map
	 *            游客信息
	 * @param message
	 *            聊天信息
	 */
	private void broadcast(Map<String, Session> map, String message) {
		System.out.println("broadcast() 广播消息  map中访客个数：" + map.size());
		for (String key : map.keySet()) {
			try {
				map.get(key).getBasicRemote().sendText(message);
			} catch (IOException e) {
				logger.info("日志：广播消息推送异常" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对特定用户发送消息
	 * 
	 * @param session
	 *            信息接收者session
	 * @param infoChat
	 *            聊天信息
	 */
	private void singleChat(Session session, String infoChat) {
		try {
			session.getBasicRemote().sendText(infoChat);
		} catch (IOException e) {
			logger.info("日志：消息发送失败！！！！" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 保存聊天信息到数据库
	 * 
	 * @param chatContent
	 *            聊天内容
	 * @param toUID
	 *            发送者ID
	 * @param fromUID
	 *            void 接收者ID
	 */
	public void saveChatInfo(String chatContent, String fromUID, String toUID) {
		System.out.println("saveChatInfo()  获取的数据：" + chatContent + "/" + fromUID + "/" + toUID);
		Message message = new Message(chatContent, new Date(), fromUID, toUID);
		int num = mService.saveMessage(message);
		if (message.getMID() != 0) {
			logger.info("日志：" + new Date().toLocaleString() + "聊天信息保存成功！！！！");
		} else {
			System.err.println("聊天信息保存失败！！！！");
			logger.info("日志：" + new Date().toLocaleString() + "聊天信息保存失败！！！！");
		}
	}

	/**
	 * 删除聊天信息
	 * 
	 * @param formID
	 * @param toID
	 *            void
	 */
	public void deleteChatInfo(String fromUID, String toUID) {
		System.out.println("deleteChatInfo()  接收的fromUID/toUID 是：" + fromUID + "/" + toUID);
		try {
			int num = mService.deleteMessage(fromUID, toUID);
			if (num != 0) {
				logger.info("日志：" + new Date().toLocaleString() + "已成功删除聊天信息： " + num + " 条。");
			} else if (num == 0) {
				logger.info("日志：" + new Date().toLocaleString() + "该访客暂无聊天信息。");
			}
		} catch (Exception e) {
			System.err.println("聊天信息删除失败！！！！");
			logger.info("日志：" + new Date().toLocaleString() + "聊天信息删除失败！！！！");
			e.printStackTrace();
		}

	}

	/**
	 * 批量删除聊天信息
	 * 
	 * @param UIDlist
	 *            访客的id集合
	 */
	public void deleteMoreMessage(List<String> UIDlist, String UID) {
		try {
			int num = mService.deleteMoreMessage(UIDlist, UID);
			if (num != 0) {
				logger.info("日志：" + new Date().toLocaleString() + "已成功删除聊天信息： " + num + " 条。");
			} else if (num == 0) {
				logger.info("日志：" + new Date().toLocaleString() + "无匹配的聊天信息可删除。");
			}
		} catch (Exception e) {
			System.err.println("聊天信息删除失败！！！！");
			logger.info("日志：" + new Date().toLocaleString() + "聊天信息删除失败！！！！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 存储访客信息
	 * 防止重复添加相同的访客信息
	 * @param session void
	 */
	public void addUserInfo(Session session) {
		// 遍历查找当前用户是否已经登录
		if (this.userlist.size() != 0) { // 当用户个数大于等于1时，就需要判断是否有重复用户
			boolean flag = true;
			for (User user : userlist) {
				System.out.println(user.getId().equals(this.user.getId()));
				if (user.getId().equals(this.user.getId())) { // 当已经存在的用户id和当前id相同是时候改变标识
					flag = false;
					break;
				}
			}
			if (flag) {
				this.map.put(this.user.getId(), session); // 保存在线用户和对应的session
				this.userlist.add(this.user); // 保存在线用户信息
				this.onlineCount++; // 记录在线人数
			}
		} else {
			this.map.put(this.user.getId(), session); // 保存在线用户和对应的session
			this.userlist.add(this.user); // 保存在线用户信息
			this.onlineCount++; // 记录在线人数
			System.out.println("直接保存用户信息成功!!!");
		}

		System.out.println("保存信息后，userlist中用户信息是：");
		for (User user : userlist) {
			System.out.println(user);
		}
		System.out.println();
	}
	

}
