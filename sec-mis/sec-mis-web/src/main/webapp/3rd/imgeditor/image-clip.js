var toolkit = {
	  getEvent : function(e) {
			return e || window.event;
		},
		 getTarget :function(e) {
			return e.srcElement || e.target;
		},
		stopEvent : function(e) {
			e.returnValue && (e.returnValue = false,e.cancelBubble = false);
			e.preventDefault && (e.preventDefault(),e.stopPropagation());
		},
		getClinetRect:function (f) {
			var d = f.getBoundingClientRect(),e = (e = {left:d.left,right:d.right,top:d.top,bottom:d.bottom,height:(d.height ? d.height : (d.bottom - d.top)),width:(d.width ? d.width : (d.right - d.left))});
			return e;
		}	
}
function Clip(node) {
        var indicator = arguments.callee,
            doc = document,
            tk = toolkit;
            that = this,
            handlerSize = 6,
            rules = null;
        node = $('#'+node) || doc.getElementsByTagName('body')[0];

        if (!that instanceof indicator) {
            return new indicator(node)
        }

        rules = {
            'TL':{
                css:'top:0;left:0;cursor:nw-resize',
                size:function(e) {//
                    rules.CL.size(e);
                    rules.TC.size(e);
                }
            },
            'TC':{
                css:'top:0;left:48%;cursor:n-resize',
                size:function(e) {//
                    that.correctY(that.hanlderInfo._down - e.clientY, that.hanlderInfo._mxTH);
                    that.correctTop();
                }
            },
            'TR':{
                css:'right:0;top:0;cursor:ne-resize',
                size:function(e) {//
                    rules.CR.size(e);
                    rules.TC.size(e);
                }
            },
            'CL':{
                css:'top:48%;left:0;cursor:w-resize',
                size:function(e) {//
                    that.correctX(that.hanlderInfo._right - e.clientX, that.hanlderInfo._mxLW);
                    that.correctLeft();

                }
            },
            'CR':{
                css:'top:48%;right:0px;cursor:e-resize',
                size:function(e) {//
                    that.correctX(e.clientX - that.hanlderInfo._left, that.hanlderInfo._mxRW);
                }
            },
            'BL':{
                css:'bottom:0;left:0;cursor:sw-resize',
                size:function(e) {//
                    rules.CL.size(e);
                    rules.BC.size(e);
                }
            },
            'BC':{
                css:'bottom:0;left:48%;cursor:s-resize',
                size:function(e) {//
                    that.correctY(e.clientY - that.hanlderInfo._top, that.hanlderInfo._mxDH);
                }
            },
            'BR':{
                css:'bottom:0;right:0px;cursor:se-resize',
                size:function(e) {//
                    rules.CR.size(e);
                    rules.BC.size(e);
                }
            }
        };
       
        that.area = null;

        that.activity || (indicator.prototype.constructor = indicator),
            indicator.fn = indicator.prototype,
            indicator.fn.constructor = indicator;

        indicator.fn.activity = function(config, fn) {
            var handler = null,handleStyle = [];
            typeof fn === 'function' && (that.fn = fn);
            that.data && (that.Data = null);

            if (null === that.area) {
                that.info = $('<span/>')[0]
                that.area = $('<div/>')[0];
                that.area.style.visibility = 'hidden';
                that.area.id = 'clipArea';

                that.area.appendChild(that.info);
                node.append(that.area);

                handleStyle = ['TL','TC','TR','CL','CR','BL','BC','BR'];

                $.each(handleStyle, function(a) {
                    handler = $('<b/>')[0];
                    handler.id = handleStyle[a];
                    handler.fn = rules[handleStyle[a]];
                    $(handler).bind('mousedown',that.mousemoveCheckThreshold);
                    $(handler).bind('touch',that.mousemoveCheckThreshold);
                    
                    that.area.appendChild(handler);
                    that.setHanldPosition(handler, that.area, handleStyle[a]);
                });
                $(that.area).bind('mousedown', that.mousemoveCheckThreshold);
                that.area.style.visibility = 'visible';
            }
            that.setProp(+config.width, +config.height);

            var parent = that.area.parentNode;
            that.area.style.cssText = 'width:' + config.width + 'px;height:' + config.height + 'px;top:' + parent.scrollTop + 'px;left:' + parent.scrollLeft + 'px';
        };

        indicator.fn.setHanldPosition = function(c, p, d) {
            var W = p.offsetWidth,H = p.offsetHeight,w1 = (W - handlerSize),w2 = Math.floor((W - handlerSize) / 2),h1 = (H - handlerSize),h2 = Math.floor((W - handlerSize) / 2);
            c.style.cssText = rules[d].css;
        };

        indicator.fn.mousemoveCheckThreshold = function(e) {
            e = tk.getEvent(e);
            var target = tk.getTarget(e),pointer = [],eType = e.type;
            while (target && target.nodeType !== 1) {
                target = target.parentNode;
            }

            ({
                mousedown:function(e) {
                    e = tk.getEvent(e);
                    tk.stopEvent(e);

                    doc.currentTarget = target;
                    that.pos = tk.getClinetRect(target);
                    that.origin = [e.clientX - that.pos.left,e.clientY - that.pos.top];
                    target.nodeName.toLowerCase() === 'b' && that.checkHandler(e);
                    $(doc).bind('mouseup', that.mousemoveCheckThreshold);
                    $(doc).bind('mousemove', that.mousemoveCheckThreshold);
                },
                mousemove:function(e) {
                    e = tk.getEvent(e);
                    var target = doc.currentTarget,reffer = {
                        top:e.clientX,
                        right:e.clientY + target.offsetWidth,
                        down:e.clientX + target.offsetHeight,
                        left:e.clientY
                    };

                    target.nodeName.toLowerCase() === 'b' ? that.handlerMove.call(target, e, reffer) : that.areaMove.call(target, e, reffer);
                },
                mouseup:function(e) {
                    e = tk.getEvent(e);
                    var target = tk.getTarget(e);
                    if (target.nodeName.toLowerCase() !== 'b') {
                        try {
                            target.style.cursor = 'move'
                        } catch(e) {
                        }
                    }
                    $(doc).unbind('mouseup', that.mousemoveCheckThreshold);
                    $(doc).unbind('mousemove', that.mousemoveCheckThreshold);
                    that.pos = that.origin = null;
                    delete that.hanlderInfo;
                    tk.stopEvent(e);
                }
            })[e.type](e)

        };

        indicator.fn.checkHandler = function(e) {

            e = tk.getEvent(e);
            var target = tk.getTarget(e),parent = target.parentNode,rect =tk.getClinetRect(parent);
            that.hanlderInfo || (that.hanlderInfo = {});

            that.hanlderInfo.mxT = 0;
            that.hanlderInfo.mxL = 0;
            that.hanlderInfo.mxR = parent.parentNode.clientWidth + parent.parentNode.scrollLeft - 2;
            that.hanlderInfo.mxB = parent.parentNode.clientHeight + parent.parentNode.scrollTop - 2;

            that.hanlderInfo.mxR = Math.max(that.hanlderInfo.mxR, that.hanlderInfo.mxL + 40);
            that.hanlderInfo.mxB = Math.max(that.hanlderInfo.mxB, that.hanlderInfo.mxT + 40);

            that.hanlderInfo.width = parent.clientWidth;
            that.hanlderInfo.height = parent.clientHeight;
            that.hanlderInfo.left = parent.offsetLeft;
            that.hanlderInfo.top = parent.offsetTop;

            that.hanlderInfo._left = rect.left;
            that.hanlderInfo._right = rect.right;
            that.hanlderInfo._top = rect.top;
            that.hanlderInfo._down = rect.bottom;

            that.hanlderInfo._fixLeft = that.hanlderInfo.width + that.hanlderInfo.left;
            that.hanlderInfo._fixTop = that.hanlderInfo.height + that.hanlderInfo.top;

            that.hanlderInfo._mxRW = that.hanlderInfo.mxR - that.hanlderInfo.left;
            that.hanlderInfo._mxDH = that.hanlderInfo.mxB - that.hanlderInfo.top;
            that.hanlderInfo._mxTH = Math.max(that.hanlderInfo._fixTop - that.hanlderInfo.mxT, 0);
            that.hanlderInfo._mxLW = Math.max(that.hanlderInfo._fixLeft - that.hanlderInfo.mxL, 0);

        };
        indicator.fn.correctX = function(w, mxW) {
            w = that.correctWidth(w, mxW);
            that.hanlderInfo.width = w;
        };
        indicator.fn.correctY = function(h, mxH) {
            h = that.correctHeight(h, mxH);
            that.hanlderInfo.height = h;
        };
        indicator.fn.correctWidth = function(w, mxW) {
            w = Math.min(mxW, w);
            w = Math.max(w, 40, 0);
            return w;
        };
        indicator.fn.correctHeight = function(h, mxH) {
            h = Math.min(mxH, h);
            h = Math.max(h, 40, 0);
            return h;
        };
        indicator.fn.correctTop = function() {
            that.hanlderInfo.top = that.hanlderInfo._fixTop - that.hanlderInfo.height;
        };
        indicator.fn.correctLeft = function() {
            that.hanlderInfo.left = that.hanlderInfo._fixLeft - that.hanlderInfo.width;
        };
        indicator.fn.handlerMove = function(e) {
            e = tk.getEvent(e);
            var d = this.id;
            this.fn.size(e);
            that.resize();
            tk.stopEvent(e);
        };
        indicator.fn.resize = function(t) {
            var target = that.area;
            target.style.cssText = 'width:' + that.hanlderInfo.width + 'px;height:' + that.hanlderInfo.height + 'px;top:' + (that.hanlderInfo.top + 1) + 'px;left:' + (that.hanlderInfo.left + 1) + 'px';
            that.fn && that.fn();
            that.setProp(that.hanlderInfo.width, that.hanlderInfo.height);
        },
        indicator.fn.areaMove = function(e) {
            var pointer = [e.clientX,e.clientY],parent = this.parentNode,max = [parent.scrollWidth,parent.scrollHeight],size = tk.getClinetRect(this.parentNode),tL,tT;
            this.style.cursor = 'crosshair';


            tL = Math.max(pointer[0] - size.left + parent.scrollLeft - that.origin[0], 0);
            tL = Math.min(tL, max[0] - this.offsetWidth);
            this.style.left = tL + 'px';

            tT = Math.max(pointer[1] - size.top + parent.scrollTop - that.origin[1], 0);
            tT = Math.min(tT, max[1] - this.offsetHeight);
            this.style.top = tT + 'px';

        };
        indicator.fn.removeArea = function(fn) {
            try {
                that.area.parentNode.removeChild(that.area),that.area = null;
            } catch(e) {
            } finally {
                ('function' === typeof fn) && fn();
            }
        };
        indicator.fn.getData = function() {
            try {
                return [that.area.offsetLeft,that.area.offsetTop,that.area.offsetWidth,that.area.offsetHeight];
            } catch(e) {
            }

        };
        indicator.fn.setProp = function(a, b) {
            that.info.innerHTML = '宽:' + Math.ceil(a) + 'px&nbsp;&nbsp;高:' + Math.ceil(b) + 'px&nbsp;&nbsp;比例:' + that.proportion(a, b);
        };
        indicator.fn.proportion = function (x, y) {
            x = x.toPrecision(1),y = y.toPrecision(1);
            var z = ((x < y) ? x : y);
            while (true) {
                if (x % z == 0 && y % z == 0) {
                    break;
                }
                z--;
            }
            ;
            return (Math.abs(x / z) + ":" + Math.abs(y / z));
        };
    }
