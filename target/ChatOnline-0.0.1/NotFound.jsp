<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404错误页面提示</title>
</head>
<body>
  <div>
	<h1>出现404错误，访问路径出错。</h1>
	<br><br><br>
	
	
      <h3>用户信息：</h3>
	<table>
		<tr>
			<td>ID</td>
			<td>用户名</td>
			<td>密码</td>
		</tr>
		<c:forEach items="${userList}" var="users">
		<tr>
			<td>${users.id}</td>
			<td>${users.username}</td>
			<td>${users.password}</td>
		</tr>
		</c:forEach>
	</table>
	<br><hr><hr>
	   <h3>聊天信息：</h3>
	<table>
		<tr>
			<td>ID</td>
			<td>消息内容</td>
			<td>发送时间</td>
			<td>发送者ID</td>
			<td>接收者ID</td>
		</tr>
		<c:forEach items="${messages}" var="message">
		<tr>
			<td>${message.MID}</td>
			<td>${message.MContent}</td>
			<td>${message.MTime}</td>
			<td>${message.MFromUserID}</td>
			<td>${message.MToUserID}</td>
		</tr>
		</c:forEach>
	</table>
	</div>
	<hr>
	<div style="width: 400px;height: 400px;border:2px solid red;"> 测试  </div>
	<div style="width: 400px;height: 400px;border:2px solid red;"> 测试  </div>
	
</body>
</html>