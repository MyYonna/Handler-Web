<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>/static/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>/static/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>/static/css/chat.css">
		<script type="text/javascript"
		src="<%=basePath %>/static/js/jquery.min.js"></script>
		<script src="<%=basePath %>static/js/AgoraRtcAgentSDK-1.6.0.js"></script>
<style>
 .talk img{
  margin-top: 12px
 }
</style>	
</head>
<body>
	<div id="header">
		<div class="head-box">
			<a href="${ctx }/sys/main"><img src="${ctx }/static/img/logo2.png" alt=""></a>
			<h1>小西机器人后台管理系统</h1>
			<ul id="navMenu">
<!-- 				<li><a href="index.html">首页</a></li> -->
				<li id="count">
					<ul class="myCount">
						<li><a href="${ctx }/sys/userInfo.do">个人信息</a></li>
						<li><a href="#" class="exit">退出账户</a></li>
					</ul>
				</li>
				<li><a href="javascript:;" onclick="exitAction()">返回</a></li>
			</ul>
		</div>
	</div>
	<div id="chat">
		<div class="chat-box">
			<div class="chat-left">
				<div class="top">
					<div class="video" id="video">
						<p>小西机器人</p>
						<a href="javascript:;"> <img src="<%=basePath %>/static/img/mp4.png"
							alt="视频" id="chatVideo">
						</a> <a href="javascript:;"> <img
							src="<%=basePath %>/static/img/mp32.png" alt="对讲" id="chatVoice">
						</a>
					</div>
					<div class="records">
						<!-- records star -->
						<ul class="talkList">
						</ul>
						<!-- records star -->
					</div>
				</div>
				<div class="bottom">
					<ul class="tools">
					</ul>
				</div>
			</div>
			<div class="chat-right">
			</div>

		</div>
	</div>
	
<script type="text/javascript">
var oVideo = document.querySelector('.video');
var aImg = oVideo.querySelectorAll('img');
	var resolution = "480p",
    maxFrameRate = Number(15),
    channel = "Handler",  //房间名称
    key = "74a0b7bb5d3e47c7abca0533d17b0afa",//App id
    remoteStream,
    agoraclient = AgoraRTC.createRtcClient(),
    ableAudio = true,
    ableVideo = true,
    hideLocalStream = false,
    fullscreenEnabled = false,
    recordingServiceUrl = 'https://recordtest.agorabeckon.com:9002/agora/recording/genToken?channelname=' + channel,
    recording = false,
    uid,
    localStream,
    queryRecordingHandler,
    lastLocalStreamId,
    isMixed = false,
    isShared = false,
    isShowShareList = false;
	firstIn = true;

	var secret = "123456",//房间密码
    type = "aes-128-xts";//加密方式;
    //初始化客户端
    initAgoraRTC();
	//注册流的事件
	subscribeStreamEvents();
    //只有音频
    function onlyAudio(){
    	firstIn = false;
		aImg[0].src="<%=basePath %>static/img/mp3.png";
		aImg[1].src="<%=basePath %>static/img/mp42.png";
		flag = false;
		if(remoteStream != null){
	    	remoteStream.disableVideo(function(){
	    		console.info("禁用视频流成功");
	    	});
	    	//删除视频流的Dom
	    	$("#video"+remoteStream.getId()).remove();
	    	
	    	remoteStream.enableAudio(function(){
	    		console.info("音频启用成功")
	    	});
	    	//显示出麦克风
	    	aImg[0].style.display = "inline";
		}
    	//

   	}
    //启用视频频
    function onlyVideo(){
    	firstIn = false;
    	aImg[0].src="<%=basePath %>static/img/mp4.png";
		aImg[1].src="<%=basePath %>static/img/mp32.png";
		flag = true;
		if(remoteStream != null){
			//主动接入视频
			showStreamOnPeerAdded(remoteStream)
			
	   		remoteStream.enableAudio(function(){
	    		console.info("禁用音频流成功")
	    	}); 
			
	    	remoteStream.enableVideo(function(){
	    		console.info("视频启用成功");
	    	});
		}
    	 
    }
    //禁用掉所有
    function disableAll(){
    	if(remoteStream != null){
	    	remoteStream.disableVideo(function(){
	    		console.info("禁用视频流成功");
	    	});
	    	//删除视频流的Dom
	    	$("#video"+remoteStream.getId()).remove();
	    	
	    	remoteStream.disableAudio(function(){
	    		console.info("禁用音频流成功")
	    	})
    	}
    }
    //初始化客户端
	function initAgoraRTC() {
		agoraclient.init(key, function(obj) {
		agoraclient.setEncryptionSecret(secret);//
		agoraclient.setEncryptionMode(type);//
		agoraclient.join(key, channel, 0, function(uid) {
	            localStream = initLocalStream(uid);
	        });
	    }, function(err) {
	        console.log(err);
	    });
	};
	//流的监听事件
	function subscribeStreamEvents() {
		agoraclient.on('stream-added', function(evt) {
	        var stream = evt.stream;
	        if(remoteStream){
	        $("#video"+remoteStream.getId()).remove();
	        }
	        remoteStream = stream;
	        console.log("视频接入 " + stream.getId());
	        agoraclient.subscribe(stream, function(err) {
	            console.log("有视频接入失败", err);
	        });
	    });
	
		agoraclient.on('peer-leave', function(evt) {
	        console.log("视频离开 " + evt.uid);
	        showStreamOnPeerLeave(remoteStream.getId())
	    });
	
		agoraclient.on('stream-subscribed', function(evt) {
	        var stream = evt.stream;
	        console.log("渲染视频" + stream.getId());
	        showStreamOnPeerAdded(stream);
	    });
	
		agoraclient.on("stream-removed", function(evt) {
	        var stream = evt.stream;
	        console.log("视频断开" + evt.stream.getId());
	        showStreamOnPeerLeave(stream.getId());
	    });
	}
	//初始化视频流
     function initLocalStream(id, callback) {
    	//选择分辨率
        uid = id;
        //判断流是否存在
        if (localStream) {
        	agoraclient.unpublish(localStream, function(err) {
                console.log("不能发流 ", err);
            });
            //关闭流
            localStream.close();
        }
        //创建本地流
        localStream = AgoraRTC.createStream({
            streamID: uid,
            audio: true,
            video: true,
            screen: false,
            local: false
        });
        //设置本地流的视频分辨率
        localStream.setVideoProfile("480P");
        //设置本地视频的高宽
        localStream.init(function() {
        	var size = {width: 0,height: 0};
            displayStream('video', localStream, size.width, size.height);
			agoraclient.publish(localStream, function(err) {
                	console.log("发出流失败 " + err);
            	});
            agoraclient.on('stream-published');

        }, function(err) {
            console.log("失败", err);
        });
        return localStream;
    } 
    
    //显示视频
    function displayStream(tagId, stream, width, height) {
    	 var $container = $("#"+tagId);
    	 var html = "<div id='"+tagId+ stream.getId() + "' data-stream-id='" + stream.getId()+ "' style='width:"+width+"px;height:"+height+"px'></div>";
    	 var oVideo = $(html);
    	 $container.append(oVideo);
        stream.play(tagId + stream.getId());
        aImg[0].style.display = "none";
       /*  if(firstIn){
        	disableAll();
        }else{
        	
        } */
    }
    
    //当有视频接入调用显示
    function showStreamOnPeerAdded(stream) {
    	 	var size = {  width: 559, height: 327 };
			displayStream('video', stream, size.width, size.height);
    }
    //当视频断开
    function showStreamOnPeerLeave(streamId) {
    	console.info("视频挂断");
    	aImg[0].src="<%=basePath %>static/img/mp4.png";
		aImg[1].src="<%=basePath %>static/img/mp32.png";
		flag = true;
		remoteStream.enableAudio(function(){
    		console.info("禁用音频流成功")
    	}); 
		
    	remoteStream.enableVideo(function(){
    		console.info("视频启用成功");
    	});
    	remoteStream = null;
    	$("#video"+streamId).remove();
    	aImg[0].style.display = "inline";
    }
    
    function stopLocalAndRemoteStreams() {
    	console.info("断开");
    	onlyVideo();
    	remoteStream = null;
    	$("#video"+streamId).remove();
    	aImg[0].style.display = "inline";
    }
</script>
</body> 
</html>