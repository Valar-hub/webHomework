<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% pageContext.setAttribute("basePath", request.getContextPath());%>
<!-- ${pageContext.request.contextPath}等价于request.getContextPath()
      如： <c:set var="ctx" value="${pageContext.request.contextPath}"/> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线聊天</title>
<link href="${basePath}/static/css/global.css" rel="stylesheet">
<link href="${basePath}/static/css/styles.css" rel="stylesheet">
</head>


<body>
	<div class="main">
		<!-- 左侧栏 显示在线人数和其他相关信息 -->
		<div class="User-Info">
			<!-- ********************左侧显示当前用户信息*********************** -->
			<div class="currentUser">
			<c:choose>
				<c:when test="${user.id eq '4'}">
					<img src="${basePath}/static/image/test.jpg" class="userImage" >&nbsp;&nbsp;
				<span class="userName" id="onlineName"></span>
				<p style="margin-left: 50px;" >当前咨询人数：<strong id="count"></strong></p>
				</c:when>
				<c:otherwise>
				<img src="${basePath}/static/image/test4.jpg" class="userImage">&nbsp;&nbsp;
				<span class="userName" id="onlineName"></span>
				</c:otherwise>
			</c:choose>
			</div>

			<!-- 当前在线用户列表 -->
			<div class="user-list" id="userList">

				 <%--     <div class="user-item selected">
                      <img src="${basePath}/static/image/test4.jpg" class="head">
                      <span class="userName" title="标题提示">当前用户 </span><br>
                      <input type="hidden" value="110" class="userId"/>
					  <div class="close"><span class="closeBtn">×</span></div>
					  <span style="color: red;display: inline;margin-left: 47px;" class="remind">10 条未读消息</span>
					  <!-- <div style="position: absolute;top: 0;left: 0;width: 198px;height: 70px;background: rgba(0,0,0,0.2);"></div> -->
                  </div>   --%> 
                  
                  
			</div>
			<p style="margin-left: 5px;">
				技术支持：<a href="#">XXXX科技有限公司</a>
			</p>
		</div>


		<!-- 展示聊天信息 -->
		<div class="chat-bg" >
			<header class="header-title" id="title">标题</header>
			
			<!-- *********************展示聊天内容************************** -->
			<section class="chat-box">
			<div class="bubbleDiv" id="talkInfo">

				<!-- <div class="bubbleItem">
					<span class="bubble2 centerBubble">wewewe</span>
				</div> -->
				
			</div>
			</section>
			
			<footer class="chat-edit clearfix"> 
			<!-- 上传文件图片按钮 -->
			  <span class="uploadImg fl">
			    <input type="file" name="uploadfile" class="uploadfile">
			  </span> 
			 <!-- *********************输入聊天内容，并发送************************** -->
			 <textarea id="chatContent" rows="4" class="chat-info" placeholder="想咨询他什么内容..."></textarea> <!-- <p class="chat-info" placeholder="想咨询他什么内容..." contenteditable="true"></p> -->
			 <button class="send-btn fr" id="subsend" onclick="subSend()" >发送</button>
			 
			</footer>
		</div>
		
	</div>
	
	<script src="${basePath}/static/js/jquery-1.8.3.min.js"></script>
	<script src="${basePath}/static/js/chat.js"></script>
	<script type="text/javascript" src="${basePath}/static/js/jquery-1.8.3.js"></script>
	<script>
	 //客服图像
    //var leftImgSrc = 'http://120.76.230.93:8063/Upload/2017/0914/20170914092809690_55e736d12f2eb9384eade4fed6628535e4dd6ffe.jpg';
    var adminImgSrc = "${basePath}/static/image/test.jpg";
    
    //访客图像
    //var rightImgSrc = 'http://120.76.230.93:8063/Upload/2017/1116/20171116180954252_img.png';
    var userImgSrc ="${basePath}/static/image/test4.jpg";
    	
	var tempName = "";
	var ws = null;
	var target = null;
	//获取登录用户信息
	var administrator = '${userJson}';
	//alert(administrator);
	if (administrator == "" || administrator == undefined || administrator.length == 0){
		window.location.href="${basePath}/User/login";
	}
	var user =JSON.parse(administrator);//由JSON字符串转换为JSON对象
	tempName = user.username; //取出用户名
	console.log(user);
	
	/* *************************************   进入该页面直接打开websocket通讯连接   **************************************/
 window.onload = function() {
		// 判断是否是管理员登陆
		if (tempName == "admin") {
			//显示当前用户名
			$("#onlineName").html("客服：<strong>" + tempName+ "</strong>");
		} else {
			//显示当前用户名
			$("#onlineName").html(tempName);
			$("#title").html("欢迎使用在线咨询服务");
			//加载聊天信息
			chatRecord(user.id, 4);
			//显示系统消息
			/*  var systemMessage='<div class="bubbleItem">'+
            '<span class="bubble2 centerBubble">'+
              '<font size="1px" color="#888" >'+
                 '--------------------以上是聊天记录消息-------------------</font></span></div>';
			 $("#talkInfo").append(systemMessage); */
		}

		target = "ws://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}${pageContext.request.contextPath}/ChatOnLine";
		//判断当前浏览器是否支持websocket通讯
		if ('WebSocket' in window) {
			ws = new WebSocket(target);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(target);
		} else {
			alert('对不起，该浏览器不支持websocket！！！');
			return;
		}
		
		/* ******************************************当websocket创建成功时，即会触发onopen事件 *******************************/
		ws.onopen = function(){  
			
		};
		
		/* ******************************************解析后台传回的消息,并予以展示 *******************************/
		 ws.onmessage = function(event) {
			 var msg = JSON.parse(event.data);
			//eval("var msg=" + event.data + ";");
			console.log(msg);
			
			/* --------------------- 显示系统提示信息 ------------------------ */
			if (undefined != msg.message) {
				if (msg.type == "error") { //消息发送失败的系统提示
					$("#talkInfo").append('<div class="bubbleItem">'
	                        +'<span class="bubble2 centerBubble"><img src="${basePath}/static/image/failure.png" width="14px;" height="14px;"/>&nbsp;<font size="3px">'
			                  +msg.message + '</font></span></div>');
				}else{
					$("#talkInfo").append('<div class="bubbleItem">'
					                        +'<span class="bubble2 centerBubble"><font size="3px">'
							                + msg.message + '</font></span></div>');
				}
				$("#talkInfo").scrollTop(999999);
			} 
			
			/* ----------------------显示左侧在线人员列表--------------------- */
			if (user.id == 4) { //判断当前是否是管理员登陆
				if (undefined != msg.userList) {
					$("#userList").html("");
					var userInfo;
					var tempUser;
					$.each(msg.userList, function(index, temp) {
						if (index == 0) { //设置默认选中第一个人
							 userInfo='<div class="user-item selected" title="'+temp.username+'" ><img src="${basePath}/static/image/test4.jpg" class="head"> &nbsp;&nbsp;<span class="userName">'
							            +temp.username+'</span><input type="hidden" value="'+temp.id+'" class="userId"/>'
							            +'<div class="close"><span class="closeBtn">×</span></div></div>';
						     $("#title").html(temp.username);//默认将该用户名加载到标题
	                         $('.chat-info').val(''); //清空输入框
	                         $(".bubbleDiv").html('');//清空聊天框
						     chatRecord(user.id, temp.id)//默认加载该用户的聊天信息 		     
						}else{
							userInfo='<div class="user-item" title="'+temp.username+'" ><img src="${basePath}/static/image/test4.jpg" class="head"> &nbsp;&nbsp;<span class="userName">'
							         +temp.username+'</span><input type="hidden" value="'+temp.id+'" class="userId"/> '+'<div class="close"><span class="closeBtn">×</span></div></div>';
						}
						
						$("#userList").append(userInfo);
					}); 
				}

			}
			
			/*------------------------ 显示当前在线人数 ------------------------*/
			if (undefined != msg.count) {
				$("#count").html(msg.count);
			}
			
			/*------------------------- 展示服务端推送的即时聊天信息 --------------------*/
			if (undefined != msg.chatContent) { //判断消息不为空
				// 1. 判断当前是否是客服登陆
				if (user.id == "4") { // 1.1 当前是管理员 
					//判断消息发送者是否是管理员
					if (msg.formUID == "4") { //消息的发送者是管理员的话，显示在右边
						chat("rightBubble",adminImgSrc,msg.chatContent);
					} else {  //如果不是管理员的话
						var toUID = $('.selected').find('.userId').val();//获取当前选中的用户ID
					    //判断当前页面（管理员页面）,当前选中的用户ID是否是发送者id
					    if (msg.formUID == toUID) { //如果是当前选中的用户，则直接显示消息
					    	chat("leftBubble",userImgSrc,msg.chatContent);
						} else { //如果不是，则给出未读消息提示
							
							var html = '<span style="color: green;display: inline;margin-left: 47px;" class="remind" >新消息！</span>';
							//添加当前提示信息前，先移除之前添加的信息，防止重复添加
							$(".user-list input[value='"+ msg.formUID+"']").parent('.user-item').find('.remind').remove();
							$(".user-list input[value='"+ msg.formUID+"']").parent('.user-item').append(html);
							window.setInterval(changeText, 800);  //让changeText 多久循环一次 
							//console.log($(".user-list input[value='"+ msg.formUID+"']").parent('.user-item').hasClass('selected'),$(".user-list input").val() == msg.formUID);
						}

					}
					
				} else { //1.2 当前不是管理员 判断消息发送者是否是当前用户 
					if (user.id == msg.formUID) {  // 消息发送者是当前用户   显示在右侧
						chat("rightBubble",userImgSrc,msg.chatContent);
					} else { //否则，消息显示在左侧
						chat("leftBubble",adminImgSrc,msg.chatContent);
					}
				}
			    $("#talkInfo").scrollTop(999999);
			}

	}
		
	/* *************************************websocket通讯连接异常  ************************************* */
       ws.onerror = function (evt) {
		  $("#talkInfo").append("<img src='${basePath}/static/image/failure.png' width='14px;' height='14px;'><font size='3px'>通信连接发生错误！！！</font><br/>");
	  }
	
       ws.onclose = function(event){
    	  // alert("已经与服务器断开连接 \n当前连接状态："+this.readyState);
       };
}
	
 /* 文字闪烁效果 */
function changeText(){
	$('.remind').css('color','green');  //默认值
	setTimeout(" $('.remind').css('color','white')",100); //第一次闪烁
	setTimeout( "$('.remind').css('color','green')",400); //第二次闪烁
};

		/* *************************************  关闭和刷新页面时，关闭websocket通道，如果不关闭，后台会出现报错显现   **************************************/
		window.onbeforeunload = function() {
			alert("即将离开该页面！！")
			ws.close();
			ws = null;
		}

		/* *************************************  发送消息到服务端  ************************************* */
		function subSend() {
			var message = $("#chatContent").val().trim();//获取聊天信息
			if (message == "") {
				alert("请输入聊天内容。。。。");
				return;
			}
			var obj = null;

			if (tempName == "admin") { //是管理员登陆
				var ToID = $('.selected').find('.userId').val();//获取选中的游客的id值
				//alert("获取的游客Id："+name);
				if (ToID == undefined) { //判断是否选中聊天对象
					alert("请选择聊天对象");
					return;
				}

				obj = {
					say : user.id, //说话人的名称
					message : message, //聊天消息
					ToID : ToID, //聊天对象
					type : 1
				// 1 表示客服给游客发消息  2 表示游客给客服发消息
				}
				ws.send(JSON.stringify(obj));
			} else { //如果是游客，则直接将聊天信息推送给管理员
				obj = {
					say : user.id, //说话人的名称
					message : message, //聊天消息
					type : 2
				// 1 表示客服给游客发消息  2 表示游客给客服发消息
				}
				ws.send(JSON.stringify(obj));
			}

			$("#chatContent").val("");//清空文本域框内容
			$("#chatContent").focus();//文本域框获取焦点事件
		}

		$(function() {
			/* ------------------------------左侧单击用户切换 --------------------------------*/
			$(".user-list").on('click','.user-item',function() {
						//清空输入框
						$('.chat-info').val('');
						//清空聊天框
						$(".bubbleDiv").html('');

						//给单击选中的对象添加样式，并移除其他对象的样式
						$(this).addClass('selected').siblings().removeClass('selected');
						//单击左侧用户列表对象后，获取用户名，将获取的用户名放到右侧上方标题栏中
						$(".header-title").html($(this).find('.userName').html());

						//加载聊天记录信息(此处客服端加载聊天信息，访客加载是在进入的时候)
						var toUID = $('.selected').find('.userId').val();//获取选中的游客的id值
						//清空未读消息提示
						$('.selected').find('.remind').css("display", "none");
						
						//alert("选中用户的ID："+toUID);
						//alert("当前用户的ID："+user.id);
						chatRecord(user.id, toUID)
					});

			//删除在线列表的某个用户
			$(".closeBtn").on('click', function(e) {
				$(this).parents('.user-item').remove();
				e.stopPropagation();
			});
			
		});

		
		
		
		/* 加载聊天记录信息  fromUID:发送者的id  toUID：接受者id*/
		function chatRecord(fromUID, toUID) {
					$.ajax({
						url : "${basePath}/Message/queryChatMessage",
						type : "get",
						data : {
							fromUID : fromUID,
							toUID : toUID
						},
						async : false,
						scriptCharset : 'utf-8',
						success : function(result) {
							console.log(result);
							if (result.length != 0) {
								$.each(result, function(index, temp) {
									/* 	var systemMessage='<div class="bubbleItem">'+
									   '<span class="bubble2 centerBubble">'+
									     '<font size="1px" color="#888" >'+temp.mtime
									     +'</font></span></div>';
									        $("#talkInfo").append(systemMessage);  */
									 //判断当前是否是管理员
									 if (user.id == "4") { // 当前是管理员
										 //判断当发送者是否是管理员
										 if (temp.mfromUserID == "4") { //发送者是管理员，显示在右边
											 chat("rightBubble", adminImgSrc,temp.mcontent);
										} else { //发送者不是管理员  ，显示在左边
											chat("leftBubble", userImgSrc,temp.mcontent);
										}
										
									} else {  //当前不是管理员
										if (temp.mfromUserID == "4") { //发送者是管理员 ，显示在左侧
											chat("leftBubble", adminImgSrc,temp.mcontent);
										} else { //发送者不是管理员 ，显示在右侧
											chat("rightBubble", userImgSrc,temp.mcontent);
										}
									}
									

								});

								var systemMessage = '<div class="bubbleItem">'
										+ '<span class="bubble2 centerBubble">'
										+ '<font size="1px" color="#888" >'
										+ '--------------------以上是聊天记录消息-------------------</font></span></div>';
							} else {
								var systemMessage = '<div class="bubbleItem">'
										+ '<span class="bubble2 centerBubble">'
										+ '<font size="1px" color="#888" >'
										+ '--------------------暂无聊天记录-------------------</font></span></div>';

							}
							$("#talkInfo").append(systemMessage);
						}
					});
			$("#talkInfo").scrollTop(999999);
		}

		/* 发送聊天信息  element 判断发送者对象(自己 or 对方)  imgSrc 图像路径   leftTextContent 说话内容*/
		function chat(element, imgSrc, leftTextContent) {
			//判断传入的值
			var $user = element;
			var $leftHead = imgSrc;
			var $textContent = leftTextContent;
			//获取容器
			var $box = $('.bubbleDiv');
			var $boxHeght = $box.height();
			var $sectionHeght = $(".chat-box").height();
			var $elvHeght = Math.abs($boxHeght - $sectionHeght);
			//医生
			if ($user === "leftBubble") {
				// $box.append(createdLeft(imgSrc,$textContent)).stop().animate({scrollTop: $(document).height()}, 300);
				$box.append(createdLeft(imgSrc, $textContent));
			} else if ($user === "rightBubble") {
				//$box.append(createRight(imgSrc,$textContent)).stop().animate({scrollTop: $(document).height()}, 300);
				$box.append(createRight(imgSrc, $textContent));
			}

		}

		//显示在左边的聊天信息
		function createdLeft(imgSrc, textContent) {
			var $textContent = textContent;
			var $imgSrc = imgSrc;
			var block;
			block = '<div class="bubbleItem">' + '<div class="left-head">'
					+ '<img src="'+ imgSrc +'" alt="doctor"/>' + '</div>'
					+ '<span class="bubble leftBubble">' + $textContent
					+ '<span class="topLevel"></span></span>' + '</div>';
			return block;
		};

		//显示在右边的聊天信息
		function createRight(imgSrc, textContent) {
			var $textContent = textContent;
			var block;
			block = '<div class="bubbleItem clearfix">'
					+ '<span class="bubble rightBubble" >' + $textContent
					+ '<span class="topLevel"></span></span>'
					+ '<div class="right-head">'
					+ '<img src="'+ imgSrc +'" alt="doctor"/>' + '</div>'
					+ '</div>';
			return block;
		};
	</script>

</body>
</html>