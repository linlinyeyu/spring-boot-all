<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>servlet async support</title>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.js" type="text/javascript"></script>
</head>
<body>
	<script type="text/javascript">
		deferred();
		function deferred() {
			$.get('defer',function(data){
				console.log(data);
				deferred();
			})
		}
	</script>
</body>
</html>