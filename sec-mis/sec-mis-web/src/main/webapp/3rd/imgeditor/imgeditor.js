function ImageEditor(opts){
	opts = $.extend(opts,{
		areaSize : [120,120]
	});
	
	var cantainerId = opts.cantainerId;
	var $cantainer = $('#'+cantainerId);
	var doc = document;
	this.EDIT_CLIP = 'CLIP';
	this.EDIT_SCALE = 'SCALE';
	this.EDIT_ROTATE = 'ROTATE';
	this.imageType = 'image/png';
	this.sourceImage = null;
	this.img = null;
	this.canvas = null;
	this.maxSize = null;
	this.editing = null;
	this.clip = new Clip(cantainerId);
	var yyp = this;
	
    this.init = function(){
    	var canvas = $('<canvas/>');
    	$cantainer.append(canvas);
    	yyp.canvas = canvas;
    }
    
    this.imageInstall=function(imgSrc,fn){
    	var canvas = getCanvas()[0],cxt = getCanvasCxt();
    	var img = new Image();
    	yyp.img = img;
    	yyp.sourceImage = imgSrc;
    	var layerIndex = layer.load('请稍候....');
        $(yyp.img).load(function() {
        	 var canvasSize = [img.width, img.height];
             yyp.maxSize = canvasSize;
             cxt.strokeStyle = cxt.createPattern(img, 'no-repeat');
             //canvas.css({width:canvasSize[0],height:canvasSize[1]});
             canvas.width = canvasSize[0],canvas.height = canvasSize[1];
             cxt.drawImage(img, 0, 0, canvasSize[0], canvasSize[1]);
             layer.close(layerIndex);
             if(jQuery.isFunction(fn)){
            	 fn();
             }
             //$(this).prop('crossOrigin','anonymous');
        }).error(function() {
        	layer.close(layerIndex);
        	alert('图片加载失败........');
        }).prop('src', imgSrc);//;
    }
    this._clip = {
    	init:function(){
    		var that = yyp._clip;
    		if(yyp.editing != yyp.EDIT_CLIP){
    			yyp.editing = yyp.EDIT_CLIP
    		}
    		try {
                that.removeArea();
            } catch (e) {
            }
           // var canvas = getCanvas()[0],cxt = getCanvasCxt();
            //yyp.img.src = canvas.toDataURL(yyp.imageType);
            that.areaCut(opts.areaSize[0], opts.areaSize[1]);
    	},
    	areaCut: function (x, y) { //
             var that = yyp._clip,
                 area = yyp.clip.area;
             yyp.clip.activity({
                 width: x,
                 height: y
             }, function () {
                //console.log('refresh...area...size');
             });
             $(yyp.clip.area).bind('dblclick', function () {
                 that.getImage();
                 that.removeArea();
             });
            // $(document).bind('keypress', this.keybordCut, false);
         },
      /*   keybordCut: function (e) {
        	 var that = yyp._clip;
           //  e = tk.getEvent(e);
             var keyCode = e.keyCode || e.charCode;
                 //target = tk.getTarget(e);
             if (keyCode === 13) {
                 that.finishClip(e.target);
             }
             return false;
         },*/
         getImage: function () { 
        	 var that = yyp._clip;
             try {
                 that.uploadImg();
                 yyp.clip.area.style.left = '0px';
                 yyp.clip.area.style.top = '0px';
             } catch (e) {
             }
         },
         uploadImg: function () { //
        	 saveImage();
         },
         removeArea: function (fn) {
             yyp.clip.area && yyp.clip.removeArea(fn);
         },
/*         setClientValue: function (node, flag) {
        	 var that = yyp._clip;
        	 var canvas = yyp.canvas[0];
             var temp = this.value.trim(),
                 x = parseInt(node.value.trim(), 10),
                 reg = /^(?!0)\d+$/g;

             (0 === flag && reg.test(temp)) && (
                 isNaN(x) ? opts.areaSize[1] : x,temp = temp > canvas.width ? (canvas.width) : temp,this.value = temp,that.areaCut(temp, x));
             (1 === flag && reg.test(temp)) && (
                 isNaN(x) ? opts.areaSize[0] : x,temp = temp > canvas.height ? (canvas.height) : temp,this.value = temp,that.areaCut(x, temp));
         },*/
       /*  finishClip: function (O) {
        	 var that = yyp._clip;
             O.disabled = 'disabled';
             that.getImage();
             that.removeArea(function () {
                 O.removeAttribute('disabled');
             });
             return false;
         },*/
         destroy: function () {
            // $(document).unbind('keypress', this.keybordCut);
            // try {
               //console.log('destroy');
           //  } catch (e) {
           //  }
         }
    }
    this.scale = {//
    	init:function(){
    		 try {
    			 var canvas = getCanvas()[0],cxt = getCanvasCxt();
	    		 yyp._clip.removeArea();
	    		 if(yyp.editing != yyp.EDIT_SCALE){
	    			yyp.editing = yyp.EDIT_SCALE;
	    			that.sx = 1;
	    			that.sy = 1;
	    		 }else{
	    			 //yyp.img.src = canvas.toDataURL(yyp.imageType);
	    		 }
	    			 
             } catch (e) {
            	 //console.log(e);
             }
    	},
    	sx:1,
    	sy:1,
    	setScale:function(wscale,hscale){
    		var that = yyp.scale;
    		var canvas = getCanvas()[0],cxt = getCanvasCxt();
    		var  img = yyp.img;
    		var x = that.sx*wscale/100;
    		var y = that.sy*hscale/100;
    		that.sx = x;
    		that.sy = y
            w = Math.max(img.width * x, 1),
            h = Math.max(img.height * y, 1);
    		
    		w = parseInt(w, 10),h = parseInt(h, 10);
	        canvas.width = w;
	        canvas.height = h;
	        
	        var rotate = yyp.rotate;
			 var 
			 	 clientWidth = canvas.width,
			 	 clientHeight = canvas.height;
			 cxt.save();
			 cxt.clearRect(0, 0, clientWidth, clientHeight);
			 /////////////
			 cxt.translate(clientWidth/2,clientHeight/2);
		     cxt.rotate(rotate.angle);
		     cxt.scale( that.sx, that.sy);//缩放
		     cxt.drawImage(img, -img.width/2,-img.height/2);
		     cxt.restore();
    	},
    	reset:function(){
    		var that = yyp.scale;
    		that.sx = 1;
    		that.sy = 1;
    	}
    }
    /**
     * 旋转
     */
    this.rotate = {
    	init:function(){
    		var canvas = getCanvas()[0],cxt = getCanvasCxt();
    		yyp._clip.removeArea();
    		 var that = yyp.rotate;
    		if(yyp.editing != yyp.EDIT_ROTATE){
    			yyp.editing = yyp.EDIT_ROTATE
    			that.angle = 0;
    			//saveImage();
    			//yyp.img.src = canvas.toDataURL(yyp.imageType);
    			//canvas.width = yyp.img.width;
    			//canvas.height = yyp.img.height;
    		}
    	},
    	/**
    	 * 0<degrees<360
    	 */
    	angle:0,
    	setRotate:function(degrees){
    		 var that = yyp.rotate;
    		 /////////////
             that.angle += degrees*Math.PI/180;
             var rotate = yyp.rotate;
    		 var scale = yyp.scale;
    		 var canvas = getCanvas()[0],cxt = getCanvasCxt();
    		 var img = yyp.img,
    		 	 clientWidth = canvas.width,
    		 	 clientHeight = canvas.height;
    		 cxt.save();
    		 cxt.clearRect(0, 0, clientWidth, clientHeight);
    		 var imgW = img.width*scale.sx;
    		 var imgH = img.height*scale.sy;
    		 var max = Math.sqrt(imgW*imgW + imgH*imgH);
    		 var canvasSize = [max, max];
             yyp.maxSize = canvasSize;
    		 canvas.width = max,canvas.height = max;
    		 /////////////
    	     //that.angle += degrees*Math.PI/180;
    	     cxt.translate(max/2,max/2);
    	     cxt.rotate(rotate.angle);
    	     cxt.scale( scale.sx, scale.sy);//缩放
    	     cxt.drawImage(img, -img.width/2,-img.height/2);
    	     cxt.restore();
    	},
    	reset:function(){
    		var that = yyp.rotate;
    		that.angle = 0;
    	}
    }
    function show(){
		 var rotate = yyp.rotate;
		 var scale = yyp.scale;
		 var canvas = getCanvas()[0],cxt = getCanvasCxt();
		 var img = yyp.img,
		 	 clientWidth = canvas.width,
		 	 clientHeight = canvas.height;
		 cxt.save();
		 cxt.clearRect(0, 0, clientWidth, clientHeight);
		 var imgW = img.width;
		 var imgH = img.height;
		 var max = Math.sqrt(imgW*imgW + imgH*imgH);
		 var canvasSize = [max, max];
         yyp.maxSize = canvasSize;
		 canvas.width = max,canvas.height = max;
		 /////////////
	     //that.angle += degrees*Math.PI/180;
	     cxt.translate(max/2,max/2);
	     cxt.rotate(rotate.angle);
	     cxt.scale( scale.sx, scale.sy);//缩放
	     cxt.drawImage(img, -img.width/2,-img.height/2);
	     cxt.restore();
    }
    /***
     * 水印
     */
    this.waterMark = {
    	init:function(){
    		var that = yyp.waterMark;
    		that.preview = $('<canvas class="preview"/>');
    		that.preview2d = that.preview[0].getContext('2d');
    	}
		    /***
		     * src:图片路径
		     * alpha:透明度
		     * type:水印拉伸
		     * 1：不拉伸
		     * 2：xy方向拉伸
		     * 3：x方向
		     * 4：y方向
		     */
    	,previewMark:function(opts){
    		opts =$.extend({
    			alpha :0.5
    		},opts)
    		var that = this;
    		var canvas = getCanvas(),cxt = getCanvasCxt();
    		var markImg = new Image();
    		$(markImg).load(function() {
    			var offset = canvas.offset();
           	 	var x = 0;
           	 	var y= 0;
           	 	var width = this.width;
           	 	var height = this.height;
           	 	var maxWidth = canvas.width();
           	 	var maxHeight = canvas.height();
           	 	var type = opts.type;
/*           	 	if(type==2){
           	 		if(width<maxWidth){
           	 			width = maxWidth;
           	 		}
           	 		if(height<maxHeight){
           	 			height = maxHeight;
           	 		}
           	 	}else if(type==3 && width<maxWidth){
	       	 		width = maxWidth;
           	 	}else if(type==4 && height<maxHeight){
       	 			height = maxHeight;
           	 	}*/
           	 	
           	 	//sif(width<=)
           	 	$cantainer.append(that.preview);
                that.preview.css({
                	'position':'absolute',
                	'left':x+'px',
                	'top':y+'px'
                });
                that.preview[0].width = maxWidth;
                that.preview[0].height = maxHeight;
               // console.log('pts.alpha:'+opts.alpha);
                that.preview2d.globalAlpha=opts.alpha;
                var wCnt=Math.round(maxWidth/width+0.5),hCnt=Math.round(maxHeight/height+0.5);
                var startX =0,startY=0;
                for(var xIndex=0;xIndex<wCnt;xIndex++){
                	for(var yIndex=0;yIndex<hCnt;yIndex++){
                		startX = xIndex*width;
                		startY = yIndex*height;
                		that.preview2d.drawImage(markImg,startX,startY,width,height);
                	}
                }
                
                //var img = that.preview2d.getImage
           }).error(function() {
           	alert('图片加载失败........');
           }).prop('src', opts.src);
    	   that.preview.click(function(){
        	   //console.log('dddddddddddd');
           });
    	}
    	,save:function(){
    		var that = this;
    		if(!that.preview){
    			return;
    		}
    		var canvas = getCanvas()[0],cxt = getCanvasCxt();
    		var image = new Image();  
    		$(image).load(function(){
    			//var offset = that.preview.offset();
        		var left = 0;//offset.left;
        		var top = 0;//offset.top;
        		cxt.drawImage(image,left,top);
        		yyp.img.src = canvas.toDataURL(yyp.imageType);
        		that.remove();
    		})
    		image.src = this.preview[0].toDataURL(yyp.imageType);  
    	}
    	,remove:function(){
    		if(this.preview){
    			this.preview.remove();
    			this.preview2d = null;
    			this.preview = null;
    		}
    	}
    }//end water mark
    //涂鸦
    this.scrawl={
		init:function(){
			var canvas = getCanvas(),cxt = getCanvasCxt();
    		var that = yyp.scrawl;
    		that.cancelList = new Array();
    		//用于记录是否在涂鸦，方便撤销
    		that.isDarw=false;
    		var x=0,y=0;
    		var canvasWidth=canvas[0].width,canvasHeight=canvas[0].height;
    		var scrawlBuf = $('<canvas class="scrawl-buf"/>');
    		that.scrawlBuf = scrawlBuf;
    		var g2d = scrawlBuf[0].getContext('2d');
    		that.g2d = g2d;
    		$cantainer.append(scrawlBuf);
    		scrawlBuf.css({
            	'position':'absolute',
            	'left':x+'px',
            	'top':y+'px'
            });
            that.scrawlBuf[0].width = canvasWidth;
            that.scrawlBuf[0].height = canvasHeight;
            
            
            $(function(){
        		var startX;
        		var startY;
        		var endX;
        		var endY;
        		var flag = 0;
        			
            	var mousedown=function(e){
            		flag = 1;  
            		e=e.originalEvent;
            	    startX = e.layerX;
            	    startY = e.layerY;
            	}
            	var mouseup=function(e){
            		flag = 0;  
            		e=e.originalEvent;
            		var image = new Image();
            		image.src = scrawlBuf[0].toDataURL();
        			image.onload = function(){
        				that.g2d.drawImage(image , 0 ,0 , image.width, image.height , 0 ,0 , canvasWidth , canvasHeight);
        				//clearContext();
        				//saveImageToAry();
        				that.isDarw = true;
        				that.cancelList.push(image.src);	
        			}
            	}
            	var mousemove=function(e){
            		if(flag!=1)return;
        		    e=e.originalEvent;
    			    endX = e.layerX;
    	    	    endY = e.layerY;
    	    	    drawRect();
            	}
            	var drawRect = function(){
            		g2d.save(); 
            		// cxt.restore();  
        		    var width = scrawlBuf.width();
    			    var heigth = scrawlBuf.height();
    	    	    g2d.translate(width/2, heigth/2);  
    			    g2d.translate(-width/2, -heigth/2);  
    			    g2d.beginPath();  
    			    g2d.lineWidth = 10 ;  
    			    g2d.strokeStyle="#99cc33";
    			    g2d.fillStyle='#D3D3D3';
    			    g2d.fillRect(startX, startY, (endX-startX), (endY-startY)); 
    			    g2d.stroke();  
    			    g2d.restore();  
            	}
            	scrawlBuf.bind('mousedown',mousedown);
            	scrawlBuf.bind('mouseup',mouseup);
            	scrawlBuf.bind('mousemove',mousemove);
            	$(window).keydown(function(e){
    			     if( e.which === 90 && e.ctrlKey ){
    			         that.cancel();
    			      }          
    			}); 
            });
            
    	}	
		//撤销list
		,cancel:function(){
			var that = yyp.scrawl;
    		if(!that.scrawlBuf){
    			return;
    		}
    		if(that.isDarw){
    			that.isDarw = false;
    			that.cancelList.pop();
    		}
    		var canvasWidth=that.scrawlBuf[0].width,canvasHeight=that.scrawlBuf[0].height;
    		var g2d = that.scrawlBuf[0].getContext('2d');
    		if( that.cancelList.length==0){
    			g2d.clearRect(0,0,canvasWidth,canvasHeight);
    			return;
    		}
    		
			g2d.clearRect(0,0,canvasWidth,canvasHeight);
			var  image = new Image();
			var url = that.cancelList.pop();
			image.src = url;
			image.onload = function(){
				g2d.drawImage(image , 0 ,0 , image.width , image.height , 0 ,0 , canvasWidth , canvasHeight);
			}
		}
    	,save:function(){
    		var that = yyp.scrawl;
    		if(!that.scrawlBuf){
    			return;
    		}
    		var canvas = getCanvas(),cxt = getCanvasCxt();
    		that.scrawlBuf.unbind('mousedown');
    		that.scrawlBuf.unbind('mouseup');
    		that.scrawlBuf.unbind('mousemove');
			
			var image = new Image();  
    		$(image).load(function(){
        		var left = 0;//offset.left;
        		var top = 0;//offset.top;
        		cxt.drawImage(image,left,top);
        		yyp.img.src = canvas[0].toDataURL(yyp.imageType);
        		that.scrawlBuf.remove();
        		that.g2d = null;
    		});
    		image.src = that.scrawlBuf[0].toDataURL(yyp.imageType);  
    	}
    }//end scrawl
    function saveImage(){
    	var canvas = getCanvas()[0],cxt = getCanvasCxt(),
	        newImg, temp = yyp.clip.getData(),
	        x, y, w, h;
	    yyp.step || (yyp.step = 0);
	    if($.isArray(temp)){
	   	 x = Math.max(temp[0], 0);
	   	 y = Math.max(temp[1], 0);
	   	 w = Math.min(temp[2] - 2, yyp.maxSize[0] - x);
	   	 h = Math.min(temp[3] - 2, yyp.maxSize[1] - y); 
	    }
	    if (w > 0 && h > 0) {
	        try {
	            newImg = cxt.getImageData(x, y, w, h);
	            canvas.width = w;
	            canvas.height = h;
	        } catch (e) {
	       	 	//console.log(e);
	        }
	    }
	    try {
	        cxt.putImageData(newImg, 0, 0);
	        yyp.img.src = canvas.toDataURL(yyp.imageType);
	    } catch (e) {
	    	//console.log(e);
	    }
    }
    function getCanvasCxt(){
    	var canvas = getCanvas()[0],cxt;
    	canvas.getContext && (cxt = canvas.getContext('2d'));
    	return cxt;
    }
    
    function getCanvas() {
    	return yyp.canvas;
    }
}
/**
 * 撤回，恢复初始状态
 */
ImageEditor.prototype.revoke=function(){
	this.clip.area && this.removeArea();
	this.scale.reset();
	this.rotate.reset();
	this.imageInstall(this.sourceImage);
}
/**
 * 保存当前画布状态
 */
ImageEditor.prototype.save=function(){
	//try{IM._clip.removeArea();}catch(e){};
	this.clip.area && this.removeArea();
}

ImageEditor.prototype.getImage=function(imageType){
	var image = new Image();  
	image.src = this.canvas[0].toDataURL(imageType);  
	return image;  
}
ImageEditor.prototype.getImageData=function(imageType,quality){
	if(!quality)quality=1;
	//var canvas = this.canvas[0];
	//var canvasWidth = canvas.width,canvasHeight=canvas.height;
	//var imgWidth=canvas.width,imgHeight=canvas.height;
	//console.log('canvasWidth:'+canvasWidth+',canvasHeight='+canvasHeight+',imgWidth='+imgWidth+',imgHeight='+imgHeight);
	return this.canvas[0].toDataURL(imageType,quality);//.replace(this.imageType, "image/octet-stream;Content-Disposition: attachment;filename=foobar.png");  
}

