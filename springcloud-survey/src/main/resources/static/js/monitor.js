var monitor = '<script type="text/x-template" id="monitor">\n' +
    '<div>\n' +
    '<a class="btn btn-primary btn-xs" onclick="signInOrOut()" id="signInOrOut">签入</a>\n' +
    '<div> \n' +
    '<ul class="nav nav-tabs">\n' +
    '   <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">坐席组</a></li>\n' +
    '   <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false"> 队列</a></li>\n' +
    '</ul>' +
    '</div>\n' +
    '<div class="tab-content">\n' +
    '   <div id="tab-1" class="tab-pane active">\n' +
    '       <div class="panel-body">\n' +
    '           <div class="ibox-content"> \n' +
    '               <div class="col-sm-3"> \n' +
    '                  <div class="ibox float-e-margins">\n' +
    '                    <div class="ibox-content">\n' +
    '                        <div class="file-manager">\n' +
    '                            <h5>坐席组</h5>\n' +
    '                            <ul class="folder-list" style="padding: 0">\n' +
    '                                <li v-for="(item, index) in agentGroups" @click="showUserAgent(index)"><a href="##"><i class="fa fa-folder"></i> {{item}}</a>\n' +
    '                                </li>\n' +
    '                            </ul>\n' +
    '                            <div class="clearfix"></div>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                </div>' +
    '               </div>\n' +
    '               <div class="col-sm-9"> \n' +
    '                   <div class="file-box" v-for="item in agentInfos">\n' +
    '                          <div class="file">\n' +
    '                                <a href="##" >\n' +
    '                                    <span class="corner"></span>\n' +
    '                                    <div class="icon">\n' +
    '                                        <i v-bind:style="{ color: agentStatusColor(item.agentstatusInfo) }">{{item.agentNum}}</i>\n' +
    '                                        <span style="position: absolute; left: 10px; top: 10px;" v-bind:style="{ color: agentStatusColor(item.agentstatusInfo) }">{{agentStatusHandler(item.agentstatusInfo)}}</span>\n' +
    '                                        <a style="position:absolute; top: 10px; right: 10px;" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-wrench" style="font-size: 18px;"></i></a>\n' +
    '                                        <ul style="position: absolute; top: 30px;" class="dropdown-menu dropdown-user">\n' +
    '                                                 <li @click="monitot(item)" style="text-align: center; border: 1px grey solid; border-top-left-radius: 5px; border-top-right-radius: 5px;" ><a href="##"></a>监听</li> \n' +
    '                                                 <li @click="forceBusy(item)" style="text-align: center; border: 1px grey solid;" ><a href="##"></a>置忙</li> \n' +
    '                                                 <li @click="forceFree(item)" style="text-align: center; border: 1px grey solid;"><a href="##"></a>置闲</li> \n' +
    '                                                 <li @click="forceOffLine(item)" style="text-align: center; border: 1px grey solid; border-bottom-left-radius: 5px; border-bottom-right-radius: 5px;"><a href="##"></a>下线</li> \n' +
    '                                        </ul>\n' +
    '                                    </div>\n' +
    '                                    <div class="file-name">\n' +
    '                                        <span v-bind:style="{ color: agentStatusColor(item.agentstatusInfo) }">{{item.agentName}}</span>\n' +
    '                                        <br>\n' +
    '                                        <small>累计在线：{{item.agentChangeTime | sToTime}}</small>\n' +
    '                                    </div>\n' +
    '                                </a>\n' +
    '                            </div>\n' +
    '                        </div>\n' +
    '               </div>\n' +
    '           </div>\n' +
    '       </div>\n' +
    '   </div>\n' +
    '   <div id="tab-2" class="tab-pane">\n' +
    '       <div class="panel-body">\n' +
    '           <div class="ibox-content"> \n' +
    '               <div class="col-sm-3"> \n' +
    '                  <div class="ibox float-e-margins">\n' +
    '                    <div class="ibox-content">\n' +
    '                        <div class="file-manager">\n' +
    '                            <h5>队列</h5>\n' +
    '                            <ul class="folder-list" style="padding: 0">\n' +
    '                                <li v-for="(item, index) in agentQueues" @click="showQueueUserAgent(item.queueNum)"><a href="##"><i class="fa fa-folder"></i> {{item.queueName}}</a>\n' +
    '                                </li>\n' +
    '                            </ul>\n' +
    '                            <div class="clearfix"></div>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                </div>' +
    '               </div>\n' +
    '               <div class="col-sm-9"> \n' +
    '                   <div class="file-box" v-for="item in agentInfos">\n' +
    '                          <div class="file">\n' +
    '                                <a href="##" >\n' +
    '                                    <span class="corner"></span>\n' +
    '                                    <div class="icon">\n' +
    '                                        <i v-bind:style="{ color: agentStatusColor(item.agentstatusInfo) }">{{item.agentNum}}</i>\n' +
    '                                        <span style="position: absolute; left: 10px; top: 10px;" v-bind:style="{ color: agentStatusColor(item.agentstatusInfo) }">{{agentStatusHandler(item.agentstatusInfo)}}</span>\n' +
    '                                    </div>\n' +
    '                                    <div class="file-name">\n' +
    '                                        <span v-bind:style="{ color: agentStatusColor(item.agentstatusInfo) }">{{item.agentName}}</span>\n' +
    '                                        <br>\n' +
    '                                        <small>累计在线：{{item.agentChangeTime | sToTime}}</small>\n' +
    '                                    </div>\n' +
    '                                </a>\n' +
    '                            </div>\n' +
    '                        </div>\n' +
    '               </div>\n' +
    '           </div>\n' +
    '       </div>\n' +
    '   </div>\n' +
    '</div>\n' +
    '</div>\n' +
    '</script>';
var dom = document.querySelector("body");
dom.innerHTML += monitor;


websocketUrl = 'wss://tj.svdata.cn/yscrm/websocket'//呼叫中心服务器websocket请求地址
agentno = '8888';//座席号码
password = 'svdata123';//座席密码
exten = '8888';//座席分机
pstnnumber = '82312340';//外线号码
popupUrl = 'http://www.baidu.com'//来电弹屏地址;
useMonitor = 'yes';//如是班长可以启用话务监控

var useWebrtc = 'yes';//是否使用浏览器内置软电话，需要浏览器支持webrtc，如chrome
var sipServer = 'tj.svdata.cn';//软电话注册地址，不启用内置软电话可以不配置
var sipPort = '7067';//软电话注册端口，不启用内置软电话可以不配置
var extenPwd = 'svdata123';//分机密码，不启用内置软电话可以不配置
var autoAnswer = 'no';//是否自动接听电话，只有启用内置软电话才生效


//离开或刷新页面
window.onbeforeunload = function () {
    //return '确认要退出页面吗?';
    if (websocket != null) {
        method = "signout";
        send();
        websocket.close();
    }
    if (useWebrtc == "yes") {
        sipUnRegister();
    }
}


Vue.component('monitor', {
    props: ['total', 'size', 'page', 'changge', 'isUrl'],
    template: '#monitor',
    data() {
        return {
            agentGroups: [],
            agentGroupIds: [],
            agentInfos: [],
            agentQueues: [],
            agentQueueInfos: [],
            cti: {
                status: 0,
                server: 0
            },
            selectedAgentgroup: '',
            selectedQueue: ''
        }
    },
    methods: {
        monitot(item) {
            selectAgent = item.agentNum;
            eavesdrop();
            this.showQueueUserAgent();
            this.showUserAgent();
        },
        forceBusy(item) {
            selectAgent = item.agentNum;
            forceBusy();
            this.showQueueUserAgent();
            this.showUserAgent();
        },
        forceFree(item) {
            selectAgent = item.agentNum;
            forceIdle();
            this.showQueueUserAgent();
            this.showUserAgent();
        },
        forceOffLine(item) {
            selectAgent = item.agentNum;
            forceOffline();
            this.showQueueUserAgent();
            this.showUserAgent();
        },
        agentStatusHandler(state) {
            if (state == "-1") {
                return "离线";
            } else if (state == "0") {
                return "空闲";
            } else if (state == "1") {
                return "振铃";
            } else if (state == "2") {
                return "通话";
            } else if (state == "3") {
                return "保持";
            } else if (state == "4") {
                return "后处理";
            } else if (state == "5") {
                return "被占用";
            } else if (state == "6") {
                return "忙碌";
            } else if (state == "7") {
                return "离开";
            } else if (state == "8") {
                return "仅呼出";
            }
        },
        agentStatusColor(s) {
            if (s == '-1') {
                return "#dadada";
            } else if (s == '0') {
                return "#3c763d";
            } else if (s == "1") {
                return "#8a6d3b";
            } else {
                return "#e44c49";
            }
        },
        showQueueUserAgent(n) {
            if(n) {
                this.selectedQueue = n;
            }
            let message = "{'method':'getAgentsOfQueue','agentno':'" + agentno + "','param1':'" + this.selectedQueue + "'}";
            websocket.send(message);
        },
        showUserAgent(index) {
            let groupId = this.agentGroupIds[index];
            if(groupId) {
                this.selectedAgentgroup = groupId;
            }
            let message = "{'method':'getAgentsOfAgentgroup','agentno':'" + agentno + "','param1':'" + this.selectedAgentgroup + "'}";
            websocket.send(message);
        },
        initWebsocket() {
            let that = this;
            //注册WebSocket服务
            if ('WebSocket' in window) {
                websocket = new WebSocket(websocketUrl);
                websocket.onerror = function () {
                    setMessageInnerHTML("error");
                };

                $ctiserver = this;
                websocket.onopen = function (event) {
                    $ctiserver.cti.server = 1
                }
                websocket.onmessage = function () {
                    try {
                        var jsonStr = JSON.parse(event.data);
                        that.onEvent(jsonStr.type, jsonStr.status, jsonStr.method, jsonStr.retCode, jsonStr);
                    } catch (e) {
                        return false;
                    }
                };

                websocket.onclose = function () {
                    setMessageInnerHTML("close");
                };
            } else {
                alert('当前浏览器不支持WebSocket通信，座席功能将不能使用！')
            }


        },
        onEvent(eventType, state, methodType, code, jsonStr) {
            let that = this;
            console.log('onEvent---json--------->', jsonStr);
            console.log('methodType----', methodType);
            $(".my-loading").hide();
            var seatState = $('#seat_state');
            var signInOrOut = $('#signInOrOut');
            var signInOrOutHtml = signInOrOut.html();
            var eventAgentNo = jsonStr.agentno;
            if (eventType == 'event') {
                if (methodType == 'signin') {
                    if (code == '0') {
                        signin = true;
                        if (signInOrOut) {
                            signInOrOut.html(signInOrOutHtml.replace("签入", "签出"));
                        }
                        agentstatus = "1";
                        method = "setAcsType";//通话结束后座席状态默认空闲
                        send();
                        if (heartbeatId >= 0) {
                            clearInterval(heartbeatId);
                        }
                        heartbeatId = setInterval("heartbeat()", timeoutSec * 1000);

                        method = "getDefinedRoleRights";//获取座席权限
                        send();
                        toastr.success("签入成功");
                        method = "getAgentGroupList";
                        send();
                        method = "getQueueList";
                        send();
                        $("#notsignin").hide();
                        $("#signin").show();
                    } else if (code == '-53') {
                        toastr.error("用户名或密码错误，签入失败");
                    } else {
                        toastr.error("签入失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'disconnect') {
                    hasResponse = true;
                    hideLoadingMessage();
                    if (methodType == 'disconnect') {//连接断开
                        signin = false;
                        signInOrOut.html(signInOrOutHtml.replace("签出", "签入"));
                        seatState.text("未签入");
                        $("#hugupSeatOnlyOut").hide();
                        $("#hugupSeatFree").hide();
                        $("#notsignin").show();
                        $("#signin").hide();
                        if (heartbeatId >= 0) {
                            clearInterval(heartbeatId);
                        }
                        if (changeStatusId >= 0) {
                            clearInterval(changeStatusId);
                        }
                        setToolbarEnabled(false, false, false, false, false, false, false, false, false, false);
                    }
                } else if (methodType == 'agent_status_change') {//座席状态被改变
                    if (eventAgentNo == agentno) {
                        setToolbarByStatus(state);
                        agentstatusCurrent = state;
                    }
                    if ($("#doctree").length > 0) {
                        setMonitorbarStatus(eventAgentNo, state);
                    }
                } else if (methodType == "common_callin_bridge_ring") {//呼入弹屏
                    console.log("呼入弹屏", jsonStr);
                    console.log("-----------------------<><><>><>------", agentno);
                    console.log('------------tttttt---------------', new Date());
                    sessionStorage.setItem("customerNum", jsonStr.customerNum);
                    if (eventAgentNo == agentno) {
                        var agentUuid = jsonStr.agentUuid;
                        var customerNum = jsonStr.customerNum;
                        var url = popupUrl + "&phone=" + customerNum + "&seat=" + eventAgentNo + "&agentUUID=" + agentUuid;
                        window.openUrl = url;
                        $("#surveyIfram").attr('src', url);
                        console.log('实际要跳转的地址', window.openUrl);
                    }
                } else if (methodType == "manual_callout_agent_ring") {//呼出弹屏
                    console.log("呼入弹屏", jsonStr.customerNum);
                    sessionStorage.setItem("customerNum", jsonStr.customerNum);
                    if (eventAgentNo == agentno) {
                        var agentUuid = jsonStr.agentUuid;
                        var customerNum = jsonStr.customerNum;
                        var url = popupUrl + "&phone=" + customerNum + "&seat=" + eventAgentNo + "&agentUUID=" + agentUuid;
                        $("#surveyIfram").attr('src', url);
                        window.openUrl = url;
                    }
                } else if (methodType == "get_agentgroup_list") {//获取座席组列表
                    console.log("获取座席组列表", jsonStr);
                    let agentGroupNames = jsonStr.agentgroupname;
                    let agentGroupNums = jsonStr.agentgroupnum;
                    let tempAgentGroup = ',' + roleAgentGroupNum + ',';
                    that.agentGroups = [];
                    $.each(agentGroupNames.split(","), function (i, groupName) {
                        that.agentGroups.push(agentGroupNames.split(",")[i]);

                    });
                    that.agentGroupIds = [];
                    $.each(agentGroupNums.split(","), function (i, groupName) {
                        that.agentGroupIds.push(agentGroupNums.split(",")[i]);
                    });
                } else if (methodType == "get_queue_list") {//获取队列列表
                    let that = this;
                    var queueNames = jsonStr.queuename.split(",");
                    var queueNums = jsonStr.queuenum.split(",");
                    that.agentQueues = [];
                    for (let i = 0; i < queueNums.length; i++) {
                        let tmp = {
                            queueNum: queueNums[i],
                            queueName: queueNames[i]
                        };
                        that.agentQueues.push(tmp);
                    }
                } else if (methodType == "get_agents_of_agentgroup") {//获取座席组中座席列表
                    let agentNums = jsonStr.agentnum;
                    monitorAgents = jsonStr.agentnum;

                    let message = "{'method':'getAgentInfo','agentno':'" + agentno + "','callee':'" + agentNums + "'}";
                    websocket.send(message);
                } else if (methodType == "get_agents_of_queue") {//获取队列中座席列表
                    var agentNums = jsonStr.agentnum;
                    monitorAgents = jsonStr.agentnum;
                    var message = "{'method':'getAgentInfo','agentno':'" + agentno + "','callee':'" + agentNums + "'}";
                    websocket.send(message);
                } else if (methodType == "get_agents_monitor_info") {//获取所有座席信息
                    selectAgent = "";
                    setMonitorbarEnabled(selectAgent);
                    let agentNames = jsonStr.agentname.split(",");
                    let agentNums = jsonStr.agentnum.split(",");
                    let agentGroupNums = jsonStr.agentgroupnum.split(",");
                    let agentStatusList = jsonStr.agentstatus.split(",");
                    let statusChangeTime = jsonStr.statuschangetime.split(",");
                    this.agentInfos = [];
                    let that = this;
                    if (agentNames.length == agentNums.length) {
                        for (let i = 0; i < agentNums.length; i++) {
                            let tem = {
                                agentName: agentNames[i],
                                agentNum: agentNums[i],
                                agentstatusInfo: agentStatusList[i],
                                agentChangeTime: statusChangeTime[i]
                            };
                            that.agentInfos.push(tem);
                        }
                    } else {
                        alert("服务器错误")
                    }

                    if (changeStatusId >= 0) {
                        clearInterval(changeStatusId);
                    }
                    changeStatusId = setInterval("changeStatusInterval()", 1000);
                } else if (methodType == "get_defined_role_rights") {//获取座席权限
                    roleAgentGroupNum = jsonStr.agentgroupnum;
                    roleQueueNum = jsonStr.queuenum;
                }
            } else if (eventType == 'response') {
                if (methodType == 'signin') {
                    hasResponse = true;
                    hideLoadingMessage();
                    if (code == '-6') {
                        alert("请不要重复签入");
                    } else if (code == '-11') {
                        alert("分机不在线，签入失败");
                    } else if (code == '-12') {
                        alert("分机已被其他座席绑定，签入失败");
                    } else if (code != '0') {
                        alert("签入失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'signout') {
                    hasResponse = true;
                    hideLoadingMessage();
                    if (code == '0') {
                        signin = false;
                        signInOrOut.html(signInOrOutHtml.replace("签出", "签入"));
                        if (heartbeatId >= 0) {
                            clearInterval(heartbeatId);
                        }
                        alert("签出成功");
                        seatState.text("未签入");
                        $("#hugupSeatOnlyOut").hide();
                        $("#hugupSeatFree").hide();
                        setToolbarEnabled(false, false, false, false, false, false, false, false, false, false);
                    } else {
                        alert("签出失败");
                    }
                } else if (methodType == 'manual_callout') {//外呼
                    if (code == '-19' || code == '-22') {
                        alert("接入号不正确，呼叫失败");
                    } else if (code == '-24' || code == '-31') {
                        alert("座席非空闲状态，呼叫失败");
                    } else if (code != '0') {
                        alert("呼叫失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'internal_call') {//内呼
                    if (code == '-30') {
                        alert("被叫座席不在线，呼叫失败");
                    } else if (code == '-24' || code == '-31') {
                        alert("座席非空闲状态，呼叫失败");
                    } else if (code != '0') {
                        alert("呼叫失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'blind_transfer') {//转接座席
                    if (code == '-30') {
                        alert("被叫座席不在线，呼叫失败");
                    } else if (code == '-24' || code == '-31' || code == '-32') {
                        alert("座席非空闲状态，呼叫失败");
                    } else if (code != '0') {
                        alert("呼叫失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'threeway') {//三方
                    if (code == '-30') {
                        alert("被叫座席不在线，呼叫失败");
                    } else if (code == '-24' || code == '-31' || code == '-32') {
                        alert("座席非空闲状态，呼叫失败");
                    } else if (code != '0') {
                        alert("呼叫失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'eavesdrop') {//监听
                    if (code == '0') {
                        setSingleButtonEnabled('eavesdrop', 'diabled');
                    } else {
                        alert("监听失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'whisper') {//密语
                    if (code == '0') {
                        setSingleButtonEnabled('whisper', 'diabled');
                    } else {
                        alert("密语失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'bargein') {//强插
                    if (code == '0') {
                        setSingleButtonEnabled('bargein', 'diabled');
                    } else {
                        alert("强插失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'force_hangup') {//强拆
                    if (code == '0') {
                        setSingleButtonEnabled('forceHangup', 'diabled');
                    } else {
                        alert("强拆失败[ErrorCode:" + code + "]");
                    }
                } else if (methodType == 'heartbeat') {//心跳检测
                    if (code == '0') {
                        loseHeartbeat = 0;
                    }
                }
            }
        }
    },
    created() {
        this.initWebsocket();
    },
    watch: {}
})

