<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSE Demo</title>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.js" type="text/javascript"></script>
</head>
<body>
	<div id="msgFrompPush"></div>
	<script type="text/javascript">
		if(!! window.EventSource){
			var source = new EventSource('push');
			s='';
			source.addEventListener('message',function(e){
				s+=e.data+"<br/>";
				$("#msgFrompPush").html(s);
			});
			
			source.addEventListener('open',function(e){
				console.log("连接打开.");
			},false);
			
			source.addEventListener('error',function(e){
				if(e.readState == EventSource.CLOSED){
					console.log("连接关闭");
				}else{
					console.log(e.readyState);
				}
			},false);
		}else{
			console.log("你的浏览器不支持SSE");
		}
	</script>
</body>
</html>