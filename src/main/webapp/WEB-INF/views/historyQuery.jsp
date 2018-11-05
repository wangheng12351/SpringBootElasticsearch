<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>状态未知号码</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<form role="form" style="width: 700px;">
		<div class="form-group" align="center">
			<label for="channelid">通道ID <input type="text"
				class="form-control" id="channelId" placeholder="请输入通道ID(必输)"
				required="required"></label> <br> <input type="button"
				class="btn btn-primary" id="submit" value="查询未知状态号码"> <input
				type="button" class="btn btn-primary" id="fan" value="返回首页">
		</div>
		<div class="container" align="left">
			<table class="table">
				<caption>
					<font size="40">短信状态未知号码（十五分钟同步一次）</font>
				</caption>
				<thead>
					<tr>
						<th style="color: blue;">通道ID</th>
						<th style="color: blue;">手机号</th>
						<th style="color: blue;">特服号</th>
						<th style="color: blue;">发送时间</th>
						<th style="color: blue;">发送内容</th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
		</div>
	</form>
	<script type="text/javascript">
$(function() {
	 $("#fan").click(function(){
	    	window.location.href='index';	
	    });
    $("#submit").click(function(){
    	var channelId=$("#channelId").val();
    	$.ajax({url:"/SpringBootElasticsearch/listHist?channelId="+channelId,
    			success:function(result){
    				var tbody=$("#tbody").html("");
    	    		if(result!=null){
    	    			var tem=$.parseJSON(result);
    	    			$.each(tem, function(n, item) {
    	    				 var tr = $("<tr class=\"info\"></tr>");
   	    				         //赋值
   	    	    	       	        tr.html('<td>'+item.channelID+'</td><td>'+item.number+'</td><td>'+item.serviceCode+'</td><td style="width: 150px;">'+formatTime(item.createTime)+'</td><td>'+item.smsContent+'</td>');
   	    	    	       	        //在房间tbody中
   	    	    	       	        $("#tbody").append(tr);
    	    				    }); 
    	    		}
		}});
    });
}); 
function formatTime(timestamp){
	var time = new Date(timestamp);
	var y = time.getFullYear();//年
	var m = time.getMonth() + 1;//月
	var d = time.getDate();//日
	var h = time.getHours();//时
	var mm = time.getMinutes();//分
	var s = time.getSeconds();//秒
	return y+"-"+m+"-"+d+" "+h+":"+mm+":"+s;
}
</script>


</body>
</html>