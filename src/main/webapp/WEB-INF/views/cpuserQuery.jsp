<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"> 
	<title>客户错误码统计页面</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<script src="/SpringBootElasticsearch/laydate/laydate.js"></script>
<script type="text/javascript">
laydate.render({
	  elem: '#endsubmittime' //指定元素
		  ,type: 'datetime'
	});
laydate.render({
	  elem: '#beginsubmittime' //指定元素
		  ,type: 'datetime'
	});
</script>
<form role="form" style="width:350px;">
  <div class="form-group" align="center"  >
    <label for="servicecode">特服号
    <input type="text" class="form-control" id="servicecode" placeholder="请输入特服号" required="required" ></label>
    <label for="beginsubmittime">开始时间
    <input type="text" class="form-control" id="beginsubmittime" placeholder="请输入开始时间" required="required"></label>
    <label for="endsubmittime">结束时间
    <input type="text" class="form-control" id="endsubmittime" placeholder="请输入结束时间" required="required"></label>
    <br>
    <input type="button" class="btn btn-primary" id="submit" value="状态报告值统计" >
    <input type="button" class="btn btn-primary" id="submit1" value="成功失败未知率统计" >
    <br>
    <br>
    <input type="button" class="btn btn-primary" id="fan" value="返回首页">
  </div>
  <div class="container" align="left">
	<table class="table">
  <caption><font size="40">客户短信发送统计</font></caption>
  <thead>
    <tr>
      <th style="color: blue;">统计类型</th>
      <th style="color: blue;">统计个数</th>
    </tr>
  </thead>
  <tbody id="tbody" >
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
    	var servicecode=$("#servicecode").val();
    	var beginsubmittime=$("#beginsubmittime").val();
    	var endsubmittime=$("#endsubmittime").val();
    	$.ajax({url:"/SpringBootElasticsearch/cpuserStatusTerms",
    			type: 'POST',
    			data:"servicecode="+servicecode+"&beginsubmittime="+beginsubmittime+"&endsubmittime="+endsubmittime,
    			success:function(result){
	    		var tbody=$("#tbody").html("");
	    		console.log(result);
	    		if(result!=null){
	    			for(var item in result){
	       			 //遍历pp对象中的属性，只显示出 非函数的 属性，注意不能 遍历 p这个类
	       			 if(typeof(result[item])== "function")
	       			  continue;
	       			var tr = $("<tr class=\"info\"></tr>");
	       	        //赋值
	       	      
	       	        tr.html('<td>'+result[item].statusName+'</td><td>'+result[item].count+'</td>');
	       	        //在房间tbody中
	       	        $("#tbody").append(tr);
	       	       }
	    		}
    		
		}});
    });
    $("#submit1").click(function(){
    	var servicecode=$("#servicecode").val();
    	var beginsubmittime=$("#beginsubmittime").val();
    	var endsubmittime=$("#endsubmittime").val();
    	$.ajax({url:"/SpringBootElasticsearch/cpUserStatisNew",
    			type: 'POST',
    			data:"servicecode="+servicecode+"&beginsubmittime="+beginsubmittime+"&endsubmittime="+endsubmittime,
    			success:function(result){
	    		var tbody=$("#tbody").html("");
	    		if(result!=null){
	    			for(var item in result){
	       			 //遍历pp对象中的属性，只显示出 非函数的 属性，注意不能 遍历 p这个类
	       			 if(typeof(result[item])== "function")
	       			  continue;
	       			var tr = $("<tr class=\"info\"></tr>");
	       	        //赋值
	       	        tr.html('<td>'+item+'</td><td>'+result[item]+'</td>');
	       	        //在房间tbody中
	       	        $("#tbody").append(tr);
	       	       }
	    		}
    		
		}});
    });
}); 
</script>


</body>
</html>