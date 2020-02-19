    /**
	 * 一些基本配置，如服务器地址、座席信息、使用的外线号码
	 */
	var websocketUrl = ''//呼叫中心服务器websocket请求地址
	var agentno = '';//座席号码
	var password = '';//座席密码
	var exten = '';//座席分机
	var pstnnumber = '';//外线号码
	var popupUrl = '';//来电弹屏地址
	
    function Map(){
		this.container = new Object();
	}
	
	Map.prototype.put = function(key, value){
		this.container[key] = value;
	}

	Map.prototype.get = function(key){
		return this.container[key];
	}
	
    var websocket = null;//websocket对象
    var hasResponse = false;//是否响应
    var signin = false;//是否登录
	var method = "";//请求类型
	var agentstatus = "0";//座席状态
	var callee = "";//被叫号码
	var timeoutSec = 30;//心跳检测时间（秒）
	var heartbeatId=-1;//心跳事件ID
	var changeStatusId=-1;//统计座席状态时间事件ID
	var loseHeartbeat=0;//心跳丢包次数
	var agentGroupStr='[{"id":1,"parent":0,"name":"座席组","open":false,"isParent":true,"busid":"-1"}';//座席组信息
	var queueStr=',{"id":2,"parent":0,"name":"队列","open":false,"isParent":true,"busid":"-1"}';//队列信息
	var monitorAgents="";//监听座席列表
	var selectAgent="";//当前选中座席
	var roleAgentGroupNum="";//当前座席可以管理的座席组
	var roleQueueNum="";//当前座席可以管理的队列
	var statusMap = new Map();//座席状态MAP
	statusMap.put("-1", "离线");
	statusMap.put("0","空闲");
	statusMap.put("1","振铃");
	statusMap.put("2","通话");
	statusMap.put("3","保持");
	statusMap.put("4","后处理");
	statusMap.put("5","被占用");
	statusMap.put("6","忙碌");
	statusMap.put("7","离开");
	statusMap.put("8","仅呼出");

	//话务监控参数设置
	var agentGroupStr='[{"id":1,"parent":0,"name":"座席组","open":false,"isParent":true,"busid":"-1"}';//座席组信息
	var queueStr=',{"id":2,"parent":0,"name":"队列","open":false,"isParent":true,"busid":"-1"}';//队列信息
	var monitorAgents="";//监听座席列表
	var selectAgent="";//当前选中座席
	var roleAgentGroupNum="";//当前座席可以管理的座席组
	var roleQueueNum="";//当前座席可以管理的队列
  	
  	//设置工具栏启用状态
  	function setToolbarEnabled(allStatusFlag,calloutInternalFlag,calloutExternalFlag,hangUpFlag,holdFlag,unHoldFlag,transferFlag,threewayFlag,evaluateFlag,monitorFlag){
  		if(allStatusFlag){
  			$('#allStatus').show();
  		}else{
  			$('#allStatus').hide();
  		}
  		if(calloutInternalFlag){
  			$('#calloutInternal').show();
  		}else{
  			$('#calloutInternal').hide();
  		}
  		if(calloutExternalFlag){
  			$('#calloutExternal').show();
  		}else{
  			$('#calloutExternal').hide();
  		}
  		if(hangUpFlag){
  			$('#hangUp').show();
  		}else{
  			$('#hangUp').hide();
  		}
  		if(holdFlag){
  			$('#hold').show();
  		}else{
  			$('#hold').hide();
  		}
  		if(unHoldFlag){
  			$('#unHold').show();
  		}else{
  			$('#unHold').hide();
  		}
  		if(transferFlag){
  			$('#transfer').show();
  		}else{
  			$('#transfer').hide();
  		}
  		if(threewayFlag){
  			$('#threeway').show();
  		}else{
  			$('#threeway').hide();
  		}
  		if(evaluateFlag){
  			$('#evaluate').show();
  		}else{
  			$('#evaluate').hide();
  		}
  		if(monitorFlag){
  			$('#monitor').show();
  		}else{
  			$('#monitor').hide();
  		}
  	}
  	
  	//设置监控栏座席状态
  	function setMonitorbarStatus(agentNum,state){
  		if($("#tdstatus_"+agentNum).length>0){
  			$("#tdstatus_"+agentNum).html(statusMap.get(state));
  			if(state=="-1"){//离线
  				$("#tdacttime_"+agentNum).val(-1);
  				if(selectAgent==agentNum && agentNum!=agentno){
	  				$("#eavesdrop").attr('disabled','');
	  				$("#whisper").attr('disabled','');
	  				$("#bargein").attr('disabled','');
	  				$("#forceHangup").attr('disabled','');
	  				$("#forceBusy").attr('disabled','');
	  				$("#forceIdle").attr('disabled','');
	  				$("#forceOffline").attr('disabled','');
  				}
  			}else if(state=="0"){//空闲
  				$("#tdacttime_"+agentNum).val(0);
  				if(selectAgent==agentNum && agentNum!=agentno){
	  				$("#eavesdrop").attr('disabled','');
	  				$("#whisper").attr('disabled','');
	  				$("#bargein").attr('disabled','');
	  				$("#forceHangup").attr('disabled','');
	  				$("#forceBusy").removeAttr('disabled');
	  				$("#forceIdle").attr('disabled','');
	  				$("#forceOffline").removeAttr('disabled');
  				}
  			}else if(state=="2"){//通话中
  				$("#tdacttime_"+agentNum).val(0);
  				if(selectAgent==agentNum && agentNum!=agentno){
	  				$("#eavesdrop").removeAttr('disabled');
	  				$("#whisper").removeAttr('disabled');
	  				$("#bargein").removeAttr('disabled');
	  				$("#forceHangup").removeAttr('disabled');
	  				$("#forceBusy").attr('disabled','');
	  				$("#forceIdle").attr('disabled','');
	  				$("#forceOffline").attr('disabled','');
  				}
  			}else if(state=="6"){//忙碌
  				$("#tdacttime_"+agentNum).val(0);
  				if(selectAgent==agentNum && agentNum!=agentno){
	  				$("#eavesdrop").attr('disabled','');
	  				$("#whisper").attr('disabled','');
	  				$("#bargein").attr('disabled','');
	  				$("#forceHangup").attr('disabled','');
	  				$("#forceBusy").attr('disabled','');
	  				$("#forceIdle").removeAttr('disabled');
	  				$("#forceOffline").removeAttr('disabled');
  				}
  			}else{//其它
  				$("#tdacttime_"+agentNum).val(0);
  				if(selectAgent==agentNum && agentNum!=agentno){
	  				$("#eavesdrop").attr('disabled','');
	  				$("#whisper").attr('disabled','');
	  				$("#bargein").attr('disabled','');
	  				$("#forceHangup").attr('disabled','');
	  				$("#forceBusy").attr('disabled','');
	  				$("#forceIdle").attr('disabled','');
	  				$("#forceOffline").attr('disabled','');
  				}
  			}
  		}
  	}
  	
  	//设置监控栏按键启用状态
  	function setMonitorbarEnabled(agentNum){
  		if($("#tdstatus_"+agentNum).length>0){
  			var state=$("#tdstatus_"+agentNum).html();
  			if(agentNum==agentno){//本身座席不能操作
  				$("#eavesdrop").attr('disabled','');
	  			$("#whisper").attr('disabled','');
	  			$("#bargein").attr('disabled','');
	  			$("#forceHangup").attr('disabled','');
	  			$("#forceBusy").attr('disabled','');
	  			$("#forceIdle").attr('disabled','');
	  			$("#forceOffline").attr('disabled','');
	  		}else if(state==statusMap.get("-1")){//离线
  				$("#eavesdrop").attr('disabled','');
	  			$("#whisper").attr('disabled','');
	  			$("#bargein").attr('disabled','');
	  			$("#forceHangup").attr('disabled','');
	  			$("#forceBusy").attr('disabled','');
	  			$("#forceIdle").attr('disabled','');
	  			$("#forceOffline").attr('disabled','');
  			}else if(state==statusMap.get("0")){//空闲
	  			$("#eavesdrop").attr('disabled','');
	  			$("#whisper").attr('disabled','');
	  			$("#bargein").attr('disabled','');
	  			$("#forceHangup").attr('disabled','');
	  			$("#forceBusy").removeAttr('disabled');
	  			$("#forceIdle").attr('disabled','');
	  			$("#forceOffline").removeAttr('disabled');
  			}else if(state==statusMap.get("2")){//通话中
	  			$("#eavesdrop").removeAttr('disabled');
	  			$("#whisper").removeAttr('disabled');
	  			$("#bargein").removeAttr('disabled');
	  			$("#forceHangup").removeAttr('disabled');
	  			$("#forceBusy").attr('disabled','');
	  			$("#forceIdle").attr('disabled','');
	  			$("#forceOffline").attr('disabled','');
  			}else if(state==statusMap.get("6")){//忙碌
	  			$("#eavesdrop").attr('disabled','');
	  			$("#whisper").attr('disabled','');
	  			$("#bargein").attr('disabled','');
	  			$("#forceHangup").attr('disabled','');
	  			$("#forceBusy").attr('disabled','');
	  			$("#forceIdle").removeAttr('disabled');
	  			$("#forceOffline").removeAttr('disabled');
  			}else{//其它
	  			$("#eavesdrop").attr('disabled','');
	  			$("#whisper").attr('disabled','');
	  			$("#bargein").attr('disabled','');
	  			$("#forceHangup").attr('disabled','');
	  			$("#forceBusy").attr('disabled','');
	  			$("#forceIdle").attr('disabled','');
	  			$("#forceOffline").attr('disabled','');
  			}
  		}else{
  			if($("#eavesdrop").length>0){
  				$("#eavesdrop").attr('disabled','');
	  			$("#whisper").attr('disabled','');
	  			$("#bargein").attr('disabled','');
	  			$("#forceHangup").attr('disabled','');
	  			$("#forceBusy").attr('disabled','');
	  			$("#forceIdle").attr('disabled','');
	  			$("#forceOffline").attr('disabled','');
  			}
  		}
  	}
  		
  	//设置指定按键启用状态
  	function setSingleButtonEnabled(buttonName,flag){
  		if($("#"+buttonName).length>0){
  			if(flag=='diabled'){
  				$("#"+buttonName).attr('disabled','');
  				if(buttonName!='eavesdrop')$("#eavesdrop").removeAttr('disabled');
  				if(buttonName!='whisper')$("#whisper").removeAttr('disabled');
  				if(buttonName!='bargein')$("#bargein").removeAttr('disabled');
  				if(buttonName!='forceHangup')$("#forceHangup").removeAttr('disabled');
  			}else{
  				$("#"+buttonName).removeAttr('disabled');
  			}
  		}
  	}
  	
  	//根据座席状态设置工具栏功能键启用状态
  	function setToolbarByStatus(state){
							var seatState = $('#signin span');
							if(state=="-1"){
								seatState.text("离线");
								setToolbarEnabled(false,false,false,false,false,false,false,false,false,false);
							}else if(state=="0"){
								seatState.text("空闲");
								setToolbarEnabled(true,true,true,false,false,false,false,false,false,true);
							}else if(state=="1"){
								seatState.text("振铃");
								setToolbarEnabled(false,false,false,false,false,false,false,false,false,true);
							}else if(state=="2"){
								seatState.text("通话");
								setToolbarEnabled(false,false,false,true,true,false,true,true,true,true);
							}else if(state=="3"){
								seatState.text("保持");
								setToolbarEnabled(false,false,false,false,false,true,false,false,false,true);
							}else if(state=="4"){
								seatState.text("后处理");
								setToolbarEnabled(true,false,false,false,false,false,false,false,false,true);
							}else if(state=="5"){
								seatState.text("被占用");
								setToolbarEnabled(false,false,false,false,false,false,false,false,false,true);
							}else if(state=="6"){
								seatState.text("忙碌");
								setToolbarEnabled(true,false,false,false,false,false,false,false,false,true);
							}else if(state=="7"){
								seatState.text("离开");
								setToolbarEnabled(true,false,false,false,false,false,false,false,false,true);
							}else if(state=="8"){
								seatState.text("仅呼出");
								setToolbarEnabled(true,false,false,false,false,false,false,false,false,true);
							}
  	}
  	
  	function setMessageInnerHTML(innerHTML){
  		//alert(innerHTML);
  	}
  	
  	function closeWebSocket(){
  		websocket.close();
  	}
  	
  	function send(){
		 $(".my-loading").show();
  		var message = "{'method':'"+method+"','agentno':'"+agentno+"','password':'"+password+"','exten':'"+exten+"','agentstatus':'"+agentstatus+"','pstnnumber':'"+pstnnumber+"','callee':'"+callee+"'}";
  		websocket.send(message);
  	}
    
    //签入或签出    
    function signInOrOut(){
		if(heartbeatId>=0){
			clearInterval(heartbeatId);
		}
		agentstatus = "0";
		if(!signin){
			if(useWebrtc=="yes"){
				loadSipml();
				sipRegister();
			}
			method = "signout";
			send();
			hasResponse = false;
    		displayLoadingMessage();
			setTimeout('delaySignin()',3000);
			setTimeout('timeout()',8000);
			return;
		}

    	hasResponse = false;
    	displayLoadingMessage();
    	method = "signin";
    	if(signin){
    		method = "signout";
			if(useWebrtc=="yes"){
				sipUnRegister();
			}
    	}else{
			agentstatus = "0";//签入时默认为空闲
		}
  		send();
  		setTimeout('timeout()',8000);
	}
	
	//延迟签入
	function delaySignin(){
		method = "signin";
		agentstatus = "0";//签入时默认为空闲
  		send();
	}


	
	//请求超时
	function timeout(){
		if(!hasResponse){
			hideLoadingMessage();
			loseHeartbeat = 0;
			var seatState = $('#seat_state');
			var signInOrOut = $('#signInOrOut');
			var signInOrOutHtml = signInOrOut.html();
			signin = false;
			signInOrOut.html(signInOrOutHtml.replace("签出","签入"));
			if(heartbeatId>=0){
				clearInterval(heartbeatId);
			}
			seatState.text("未签入");
			setToolbarEnabled(false,false,false,false,false,false,false,false,false,false);
  			//websocket.send(message);
			alert("与服务器连接中断，请重新签入");
		}	
	}
	
	//心跳检测
	function heartbeat(){
		if(loseHeartbeat>1){
			loseHeartbeat = 0;
			var seatState = $('#seat_state');
			var signInOrOut = $('#signInOrOut');
			var signInOrOutHtml = signInOrOut.html();
			signin = false;
			signInOrOut.html(signInOrOutHtml.replace("签出","签入"));
			if(heartbeatId>=0){
				clearInterval(heartbeatId);
			}
			seatState.text("未签入");
			setToolbarEnabled(false,false,false,false,false,false,false,false,false,false);
  			//websocket.send(message);
			alert("与服务器连接中断，请重新签入");
		}else{
			loseHeartbeat = loseHeartbeat+1;
			var message = "{'method':'heartbeat','agentno':'"+agentno+"'}";
	  		websocket.send(message);
  		}
	}	
	
	function changeStatusInterval(){
		if(monitorAgents!=''){
			$.each(monitorAgents.split(","), function(i,mAgent){  
				if($("#tdacttime_"+mAgent).length>0){
					var changeTime = $("#tdacttime_"+mAgent).val();
					if(Number(changeTime)>=0){
						changeTime = Number(changeTime)+1;
						$("#tdtime_"+mAgent).html(formatChangeTime(Number(changeTime)));
						$("#tdacttime_"+mAgent).val(changeTime)
					}else{
						$("#tdtime_"+mAgent).html("");
					}
				}
  			});  
		}
	}
	
	//响应事件 
	function onEvent(eventType,state,methodType,code,jsonStr){
  		$(".my-loading").hide();
		var seatState = $('#seat_state');
		var signInOrOut = $('#signInOrOut');
		var signInOrOutHtml = signInOrOut.html();
		var eventAgentNo = jsonStr.agentno;
		if(eventType=='event'){
			if(methodType=='signin'){
						if(code=='0'){
		  					signin = true;
							signInOrOut.html(signInOrOutHtml.replace("签入","签出"));
							agentstatus="1";
							method="setAcsType";//通话结束后座席状态默认空闲
							send();
							if(heartbeatId>=0){
								clearInterval(heartbeatId);
							}
							heartbeatId = setInterval("heartbeat()",timeoutSec*1000);
							
							method="getDefinedRoleRights";//获取座席权限
							send();
							alert("签入成功");
							$("#notsignin").hide();
							$("#signin").show();
		  				}else if(code=='-53'){
							alert("用户名或密码错误，签入失败");
						}else{
		  					alert("签入失败[ErrorCode:"+code+"]");
		  				}
			}else if(methodType=='disconnect'){
				hasResponse = true;
	  			hideLoadingMessage();
	  			if(methodType=='disconnect'){//连接断开
	  				signin = false;
					signInOrOut.html(signInOrOutHtml.replace("签出","签入"));
					seatState.text("未签入");
					$("#notsignin").show();
					$("#signin").hide();
					if(heartbeatId>=0){
						clearInterval(heartbeatId);
					}
					if(changeStatusId>=0){
						clearInterval(changeStatusId);
					}
					setToolbarEnabled(false,false,false,false,false,false,false,false,false,false);
	  			}
			}else if(methodType=='agent_status_change'){//座席状态被改变
				if(eventAgentNo==agentno){
					setToolbarByStatus(state);
				}
				if($("#doctree").length>0){
					setMonitorbarStatus(eventAgentNo,state);
				}
			}else if(methodType=="common_callin_bridge_ring"){//呼入弹屏
				if(eventAgentNo==agentno){
					var agentUuid = jsonStr.agentUuid;
					var customerNum = jsonStr.customerNum;
					var url=popupUrl+"?phone="+customerNum+"&agentno="+eventAgentNo+"&agentUUID="+agentUuid;
					window.open (url,'来电弹屏') 
				}
			}else if(methodType=="manual_callout_agent_ring"){//呼出弹屏
				if(eventAgentNo==agentno){
					var agentUuid = jsonStr.agentUuid;
					var customerNum = jsonStr.customerNum;
					var url=popupUrl+"?phone="+customerNum+"&agentno="+eventAgentNo+"&agentUUID="+agentUuid;
					window.open (url,'呼出弹屏') 
				}
			}else if(methodType=="blind_transfer_ring"){//转接弹屏
				if(eventAgentNo==agentno){
					var agentUuid = jsonStr.agentUuid;
					var customerNum = jsonStr.customerNum;
					var url=popupUrl+"?phone="+customerNum+"&agentno="+eventAgentNo+"&agentUUID="+agentUuid;
					window.open (url,'转接弹屏') 
				}
			}else if(methodType=="get_agentgroup_list"){//获取座席组列表
				var agentGroupNames = jsonStr.agentgroupname;
				var agentGroupNums = jsonStr.agentgroupnum.split(",");
				agentGroupStr = '[{"id":1,"pId":0,"name":"座席组","open":true,"isParent":true,"busid":"-1"}';//座席组信息
				var idx = 100;
				var tempAgentGroup = ','+roleAgentGroupNum+',';
				$.each(agentGroupNames.split(","), function(i,groupName){
					if(tempAgentGroup.indexOf(agentGroupNums[i])>0){
						idx = idx+1;
						agentGroupStr=agentGroupStr+',{"id":'+idx+',"pId":1,"name":"'+groupName+'","open":false,"isParent":false,"busid":"'+agentGroupNums[i]+'"}';						}  
  				});   
  				var zNodesStr = agentGroupStr+queueStr+']';
  				var zNodes =JSON.parse(zNodesStr);
  				if($("#doctree").length>0){
  					$.fn.zTree.init($("#doctree"), setting, zNodes);
       				var treeObj = $.fn.zTree.getZTreeObj("doctree"); 
  				}
			}else if(methodType=="get_queue_list"){//获取队列列表
				var queueNames = jsonStr.queuename;
				var queueNums = jsonStr.queuenum.split(",");
				queueStr = ',{"id":2,"pId":0,"name":"队列","open":true,"isParent":true,"busid":"-1"}';//队列信息
				var idx = 200;
				var tempQueue = ','+roleQueueNum+',';
				$.each(queueNames.split(","), function(i,queueName){  
					if(tempQueue.indexOf(queueNums[i])>0){
						idx = idx+1;
						queueStr=queueStr+',{"id":'+idx+',"pId":2,"name":"'+queueName+'","open":false,"isParent":false,"busid":"'+queueNums[i]+'"}';
					}
  				});   
  				var zNodesStr = agentGroupStr+queueStr+']';
  				var zNodes =JSON.parse(zNodesStr);
  				if($("#doctree").length>0){
  					$.fn.zTree.init($("#doctree"), setting, zNodes);
       				var treeObj = $.fn.zTree.getZTreeObj("doctree"); 
  				}
			}else if(methodType=="get_agents_of_agentgroup"){//获取座席组中座席列表
				var agentNums = jsonStr.agentnum;
				monitorAgents = jsonStr.agentnum;
				var message = "{'method':'getAgentInfo','agentno':'"+agentno+"','callee':'"+agentNums+"'}";
	  			websocket.send(message);
			}else if(methodType=="get_agents_of_queue"){//获取队列中座席列表
				var agentNums = jsonStr.agentnum;
				monitorAgents = jsonStr.agentnum;
				var message = "{'method':'getAgentInfo','agentno':'"+agentno+"','callee':'"+agentNums+"'}";
	  			websocket.send(message);
			}else if(methodType=="get_agents_monitor_info"){//获取所有座席信息
				selectAgent="";
				setMonitorbarEnabled(selectAgent);
				var agentNames = jsonStr.agentname.split(",");
				var agentNums = jsonStr.agentnum.split(",");
				var agentGroupNums = jsonStr.agentgroupnum.split(",");
				var agentStatus = jsonStr.agentstatus.split(",");
				var statusChangeTime = jsonStr.statuschangetime.split(",");
				if($("#tab_monitor").length>0){
					var tab_monitor= $("#tab_monitor");
					tab_monitor.empty();
					var idx = 1;
					var vTr= "<tr style='background:#CECEFF'><th width='70px' style='border:1px solid #eee;'>状态</th><th width='70px' style='border:1px solid #eee;'>工号</th>"+
					"<th width='115px' style='border:1px solid #eee;'>姓名</th><th width='95px' style='border:1px solid #eee;'>时长</th>"+
					"</tr>";
					tab_monitor.append(vTr); 
					$.each(agentNums, function(i,agentNum){  
						vTr= "<tr id='tr_"+agentNum+"'><input type='hidden' id='tdacttime_"+agentNum+"' value='"+statusChangeTime[i]+"'><td id='tdstatus_"+agentNum+"' style='border:1px solid #eee;'>"+
							statusMap.get(agentStatus[i])+"</td><td style='border:1px solid #eee;'>"+agentNum+"</td><td style='border:1px solid #eee;'>"+agentNames[i]+
							"</td><td id='tdtime_"+agentNum+"' style='border:1px solid #eee;'></td></tr>";
		      				tab_monitor.append(vTr); 
							idx = idx+1;
	  			});
	  				
					var tbList = $("#tab_monitor");
					tbList.each(function() { 
						var self = this; 
					
						// 鼠标经过的行变色 
						$("tr:not(:first)", $(self)).hover( 
						function () { 
							$(this).addClass('hoverTD');$(this).removeClass('table-td-content'); 
						}, 
						function () { 
							$(this).removeClass('hoverTD');$(this).addClass('table-td-content'); 
						}); 
					
						// 选择行变色 
						$("tr", $(self)).click(function (){ 
							var trThis = this; 
							if($(trThis).attr("id").indexOf("tr_")<0){
								return;
							} 
							$(self).find(".trSelected").removeClass('trSelected'); 
							$(trThis).addClass('trSelected'); 
							selectAgent=$(trThis).attr("id").split("_")[1];
							setMonitorbarEnabled(selectAgent);
						
						}); 
					}); 
  				}
  				
  				if(changeStatusId>=0){
					clearInterval(changeStatusId);
				}
				changeStatusId = setInterval("changeStatusInterval()",1000);
			}else if(methodType=="get_defined_role_rights"){//获取座席权限
				roleAgentGroupNum = jsonStr.agentgroupnum;
				roleQueueNum = jsonStr.queuenum;
			} 
		}else if(eventType=='response'){
			if(methodType=='signin'){
					hasResponse = true;
					hideLoadingMessage();
					if(code=='-6'){
		  				alert("请不要重复签入");
		  			}else if(code=='-11'){
		  				alert("分机不在线，签入失败");
		  			}else if(code=='-12'){
		  				alert("分机已被其他座席绑定，签入失败");
		  			}else if(code!='0'){
		  				alert("签入失败[ErrorCode:"+code+"]");
		  			}
	  		}else if(methodType=='signout'){
					hasResponse = true;
					hideLoadingMessage();
					if(code=='0'){
	  					signin = false;
						signInOrOut.html(signInOrOutHtml.replace("签出","签入"));
						if(heartbeatId>=0){
							clearInterval(heartbeatId);
						}
						alert("签出成功");
						seatState.text("未签入");
						setToolbarEnabled(false,false,false,false,false,false,false,false,false,false);
	  				}else{
	  					alert("签出失败");
	  				}
	  		}else if(methodType=='manual_callout'){//外呼
				if(code=='-19' || code=='-22'){
					alert("接入号不正确，呼叫失败");
				}else if(code=='-24' || code=='-31'){
					alert("座席非空闲状态，呼叫失败");
				}else if(code!='0'){
					alert("呼叫失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='internal_call'){//内呼
				if(code=='-30'){
					alert("被叫座席不在线，呼叫失败");
				}else if(code=='-24' || code=='-31'){
					alert("座席非空闲状态，呼叫失败");
				}else if(code!='0'){
					alert("呼叫失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='blind_transfer'){//转接座席
				if(code=='-30'){
					alert("被叫座席不在线，呼叫失败");
				}else if(code=='-24' || code=='-31' || code=='-32'){
					alert("座席非空闲状态，呼叫失败");
				}else if(code!='0'){
					alert("呼叫失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='threeway'){//三方
				if(code=='-30'){
					alert("被叫座席不在线，呼叫失败");
				}else if(code=='-24' || code=='-31' || code=='-32'){
					alert("座席非空闲状态，呼叫失败");
				}else if(code!='0'){
					alert("呼叫失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='eavesdrop'){//监听
				if(code=='0'){
					setSingleButtonEnabled('eavesdrop','diabled');
				}else{
					alert("监听失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='whisper'){//密语
				if(code=='0'){
					setSingleButtonEnabled('whisper','diabled');
				}else{
					alert("密语失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='bargein'){//强插
				if(code=='0'){
					setSingleButtonEnabled('bargein','diabled');
				}else{
					alert("强插失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='force_hangup'){//强拆
				if(code=='0'){
					setSingleButtonEnabled('forceHangup','diabled');
				}else{
					alert("强拆失败[ErrorCode:"+code+"]");
				}
			}else if(methodType=='heartbeat'){//心跳检测
				if(code=='0'){
					loseHeartbeat = 0;
				}
			}
		}
	}
	
	//修改座席状态
	function changeStatus(state){
		agentstatus = state;
		method="changeStatus";
		send();
	}
	
	
	//内呼
	function calloutInternal(){
		var dialog = art.dialog({
		title: '呼出内线',
	    content: '<p>座席号码：'+'<input id="demo-labs-input" style="width:15em; padding:4px" /></p>',
	    fixed: true,
	    id: 'Fm7',
	    icon: 'succeed',
	    //time: 2,
	    okVal: '确定',
	    ok: function () {
	    	var input = document.getElementById('demo-labs-input');
	    	var reNum =/^[0-9]+$/;
	    	if (!reNum.test(input.value)) {
	            input.select();
	            input.focus();
	            alert("请输入有效的座席号码");
	            return false;
	        } else {
				var message = "{'method':'internalCall','agentno':'"+agentno+"','callee':'"+input.value+"'}";
	  			websocket.send(message);
	        };
	    },
	    cancel: true
		});
	}
	
	//外呼
	function calloutExternal(){
		var input = document.getElementById('phoneNum');
		var reNum =/^[0-9]+$/;
		if (!reNum.test(input.value)) {
			input.select();
			input.focus();
			alert("请输入有效的电话号码");
			return false;
		} else {
			var message = "{'method':'manualCallout','agentno':'"+agentno+"','pstnnumber':'"+pstnnumber+"','callee':'"+input.value+"'}";
			$("#myPhoneModal").modal('hide');
			websocket.send(message);
		};
	}
	
	//挂断
	function hangUp(){
		method="hangup";
		send();
	}
	
	//评分
	function customerEvaluate(){
		method="evaluate";
		send();
	}
	
	//保持
	function hold(){
		method="hold";
		send();
	}
	
	//解除保持
	function unHold(){
		method="unhold";
		send();
	}
	
	//转接座席
	function transfer(){	
		var dialog = art.dialog({
		title: '转接座席',
	    content: '<p>座席号码：'+'<input id="demo-labs-input" style="width:15em; padding:4px" /></p>',
	    fixed: true,
	    id: 'Fm7',
	    icon: 'succeed',
	    //time: 2,
	    okVal: '确定',
	    ok: function () {
	    	var input = document.getElementById('demo-labs-input');
	    	var reNum =/^[0-9]+$/;
	    	if (!reNum.test(input.value)) {
	            input.select();
	            input.focus();
	            alert("请输入有效的座席号码");
	            return false;
	        } else {
				var message = "{'method':'blindTransfer','agentno':'"+agentno+"','callee':'"+input.value+"'}";
	  			websocket.send(message);
	        };
	    },
	    cancel: true
		});
	}
	
	//三方
	function threeway(){	
		var dialog = art.dialog({
		title: '三方',
	    content: '<p>座席号码：'+'<input id="demo-labs-input" style="width:15em; padding:4px" /></p>',
	    fixed: true,
	    id: 'Fm7',
	    icon: 'succeed',
	    //time: 2,
	    okVal: '确定',
	    ok: function () {
	    	var input = document.getElementById('demo-labs-input');
	    	var reNum =/^[0-9]+$/;
	    	if (!reNum.test(input.value)) {
	            input.select();
	            input.focus();
	            alert("请输入有效的座席号码");
	            return false;
	        } else {
				var message = "{'method':'threeway','agentno':'"+agentno+"','callee':'"+input.value+"'}";
	  			websocket.send(message);
	        };
	    },
	    cancel: true
		});
	}

	//话务监控
	function monitor(){
		var dialog = art.dialog({
		title: '话务监控',
		width: '700px', 
		height: '380px', 
	    content: '<table width="100%" id="tab_toolbar" cellspacing="0"></table><br/><div style="width:700px; height:380px; overflow:auto; border:0px solid #eee;">'+
	    '<table width="100%"><tr><td width="30%" valign="top"><span id="doctree" class="ztree" style="z-index:-1"></span></td></td><td width="70%" valign="top">'+
	    '<table id="tab_monitor" cellspacing="0"></table></td></tr></table></div>',
	    fixed: true,
	    id: 'Fm7',
	    lock: true,
	    icon: '',
	    cancelVal: '关闭',
	    cancel: true
		});
		if(roleAgentGroupNum!=''){
			method="getAgentGroupList";
			send();	
		}
		if(roleQueueNum!=''){
			method="getQueueList";
			send();
		}
		if(roleAgentGroupNum!='' || roleQueueNum!=''){
			var tab_toolbar= $("#tab_toolbar");
			tab_toolbar.empty();
			var vTr= "<tr><td align='center'><div class='aui_buttons'>"+
						"<button type='button' id='eavesdrop' onclick='eavesdrop()' disabled='' class='aui_state_highlight'>监听</button>"+
						"<button type='button' id='whisper' onclick='whisper()' disabled='' class='aui_state_highlight'>密语</button>"+
						"<button type='button' id='bargein' onclick='bargein()' disabled='' class='aui_state_highlight'>强插</button>"+
						"<button type='button' id='forceHangup' onclick='forceHangup()' disabled='' class='aui_state_highlight'>强拆</button>"+
						"<button type='button' id='forceBusy' onclick='forceBusy()' disabled='' class='aui_state_highlight'>强制置忙</button>"+
						"<button type='button' id='forceIdle' onclick='forceIdle()' disabled='' class='aui_state_highlight'>强制置闲</button>"+
						"<button type='button' id='forceOffline' onclick='forceOffline()' disabled='' class='aui_state_highlight'>强制下线</button>"+
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
						"</div></td></tr>";
			tab_toolbar.append(vTr);
		}
	}
	
	//监听
	function eavesdrop(){
		method="eavesdrop";
		callee=selectAgent;
		send();
	}
		
	//密语
	function whisper(){
		method="whisper";
		callee=selectAgent;
		send();
	}
	
	//强插
	function bargein(){
		method="bargein";
		callee=selectAgent;
		send();
	}
	
	//强拆
	function forceHangup(){
		method="forceHangup";
		callee=selectAgent;
		send();
	}
	
	//强制置忙
	function forceBusy(){
		method="forceBusy";
		callee=selectAgent;
		send();
	}
	
	//强制置闲
	function forceIdle(){
		method="forceIdle";
		callee=selectAgent;
		send();
	}
	
	//强制下线
	function forceOffline(){
		method="forceOffline";
		callee=selectAgent;
		send();
	}

	//时间格式化
	function formatChangeTime(stime){
		if(stime==0)return '';
		if(stime>0){
			hour = Math.floor(stime/3600);
   			min = Math.floor(stime/60) % 60;
   			sec = Math.floor(stime % 60);
		}else{
			hour=0;
			min=0;
			sec=1;
		}
		if(hour>0){
			secStr = hour+"小时"+min+"分"+sec+"秒";
		}else if(min>0){
			secStr = min+"分"+sec+"秒";
		}else{
			secStr = sec+"秒";
		}
		return secStr;
	}

	//话务监控ZTree Begin--------------------------------------------------------------------------------
		var setting = {
			view: {
				dblClickExpand: false,
				showLine:false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			check: {
				enable: false
			},
			callback: {
				onClick: zTreeOnClick  
				}
		};
		function zTreeOnClick(event, treeId, treeNode) {
		    //alert(treeNode.busid + ", " + treeNode.name);
		    if(treeNode.busid!='-1'){
		    	if(treeNode.pId==1){
		   	 		var message = "{'method':'getAgentsOfAgentgroup','agentno':'"+agentno+"','param1':'"+treeNode.busid+"'}";
	  				websocket.send(message);
	  			}else if(treeNode.pId==2){
	  				var message = "{'method':'getAgentsOfQueue','agentno':'"+agentno+"','param1':'"+treeNode.busid+"'}";
	  				websocket.send(message);
	  			}
	  		}
		}
		
	    function createTree () {
        }
		
		function onCheck(e, treeId, treeNode) {
			count();
		}
		
		function count() {
			function isForceHidden(node) {
				if (!node.parentTId) return false;
				var p = node.getParentNode();
				return !!p.isHidden ? true : isForceHidden(p);
			}
			var zTree = $.fn.zTree.getZTreeObj("doctree");
			checkCount = zTree.getCheckedNodes(true).length;
			nocheckCount = zTree.getCheckedNodes(false).length;
			hiddenNodes = zTree.getNodesByParam("isHidden", true);
			hiddenCount = hiddenNodes.length;
			
			checkNodes = zTree.getCheckedNodes(true);
			var checkStr = "";
			for (var i=0, j=checkNodes.length; i<j; i++) {
				var n = checkNodes[i];
				checkStr = checkStr+","+n.id;
			}
			
			if(""==checkStr) checkStr=",";
			document.getElementById("moduleStr").value=checkStr;
			
			for (var i=0, j=hiddenNodes.length; i<j; i++) {
				var n = hiddenNodes[i];
				if (isForceHidden(n)) {
					hiddenCount -= 1;
				} else if (n.isParent) {
					hiddenCount += zTree.transformToArray(n.children).length;
				}
			}
		}