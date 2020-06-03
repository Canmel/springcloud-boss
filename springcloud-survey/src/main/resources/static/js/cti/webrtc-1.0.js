var oSipStack, oSipSessionRegister, oSipSessionCall, oSipSessionTransferCall;
var oConfigCall;
var oNotifICall;
var bDisableVideo = false;
var viewVideoLocal, viewVideoRemote, viewLocalScreencast;
var txtPhoneNumber;
var isConnecting = false;//是否通话中
var sipRegisted = false;//话机注册状态

//电话注册
function sipRegister() {
    var display_name = exten;
    var private_identity = exten;
    var public_identity = "sip:" + exten + "@" + sipServer;
    var rtcpassword = extenPwd;

    var realm = sipServer;
    var registered_address = sipServer;
    var registered_port = sipPort;

    if (registered_address === "") {
        alert("未填写sip外呼地址，不能注册，请注册后使用");
        return;
    }

    if (registered_port === "") {
        alert("未填写sip外呼端口，不能注册，请注册后使用");
        return;
    }

    var disable_video = true; // disable_video= "1";disable_video=="1"?true:false
    var enable_rtcweb_breaker = true; //enable_rtcweb_breaker=="1"?true:false
    var webSocket_server_url = "wss://" + registered_address + ":" + registered_port;
    ;
    var sip_outbound_proxy_url = "";
    var ice_servers = "[]";
    var max_bandwidth = "";
    var video_size = "";
    var disable_3gpp_early_ims = true;//disable_3gpp_early_ims=="1"?true:false
    var disable_debug_messages = true;//disable_debug_messages=="1"?true:false
    var cache_the_media_stream = false;//cache_the_media_stream=="1"?true:false
    var disable_call_button_options = false;//disable_call_button_options=="1"?true:false

    try {
        var o_impu = tsip_uri.prototype.Parse(public_identity);
        if (!o_impu || !o_impu.s_user_name || !o_impu.s_host) {
            alert("[" + public_identity + "] is not a valid Public identity");
            return;
        }

        // enable notifications if not already done
        if (window.webkitNotifications && window.webkitNotifications.checkPermission() != 0) {
            window.webkitNotifications.requestPermission();
        }

        // save credentials
        //saveCredentials();

        // update debug level to be sure new values will be used if the user haven't updated the page "error"|"info"
        SIPml.setDebugLevel("info");

        // create SIP stack
        oSipStack = new SIPml.Stack({
            realm: realm,
            impi: private_identity,
            impu: public_identity,
            password: rtcpassword,
            display_name: display_name,
            websocket_proxy_url: webSocket_server_url,
            outbound_proxy_url: sip_outbound_proxy_url,
            ice_servers: ice_servers,
            enable_rtcweb_breaker: enable_rtcweb_breaker,
            events_listener: {events: '*', listener: onSipEventStack},
            enable_early_ims: disable_3gpp_early_ims, // Must be true unless you're using a real IMS network
            enable_media_stream_cache: cache_the_media_stream,
            bandwidth: max_bandwidth, // could be redefined a session-level
            video_size: video_size, // could be redefined a session-level
            sip_headers: [
                {name: 'User-Agent', value: 'IM-client/OMA1.0 sipML5-v1.2016.03.04'},
                {name: 'Organization', value: 'Doubango Telecom'}
            ]
        });

        if (oSipStack.start() != 0) {
            tsk_utils_log_info("Failed to start the SIP stack");
        } else {
            return;
        }
    } catch (e) {
        //txtRegStatus.innerHTML = "<b>" + e + "</b>";
        //tsk_utils_log_info("<b>2:" + e + "</b>");
    }
}

//话机下线
function sipUnRegister() {
    if (oSipStack) {
        oSipStack.stop(); // shutdown all sessions
        sipRegisted = false;
    }
}

//呼叫
function sipCall(s_type, txtPhoneNumber) {
    var max_bandwidth = "";
    var video_size = "";
    if (oSipStack && !oSipSessionCall && !tsk_string_is_null_or_empty(txtPhoneNumber)) {
        if (s_type == 'call-screenshare') {
            if (!SIPml.isScreenShareSupported()) {
                tsk_utils_log_info("Screen sharing not supported. Are you using chrome 26+?");
                return;
            }
            if (!location.protocol.match('https')) {
                if (confirm("Screen sharing requires https://. Do you want to be redirected?")) {
                    sipUnRegister();
                    //window.location = 'https://ns313841.ovh.net/call.htm';
                    tsk_utils_log_info("必须支持https请求");
                }
                return;
            }
        }

        // create call session
        oSipSessionCall = oSipStack.newSession(s_type, oConfigCall);
        // make call
        if (oSipSessionCall.call(txtPhoneNumber) != 0) {
            oSipSessionCall = null;
            tsk_utils_log_info("Failed to make call");
            return;
        }
    } else if (oSipSessionCall) {
        tsk_utils_log_info("'<i>Connecting...</i>");
        oSipSessionCall.accept(oConfigCall);
    }
}

//接听
function sipAnswer() {
    sipCall("call-audio");
    //stopRingbackTone();
    //stopRingTone();
    // $("#msg_dial").hide();
}

//挂断
function sipHangUp() {
    if (oSipSessionCall) {
        tsk_utils_log_info("Terminating the call......");
        oSipSessionCall.hangup({events_listener: {events: '*', listener: onSipEventSession}});
    }
}

function startRingTone() {
    try {
        ringtone.play();
    } catch (e) {
    }
}

function stopRingTone() {
    try {
        ringtone.pause();
    } catch (e) {
    }
}

function startRingbackTone() {
    try {
        ringbacktone.play();
    } catch (e) {
    }
}

function stopRingbackTone() {
    try {
        ringbacktone.pause();
    } catch (e) {
    }
}

function onSipEventStack(e) {
    tsk_utils_log_info("===SipEventStack type=" + e.type);
    switch (e.type) {
        case 'started': {
            // catch exception for IE (DOM not ready)
            try {
                // LogIn (REGISTER) as soon as the stack finish starting
                oSipSessionRegister = this.newSession('register', {
                    expires: 200,
                    events_listener: {events: '*', listener: onSipEventSession},
                    sip_caps: [
                        {name: '+g.oma.sip-im', value: null},
                        //{ name: '+sip.ice' }, // rfc5768: FIXME doesn't work with Polycom TelePresence
                        {name: '+audio', value: null},
                        {name: 'language', value: '\"en,fr\"'}
                    ]
                });
                oSipSessionRegister.register();
            } catch (e) {
                tsk_utils_log_info("===SipEventStack=1:" + e + "</b>");
            }
            break;
        }
        case 'stopping':
        case 'stopped':
        case 'failed_to_start':
        case 'failed_to_stop': {
            var bFailure = (e.type == "failed_to_start") || (e.type == "failed_to_stop");
            oSipStack = null;
            oSipSessionRegister = null;
            oSipSessionCall = null;

            sipRegisted = false;
            isConnecting = false;
            stopRingbackTone();
            stopRingTone();

            isConnecting = false;
            //$("#state").text("离线");

            tsk_utils_log_info(bFailure ? "===SipEventStack Disconnected:" + e.description : "===SipEventStack Disconnected");
            break;
        }

        case 'i_new_call': {

            if (oSipSessionCall) {
                // do not accept the incoming call if we're already 'in call'
                e.newSession.hangup(); // comment this line for multi-line support
            } else {
                oSipSessionCall = e.newSession;
                // start listening for events
                oSipSessionCall.setConfiguration(oConfigCall);

                startRingTone();

                var sRemoteNumber = (oSipSessionCall.getRemoteFriendlyName() || "unknown");
                //txtRegStatus.innerHTML = "<b>" + "来电：" +sRemoteNumber+ "</b>";

                //来电自动接听
                if (autoAnswer == "yes") {
                    sipAnswer();
                } else {
                    $.MsgBox.Confirm("来电通知", "确定接听吗？", function () {
                        sipAnswer();
                        window.openSurvey(openUrl);
                    }, function () {
                        sipHangUp();
                    });
                }

                tsk_utils_log_info("===SipEventStack=Answer|Reject");
                tsk_utils_log_info("===SipEventStack=Incoming call from [" + sRemoteNumber + "]");
            }
            break;
        }

        case 'm_permission_requested': {
            break;
        }
        case 'm_permission_accepted':
        case 'm_permission_refused': {
            if (e.type == "m_permission_refused") {
                tsk_utils_log_info("===SipEventStack=Media stream permission denied");
                oSipSessionCall = null;
                if (oNotifICall) {
                    oNotifICall.cancel();
                    oNotifICall = null;
                }
            }
            break;
        }

        case 'starting':
        default:
            break;
    }
};

//Callback function for SIP sessions (INVITE, REGISTER, MESSAGE...)
//SIPml.Session.Event
function onSipEventSession(e) {
    tsk_utils_log_info("*********onSipEventSession type=" + e.type);
    switch (e.type) {
        case 'connecting':
        case 'connected': {
            var bConnected = (e.type == 'connected');
            sipRegisted = bConnected;
            tsk_utils_log_info("*********onSipEventSession话机注册状态=" + sipRegisted);
            if (e.session == oSipSessionRegister) {
                tsk_utils_log_info("*********onSipEventSession oSipSessionRegister(case 'connecting': case 'connected') if=" + e.description);
                if (sipRegisted) {

                    //alert("内置软电话注册成功");
                    isConnecting = false;
                    //$("#state").text("在线");

                }
            } else if (e.session == oSipSessionCall) {
                if (bConnected) {
                    isConnecting = true;
                    //txtRegStatus.innerHTML = "<b>" + "通话中" + "</b>";
                    stopRingbackTone();
                    stopRingTone();
                }
                tsk_utils_log_info("*********onSipEventSession oSipSessionCall(case 'connecting': case 'connected') else=" + e.description);
                if (SIPml.isWebRtc4AllSupported()) { // IE don't provide stream callback

                    tsk_utils_log_info("*********onSipEventSession isWebRtc4AllSupported=" + SIPml.isWebRtc4AllSupported());
                }
            }
            break;
        } // 'connecting' | 'connected'
        case 'terminating':
        case 'terminated': {
            $("#msg_dial").hide();
            if (e.session == oSipSessionRegister) {
                //uiOnConnectionEvent(false, false);
                oSipSessionCall = null;
                oSipSessionRegister = null;
                //alert("内置软电话注册失败["+e.description+"]");
            } else if (e.session == oSipSessionCall) {
                //uiCallTerminated(e.description);
                oSipSessionCall = null;
                if (oNotifICall) {
                    oNotifICall.cancel();
                    oNotifICall = null;
                }
                stopRingbackTone();
                stopRingTone();
                //txtRegStatus.innerHTML = "<b>" + "挂断" + "</b>";
            }
            tsk_utils_log_info("*********onSipEventSession onSipEventSession(case 'terminating': case 'terminated')=" + e.description);
            break;
        } // 'terminating' | 'terminated'

        case 'm_stream_video_local_added': {
            if (e.session == oSipSessionCall) {
                //uiVideoDisplayEvent(true, true);
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_stream_video_local_added')=" + e.description);
            break;
        }
        case 'm_stream_video_local_removed': {
            if (e.session == oSipSessionCall) {
                //uiVideoDisplayEvent(true, false);
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_stream_video_local_removed')=" + e.description);
            break;
        }
        case 'm_stream_video_remote_added': {
            if (e.session == oSipSessionCall) {
                //uiVideoDisplayEvent(false, true);
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_stream_video_remote_added')=" + e.description);
            break;
        }
        case 'm_stream_video_remote_removed': {
            if (e.session == oSipSessionCall) {
                //uiVideoDisplayEvent(false, false);
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_stream_video_remote_removed')=" + e.description);
            break;
        }

        case 'm_stream_audio_local_added':
        case 'm_stream_audio_local_removed':
        case 'm_stream_audio_remote_added':
        case 'm_stream_audio_remote_removed': {
            break;
        }

        case 'i_ect_new_call': {
            oSipSessionTransferCall = e.session;
            tsk_utils_log_info("*********onSipEventSession(case 'i_ect_new_call')=" + e.description);
            break;
        }

        case 'i_ao_request': {
            if (e.session == oSipSessionCall) {
                var iSipResponseCode = e.getSipResponseCode();
                if (iSipResponseCode == 180 || iSipResponseCode == 183) {
                    startRingbackTone();
                    tsk_utils_log_info("*********onSipEventSession [" + iSipResponseCode + "]Remote ringing......");
                }
            }
            tsk_utils_log_info("*********onSipEventSession(case 'i_ao_request')=" + e.description);
            break;
        }

        case 'm_early_media': {
            if (e.session == oSipSessionCall) {
                stopRingbackTone();
                stopRingTone();
                tsk_utils_log_info("*********onSipEventSession Early media started");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_early_media')=" + e.description);
            break;
        }

        case 'm_local_hold_ok': {
            if (e.session == oSipSessionCall) {
                if (oSipSessionCall.bTransfering) {
                    oSipSessionCall.bTransfering = false;
                    // this.AVSession.TransferCall(this.transferUri);
                }
                tsk_utils_log_info("*********onSipEventSession Resume|Call placed on hold");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_local_hold_ok')=" + e.description);
            break;
        }
        case 'm_local_hold_nok': {
            if (e.session == oSipSessionCall) {
                oSipSessionCall.bTransfering = false;
                tsk_utils_log_info("*********onSipEventSession Hold|Failed to place remote party on hold");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_local_hold_nok')=" + e.description);
            break;
        }
        case 'm_local_resume_ok': {
            if (e.session == oSipSessionCall) {
                oSipSessionCall.bTransfering = false;
                tsk_utils_log_info("*********onSipEventSession Hold|Call taken off hold");
                if (SIPml.isWebRtc4AllSupported()) { // IE don't provide stream callback yet
                    //uiVideoDisplayEvent(false, true);
                    //uiVideoDisplayEvent(true, true);
                    tsk_utils_log_info("*********onSipEventSession isWebRtc4AllSupported=" + SIPml.isWebRtc4AllSupported());
                }
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_local_resume_ok')=" + e.description);
            break;
        }
        case 'm_local_resume_nok': {
            if (e.session == oSipSessionCall) {
                oSipSessionCall.bTransfering = false;
                tsk_utils_log_info("*********onSipEventSession Failed to unhold call");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_local_resume_nok')=" + e.description);
            break;
        }
        case 'm_remote_hold': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Placed on hold by remote party");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_remote_hold')=" + e.description);
            break;
        }
        case 'm_remote_resume': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Taken off hold by remote party");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_remote_resume')=" + e.description);
            break;
        }
        case 'm_bfcp_info': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession BFCP Info:" + e.description);
            }
            tsk_utils_log_info("*********onSipEventSession(case 'm_bfcp_info')=" + e.description);
            break;
        }

        case 'o_ect_trying': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Call transfer in progress......");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'o_ect_trying')=" + e.description);
            break;
        }
        case 'o_ect_accepted': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Call transfer accepted");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'o_ect_accepted')=" + e.description);
            break;
        }
        case 'o_ect_completed':
        case 'i_ect_completed': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Call transfer completed");
                if (oSipSessionTransferCall) {
                    oSipSessionCall = oSipSessionTransferCall;
                }
                oSipSessionTransferCall = null;
            }
            tsk_utils_log_info("*********onSipEventSession(case 'o_ect_completed':case 'i_ect_completed')=" + e.description);
            break;
        }
        case 'o_ect_failed':
        case 'i_ect_failed': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Call transfer failed");
            }
            tsk_utils_log_info("*********onSipEventSession(case 'o_ect_failed':case 'i_ect_failed')=" + e.description);
            break;
        }
        case 'o_ect_notify':
        case 'i_ect_notify': {
            if (e.session == oSipSessionCall) {
                tsk_utils_log_info("*********onSipEventSession Call Transfer:" + e.getSipResponseCode() + " " + e.description);
                if (e.getSipResponseCode() >= 300) {
                    if (oSipSessionCall.bHeld) {
                        oSipSessionCall.resume();
                    }
                }
            }
            tsk_utils_log_info("*********onSipEventSession(case 'o_ect_notify':case 'i_ect_notify')=" + e.description);
            break;
        }
        case 'i_ect_requested': {
            if (e.session == oSipSessionCall) {
                var s_message = "Do you accept call transfer to [" + e.getTransferDestinationFriendlyName() + "]?";//FIXME
                if (confirm(s_message)) {
                    tsk_utils_log_info("*********onSipEventSession Call transfer in progress......");
                    oSipSessionCall.acceptTransfer();
                    break;
                }
                oSipSessionCall.rejectTransfer();
            }
            tsk_utils_log_info("*********onSipEventSession(case 'i_ect_requested')=" + e.description);
            break;
        }
    }
}

function loadSipml() {
    //videoLocal = document.getElementById("video_local");
    //videoRemote = document.getElementById("video_remote");
    audioRemote = document.getElementById("audio_remote");
    oConfigCall = {
        audio_remote: audioRemote,
        video_local: viewVideoLocal,
        video_remote: viewVideoRemote,
        screencast_window_id: 0x00000000, // entire desktop
        bandwidth: {audio: undefined, video: undefined},
        video_size: {minWidth: undefined, minHeight: undefined, maxWidth: undefined, maxHeight: undefined},
        events_listener: {events: '*', listener: onSipEventSession},
        sip_caps: [
            {name: '+g.oma.sip-im'},
            {name: 'language', value: '\"en,fr\"'}
        ]
    };
}
	
	