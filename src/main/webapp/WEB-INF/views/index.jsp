<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"> 
	<title>索引页面</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="top: 100">
<form role="form" style="align-self: center;top: 100">
  <div class="form-group" align="center"  >
  	<h2  >页面导航，点击相应的按钮跳转</h2>
    <br>
    <br>
    <input type="button" class="btn btn-primary" id="cpuser" value="跳转客户错误码统计" >
    <br>
    <br>
    <input type="button" class="btn btn-primary" id="channel" value="跳转通道错误码统计" >
    <br>
    <br>
    <input type="button" class="btn btn-primary" id="historyQuery" value="跳转查询未知状态号码" >
  </div>
</form>
<script type="text/javascript">
$(function() {
    $("#cpuser").click(function(){
    	window.location.href='cpuserQuery';	
    });
    $("#channel").click(function(){
    	window.location.href='channelQuery';
    	});
	$("#historyQuery").click(function(){
		window.location.href='historyQuery';
		});
	} );
</script>


</body>
</html>