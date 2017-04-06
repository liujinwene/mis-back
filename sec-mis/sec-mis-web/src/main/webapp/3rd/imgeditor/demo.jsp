<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>image editor demo</title>
<!--     <link href="css/cssreset.css" rel="stylesheet" type="text/css"/>
    <link href="css/main.css" rel="stylesheet" type="text/css"/> -->
<link href="imgeditor.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
	.canvasContainer{
		text-align: center;
		width: auto;
		display: table;
		margin-left: auto;
		margin-right: auto;
	}
</style>
<script src="../jquery/jquery-2.0.3.min.js"></script>
<script src="image-clip-bak.js"></script>
<script src="imgeditor.js"></script>

<body>
<span>
<button id="start_clip" value="启动裁剪">启动裁剪</button>
</span>
<span>
<button id="start_scale">启动缩放</button>
宽：<input id="scale_w" value="90"/>
高：<input id="scale_h" value="90"/>
</span>
<span>
<button id="start_rotate">旋转</button>
转换角度：<input id="degress" value="90"/>
</span>
<br>
<span>
	<img alt="" src="images/logo01.png">
	<button id="water_start">打水印</button>
	<button id="water_cancel">取消水印</button>
	<button id="water_ok">确认水印</button>
</span>
<br>
<span>
  <button id="scrawl">开始涂鸦</button>
</span>
<br>
<br>
<button id="revoke">可以重来吗？</button>
<button id="save">保存</button>

<div id="canvasContainer"></div>
<script type="text/javascript">
var imageEditor = new ImageEditor({cantainerId:'canvasContainer'});
$(function(){
	imageEditor.init();
	imageEditor.imageInstall('http://localhost:8080/xyb-mis-web/upload/getImage.do?src=http://192.168.90.253:8080/hhcfdc//upload/borrowProjectFile/1415617929188.jpg');
	$('#start_clip').click(function(){
		var clip = imageEditor._clip;
		clip.init();
	});
	$('#start_scale').click(function(){
		//imageEditor.revoke();
		var scale = imageEditor.scale;
		scale.init();
		scale.setScale($('#scale_w').val(),$('#scale_h').val())
	});
	$('#start_rotate').click(function(){
		//imageEditor.revoke();
		var rotate = imageEditor.rotate;
		rotate.init();
		rotate.setRotate($('#degress').val())
	});
	var water =  imageEditor.waterMark;
	$('#water_start').click(function(){
		water.init();
		water.previewMark({
			'src':'images/water_01.jpg'
			,'type':2
			});
	});

	$('#water_cancel').click(function(){
		water.remove();
	});
	$('#water_ok').click(function(){
		water.save();
	});
	var scrawl = false;
	$('#scrawl').click(function(){
		scrawl = !scrawl;
		imageEditor.scrawl(scrawl);
		$(this).text(scrawl?'结束涂鸦':'开始涂鸦');
	});
	$('#revoke').click(function(){
		imageEditor.revoke();
	});
	
	$('#save').click(function(){
		//$('#canvasContainer').append(imageEditor.getImage());
		var imagedata = imageEditor.getImageData();
		//console.log(imagedata);
		imagedata =  encodeURIComponent(imagedata);  
		imagedata = imagedata.substr(30);
		//console.log(imagedata);
		$.ajax({
			type: 'POST',
			dataType: "json",
			url: '<%=context%>/upload/base64-img.do',
			data: {'base64Data':imagedata,'fileType':'projectFile'},
			success: function(data){
				//console.log(data);
				alert('success');
			},
			error:function(e){
				//console.log(e);
				alert('error');
			}
		});
	});
	
})
</script>
</body>
</html>