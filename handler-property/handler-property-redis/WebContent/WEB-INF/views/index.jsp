<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.min.js" type="text/javascript"></script>  
<html>
<head>
<meta charset="utf-8">
<title>智慧平台</title>
<script type="text/javascript" src="${ctx }/js/jquery.min.js"></script>
</head>
<body>
	
<input type="button" value="新增" onclick="add();"/>
<input type="button" value="查询" onclick="query();"/>
<input type="button" value="更新" onclick="update();"/>
<input type="button" value="清空缓存" onclick="reloadCache();"/>
<input type="button" value="发送" onclick="sendMessage()">
<script type="text/javascript">
  function add(){
	  $.getJSON("${ctx}/user/saveDeveloper",function(){
		  
	  })
  }
  function query(){
	  $.getJSON("${ctx}/user/getDeveloper",function(){
		  
	  })
  }
  function update(){
	  $.getJSON("${ctx}/user/updateDeveloper",function(){
		  
	  })
  }
  
  function reloadCache(){
	  $.getJSON("${ctx}/user/reloadCache",function(){
		  
	  })
  }
  var client;
  
  $(function(){
	  client = new Paho.MQTT.Client("tcp://192.168.1.179", Number("61615"), "cloudringADBroker","admin","password");//建立客户端实例  
	  client.onConnectionLost = onConnectionLost;//注册连接断开处理事件  
	  client.onMessageArrived = onMessageArrived;//注册消息接收处理事件  
	  client.connect({onSuccess:onConnect});//连接服务器并注册连接成功处理事件  
  })
  function sendMessage(){
	  //发送消息  
	 var  message = new Paho.MQTT.Message("hello");  
	 message.destinationName = "/topic";  
	 client.send(message);  
  }
  
  function onConnect() {  
      console.log("onConnected");  
      client.subscribe("/topic");//订阅主题  
  }  
  
  function onConnectionLost(responseObject) {  
      if (responseObject.errorCode !== 0) {  
          console.log("onConnectionLost:"+responseObject.errorMessage);  
          console.log("连接已断开");  
       }  
  }  
  
  function onMessageArrived(message) {  
    console.log("收到消息:"+message.payloadString);  
  }  

</script>
</body>

</html>
