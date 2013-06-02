/*

Copyright (c) 2009 Anant Garg (anantgarg.com | inscripts.com)

This script may be used for non-commercial purposes only. For any
commercial purposes, please contact the author at 
anant.garg@inscripts.com

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

*/

var windowFocus = true;
var userId;
var userName;
var minChatHeartbeat = 1000 * 5;
var maxChatHeartbeat = 1000 * 30;
var chatHeartbeatTime = minChatHeartbeat;
var originalTitle;
var blinkOrder = 0;

var chatBoxFocus = new Array();
var newMessages = new Array();
var newMessagesWin = new Array();
var chatBoxes = new Array();

$(document).ready(function() {
    originalTitle = document.title;
    startChatSession();

    $([window, document]).blur(function() {
        windowFocus = false;
    }).focus(function() {
        windowFocus = true;
        document.title = originalTitle;
    });
});

function restructureChatBoxes() {
    align = 0;
    for (var x = 0; x < chatBoxes.length; x++) {
        chatBoxId = chatBoxes[x];

        if ($("#chatbox_" + chatBoxId).css('display') != 'none') {
            if (align == 0) {
                $("#chatbox_" + chatBoxId).css('right', '20px');
            } else {
                width = (align) * (225 + 7) + 20;
                $("#chatbox_" + chatBoxId).css('right', width + 'px');
            }
            align++;
        }
    }
}

function chatWith(chatWithUserName, chatWithUserId) {
    createChatBox(chatWithUserName, chatWithUserId);
    $("#chatbox_" + chatWithUserId + " .chatboxtextarea").focus();
}

function createChatBox(chatWithUserName, chatWithUserId, minimizeChatBox) {
    if ($("#chatbox_" + chatWithUserId).length > 0) {
        if ($("#chatbox_" + chatWithUserId).css('display') == 'none') {
            $("#chatbox_" + chatWithUserId).css('display', 'block');
            restructureChatBoxes();
        }
        $("#chatbox_" + chatWithUserId + " .chatboxtextarea").focus();
        return;
    }

    $(" <div />").attr("id", "chatbox_" + chatWithUserId)
            .addClass("chatbox")
            .html(
            '<div class="chatboxhead">' +
                '<div class="chatboxtitle">' + chatWithUserName + '</div>' +
                '<div class="chatboxoptions">' +
                    '<a href="javascript:void(0)" onclick="javascript:toggleChatBoxGrowth(\'' + chatWithUserId + '\')">-</a> ' +
                    '<a href="javascript:void(0)" onclick="javascript:closeChatBox(\'' + chatWithUserId + '\')">X</a>' +
                '</div>' +
                '<br clear="all"/>' +
                '</div>' +
                '<div class="chatboxcontent"></div>' +
                '<div class="chatboxinput">' +
                    '<textarea class="chatboxtextarea" onkeydown="javascript:return checkChatBoxInputKey(event,this,\'' + chatWithUserId + '\');"></textarea>' +
                '</div>')
            .appendTo($("body"));

    $("#chatbox_" + chatWithUserId).css('bottom', '0px');

    chatBoxeslength = 0;

    for (var x = 0; x < chatBoxes.length; x++) {
        if ($("#chatbox_" + chatBoxes[x]).css('display') != 'none') {
            chatBoxeslength++;
        }
    }

    if (chatBoxeslength == 0) {
        $("#chatbox_" + chatWithUserId).css('right', '20px');
    } else {
        width = (chatBoxeslength) * (225 + 7) + 20;
        $("#chatbox_" + chatWithUserId).css('right', width + 'px');
    }

    chatBoxes.push(chatWithUserId);

    if (minimizeChatBox == 1) {
        minimizedChatBoxes = new Array();

        if ($.cookie('chatbox_minimized')) {
            minimizedChatBoxes = $.cookie('chatbox_minimized').split(/\|/);
        }
        minimize = 0;
        for (j = 0; j < minimizedChatBoxes.length; j++) {
            if (minimizedChatBoxes[j] == chatWithUserId) {
                minimize = 1;
            }
        }

        if (minimize == 1) {
            $('#chatbox_' + chatWithUserId + ' .chatboxcontent').css('display', 'none');
            $('#chatbox_' + chatWithUserId + ' .chatboxinput').css('display', 'none');
        }
    }

    chatBoxFocus[chatWithUserId] = false;

    $("#chatbox_" + chatWithUserId + " .chatboxtextarea").blur(function() {
        chatBoxFocus[chatWithUserId] = false;
        $("#chatbox_" + chatWithUserId + " .chatboxtextarea").removeClass('chatboxtextareaselected');
    }).focus(function() {
        chatBoxFocus[chatWithUserId] = true;
        newMessages[chatWithUserId] = false;
        $('#chatbox_' + chatWithUserId + ' .chatboxhead').removeClass('chatboxblink');
        $("#chatbox_" + chatWithUserId + " .chatboxtextarea").addClass('chatboxtextareaselected');
    });

    $("#chatbox_" + chatWithUserId).click(function() {
        if ($('#chatbox_' + chatWithUserId + ' .chatboxcontent').css('display') != 'none') {
            $("#chatbox_" + chatWithUserId + " .chatboxtextarea").focus();
        }
    });

    $("#chatbox_" + chatWithUserId).show();
}


function chatHeartBeat(){

    var itemsfound = 0;

    if (!windowFocus) {

        var blinkNumber = 0;
        var titleChanged = 0;
        for (var x = 0; x < newMessagesWin.length; x++) {
            if (newMessagesWin[x]) {
                ++blinkNumber;
                if (blinkNumber >= blinkOrder) {
                    document.title = x + ' says...';
                    titleChanged = 1;
                    break;
                }
            }
        }

        if (titleChanged == 0) {
            document.title = originalTitle;
            blinkOrder = 0;
        } else {
            ++blinkOrder;
        }

    } else {
        for (x = 0; x < newMessagesWin.length; x++) {
            newMessagesWin[x] = false;
        }
    }

    for (x = 0; x < newMessagesWin.length; x++) {
        if (newMessages[x]) {
            if (!chatBoxFocus[x]) {
                //FIXME: add toggle all or none policy, otherwise it looks funny
                $('#chatbox_' + x + ' .chatboxhead').toggleClass('chatboxblink');
            }
        }
    }

    if (!isChatDisabledForSession()) {
    $.ajax({
        url: "/qlab/annotated/qtext/heartBeat",
        cache: false,
        dataType: "json",
        success: function(data) {
            $.each(data.items, function(i, item) {
                if (item) { // fix strange ie bug
                    var chatWithUserName = item.f;
                    var chatWithUserId = item.fid;

                    if ($("#chatbox_" + chatWithUserId).length <= 0) {
                        createChatBox(chatWithUserName, chatWithUserId, 0);
                    }
                    if ($("#chatbox_" + chatWithUserId).css('display') == 'none') {
                        $("#chatbox_" + chatWithUserId).css('display', 'block');
                        restructureChatBoxes();
                    }

                    newMessages[chatWithUserId] = true;
                    newMessagesWin[item.fNick] = true;
                    $("#chatbox_" + chatWithUserId + " .chatboxcontent").append('<div class="chatboxmessage"><span class="chatboxmessagefrom">' + item.fNick + ' (' + item.t + '):&nbsp;&nbsp;</span><span class="chatboxmessagecontent">' + item.m + '</span></div>');

                    $("#chatbox_" + chatWithUserId + " .chatboxcontent").scrollTop($("#chatbox_" + chatWithUserId + " .chatboxcontent")[0].scrollHeight);
                    itemsfound += 1;
                }
            });
//            console.info(chatHeartbeatTime + " " + new Date());
            if (itemsfound > 0) {
                chatHeartbeatTime = minChatHeartbeat;
            } else if (chatHeartbeatTime < maxChatHeartbeat) {
                chatHeartbeatTime += minChatHeartbeat;
            }

            setTimeout('chatHeartBeat();', chatHeartbeatTime);
        }});
    }
}

function closeChatBox(chatWithUserId) {
    $('#chatbox_' + chatWithUserId).css('display', 'none');
    restructureChatBoxes();
}

function toggleChatBoxGrowth(chatWithUserId) {
    if ($('#chatbox_' + chatWithUserId + ' .chatboxcontent').css('display') == 'none') {

        var minimizedChatBoxes = new Array();

        if ($.cookie('chatbox_minimized')) {
            minimizedChatBoxes = $.cookie('chatbox_minimized').split(/\|/);
        }

        var newCookie = '';

        for (i = 0; i < minimizedChatBoxes.length; i++) {
            if (minimizedChatBoxes[i] != chatWithUserId) {
                newCookie += chatWithUserId + '|';
            }
        }

        newCookie = newCookie.slice(0, -1)


        $.cookie('chatbox_minimized', newCookie);
        $('#chatbox_' + chatWithUserId + ' .chatboxcontent').css('display', 'block');
        $('#chatbox_' + chatWithUserId + ' .chatboxinput').css('display', 'block');
        $("#chatbox_" + chatWithUserId + " .chatboxcontent").scrollTop($("#chatbox_" + chatWithUserId + " .chatboxcontent")[0].scrollHeight);
    } else {

        var newCookie = chatWithUserId;

        if ($.cookie('chatbox_minimized')) {
            newCookie += '|' + $.cookie('chatbox_minimized');
        }


        $.cookie('chatbox_minimized', newCookie);
        $('#chatbox_' + chatWithUserId + ' .chatboxcontent').css('display', 'none');
        $('#chatbox_' + chatWithUserId + ' .chatboxinput').css('display', 'none');
    }

}

function checkChatBoxInputKey(event, chatBoxTextArea, chatWithUserId) {

    if (event.keyCode == 13 && event.shiftKey == 0) {
        var message = $(chatBoxTextArea).val();
        message = message.replace(/^\s+|\s+$/g, "");
        $(chatBoxTextArea).val('');
        $(chatBoxTextArea).focus();
        $(chatBoxTextArea).css('height', '44px');
        if (message.length > 0) {
            $.post("/qlab/annotated/qtext/send", {to: chatWithUserId, message: message}, function(data) {
                message = message.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\"/g, "&quot;");
                $("#chatbox_" + chatWithUserId + " .chatboxcontent").append('<div class="chatboxmessage"><span class="chatboxmessagefrom">me:&nbsp;&nbsp;</span><span class="chatboxmessagecontent">' + message + '</span></div>');
                $("#chatbox_" + chatWithUserId + " .chatboxcontent").scrollTop($("#chatbox_" + chatWithUserId + " .chatboxcontent")[0].scrollHeight);
            });
        }
        chatHeartbeatTime = minChatHeartbeat;

        return false;
    }

    var adjustedHeight = chatBoxTextArea.clientHeight;
    var maxHeight = 94;

    if (maxHeight > adjustedHeight) {
        adjustedHeight = Math.max(chatBoxTextArea.scrollHeight, adjustedHeight);
        if (maxHeight)
            adjustedHeight = Math.min(maxHeight, adjustedHeight);
        if (adjustedHeight > chatBoxTextArea.clientHeight)
            $(chatBoxTextArea).css('height', adjustedHeight + 8 + 'px');
    } else {
        $(chatBoxTextArea).css('overflow', 'auto');
    }

}

function startChatSession() {
    $.ajax({
        url: "/qlab/annotated/qtext/startSession",
        cache: false,
        dataType: "json",
        success: function(data) {

            userName = data.userName;
            userId = data.userId;

            $.each(data.items, function(i, item) {
                if (item) { // fix strange ie bug

                    var chatWithUserId = item.fid;
                    var chatWithUserName = item.f;

                    if ($("#chatbox_" + chatWithUserId).length <= 0) {
                        createChatBox(chatWithUserName, chatWithUserId, 1);
                    }


                    $("#chatbox_" + chatWithUserId + " .chatboxcontent")
                            .append('<div class="chatboxmessage"><span class="chatboxmessagefrom">' + item.fNick + ' (' + item.t + '):&nbsp;&nbsp;</span><span class="chatboxmessagecontent">' + item.m + '</span></div>');
                }
            });

            for (i = 0; i < chatBoxes.length; i++) {
                chatWithUserId = chatBoxes[i];
                $("#chatbox_" + chatWithUserId + " .chatboxcontent").scrollTop($("#chatbox_" + chatWithUserId + " .chatboxcontent")[0].scrollHeight);
                setTimeout('$("#chatbox_" + chatWithUserId + " .chatboxcontent").scrollTop($("#chatbox_" + chatWithUserId + " .chatboxcontent")[0].scrollHeight);', 100); // yet another strange ie bug
            }

            setTimeout('chatHeartBeat();', chatHeartbeatTime);

        }});
}

/**
 * Cookie plugin
 *
 * Copyright (c) 2006 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

function isChatDisabledForSession() {
        var now = AMS.utils.getTimestamp();
        var last_refresh_time = AMS.utils.cookie(AMS.globals.lastRefreshCookieKey());
        var interval_since_refresh = now - last_refresh_time;
        return interval_since_refresh >= AMS.globals.sessionWarnPeriod();
}