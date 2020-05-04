<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	pageContext.setAttribute("basePath", request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>

    <link rel="stylesheet" type="text/css" href="${basePath}/static/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/static/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/static/css/demo.css">
	<script type="text/javascript" src="${basePath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/jquery-1.8.3.js"></script>
    
</head>

<script type="text/javascript">
    function chat2() {
    	/* var aa = $("#ee").html();
    	alert(aa); */
    	window.open ('${basePath}/chat.jsp','newwindow', 'height=800, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no') 
	}
    function submitForm(){
		$('#ff').form('submit');
	}
	function clearForm(){
		$('#ff').form('clear');
	}
</script>

<body>
    <!-- 管理员登陆  -->
    <br><br><br>
	<div style="width: 400px;height: 500px;margin: 0 auto;">
	<h2>用户登陆</h2>
	<div class="easyui-panel" title="管理员登陆入口" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form id="ff" method="post" action="${basePath}/User/login" >
	    	<table cellpadding="5">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox" type="text" name="name"></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-textbox" type="password" name="password"></input></td>
	    		</tr>
	    	</table>
	    	<div style="text-align:center;padding:5px;">
	    	<input type="submit" value="登       陆" class="easyui-linkbutton" style="width: 80px; height: 25px;"/>
	    	&nbsp;&nbsp;&nbsp;&nbsp;
	    	<input type="reset" value="重        置"  class="easyui-linkbutton" style="width: 80px; height: 25px;"/>
	    </div>
	    </form>
	    </div>
	</div>
	</div>
	
	
	
 	<br><br><br>
	<div>
	<h2>游客入口</h2>
	<div>
	<a href="${basePath}/chat.jsp" target="_blank"><img alt="测试" src="${basePath}/static/image/test.jpg" width="50px;" height="50px;"><br>
	<strong>在线客服</strong></a>
	</div>
	
	
	<div>
	<br><br><br>
	<a href="javascript:void(0)" onclick="chat2()"><img alt="测试" src="${basePath}/static/image/test2.jpg" width="50px;" height="50px;"><br>
	<strong id="ee">在线客服</strong></a>
	</div>
	
	
	<%--
	<br><br><br>
	<h3><a href="${basePath}/User/queryAll">测试访问数据库user信息</a></h3>
	<h3><a href="${basePath}/Message/queryAll">测试访问数据库message信息</a></h3><br>
	<h3><a href="${basePath}/Message/save">新增聊天message信息</a></h3><br>
	</div> --%>
</body>
</html>