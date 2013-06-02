var AMS = {};

AMS.globals = function() {
    return {
        sessionWarnPeriod : function() {
            return 10 * 60 * 1000;
        },
        sessionExpiryPeriod : function() {
            return 15 * 60 * 1000;
        },
        lastRefreshCookieKey : function() {
            return "last_refresh";
        },
        lastAppNotifCookieKey : function() {
            return "last_app_notif";
        },
        popUpTypeMedium: function() {
            return "medium";
        },
        popUpTypeLarge: function() {
            return "large";
        },
        popUpTypeCustom: function() {
            return "custom";
        }
    };
}();

AMS.utils = function () {
    var agt = navigator.userAgent.toLowerCase();
    var is_ie = ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));

    return {
        addEventHandler: function (obj, type, handler) {
            if (obj.addEventListener) {
                obj.addEventListener(type, handler, false);
            } else if (obj.attachEvent) {
                obj.attachEvent("on" + type, handler);
            } else {
                throw new Error("Can not attach event listener, unknown browser type");
            }
        },

        cookie: function(name, value) {
            if (typeof value != 'undefined') {
                document.cookie = name + "=" + encodeURIComponent(value) + "; path=/";
            } else {
                if (document.cookie && document.cookie != '') {
                    var cookies = document.cookie.split(';');
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = cookies[i].replace(/^\s+/, '').replace(/\s+$/, '');
                        if (cookie.substring(0, name.length + 1) == (name + '=')) {
                            return decodeURIComponent(cookie.substring(name.length + 1));
                        }
                    }
                }
                return null;
            }
        },

        log: function(msg) {
            if (!is_ie) {
                if (!window['console']) {
                    if (window['loadFirebugConsole']) {
                        window.loadFirebugConsole();
                    }
                }
                if (window['console']) {
                    console.log("AMS.utils: " + msg);
                }
            }
        },

        isIe: function() {
            return is_ie;
        },

        getTimestamp: function () {
            return (new Date()).getTime();
        }
    };
}();


AMS.page = function () {
    return {
        top: function() {
            return document.body.scrollTop || document.documentElement.scrollTop;
        },

        width: function() {
            return self.innerWidth || (document.documentElement.clientWidth || document.body.clientWidth);
        },

        height: function() {
            return self.innerHeight || (document.documentElement.clientHeight || document.body.clientHeight);
        },

        theight: function() {
            var d = document, b = d.body, e = d.documentElement;
            return Math.max(Math.max(b.scrollHeight, e.scrollHeight), Math.max(b.clientHeight, e.clientHeight));
        },

        twidth: function() {
            var d = document, b = d.body, e = d.documentElement;
            return Math.max(Math.max(b.scrollWidth, e.scrollWidth), Math.max(b.clientWidth, e.clientWidth));
        }
    };
}();

AMS.blocker = function () {
    var overlay, iframe, initialized = false, ie6 = /MSIE 6/i.test(navigator.userAgent);

    function mask() {
        overlay.style.height = AMS.page.theight() + 'px';
        overlay.style.width = '';
        overlay.style.width = AMS.page.twidth() + 'px';

        if (iframe) {
            iframe.style.height = AMS.page.theight() + 'px';
            iframe.style.width = '';
            iframe.style.width = AMS.page.twidth() + 'px';
        }
    }

    function init() {
        if (!initialized) {
            overlay = document.createElement('div');
            overlay.style.position = 'absolute';
            overlay.style.display = 'none';
            overlay.style.top = '0';
            overlay.style.left = '0';
            overlay.style.height = '100%';
            overlay.style.width = '100%';
            overlay.style.background = '#000';
            overlay.style.zIndex = '1500';
            document.body.appendChild(overlay);

            if (ie6) {
                iframe = document.createElement('iframe');
                iframe.style.display = 'none';
                iframe.style.opacity = 0;
                iframe.style.filter = 'alpha(opacity=' + 0 + ')';
                iframe.style.position = 'absolute';
                iframe.style.top = '0';
                iframe.style.left = '0';
                iframe.style.zIndex = '1499';
                iframe.style.border = '0';
                iframe.src = 'javascript:false;';
                document.body.appendChild(iframe);
            } else {
                iframe = null;
            }

            AMS.utils.addEventHandler(window, "resize", mask);
            initialized = true;
        }
    }

    return {
        block: function(opaque) {
            var opacity = opaque ? 0.6 : 0;
            init();
            overlay.style.opacity = opacity;
            overlay.style.filter = 'alpha(opacity=' + opacity * 100 + ')';

            mask();
            overlay.style.display = "block";
            if (iframe) {
                iframe.style.display = "block";
            }
        },

        blockWithExternalParams: function(opacity, bgcolor) {
            init();
            overlay.style.opacity = opacity;
            overlay.style.filter = 'alpha(opacity=' + opacity * 100 + ')';
            overlay.style.opacity = opacity;
            overlay.style.background = bgcolor;

            mask();
            overlay.style.display = "block";
            if (iframe) {
                iframe.style.display = "block";
                iframe.style.opacity = opacity;
                iframe.style.background = bgcolor;
            }
        },

        unblock: function() {
            if (initialized) {
                overlay.style.display = "none";
                if (iframe) iframe.style.display = "none";
            }
        }
    };
}();

AMS.createModal = function (title, message, buttonText, callback) {
    var modal, container, visible = false, initialized = false;

    function init() {
        if (!initialized) {
            if (!buttonText) {
                buttonText = 'OK';
            }

            modal = document.createElement('div');
            modal.style.position = 'absolute';
            modal.style.display = 'none';
            modal.style.width = "auto";
            modal.style.height = "auto";
            modal.style.backgroundImage = 'none';
            modal.style.zIndex = '2000';

            container = document.createElement('div');
            container.style.background = '#fff';
            container.innerHTML = '<div style="padding:3px;width:300px;font-family:verdana,lucida,arial,helvetica,sans-serif;color:#000;">' +
                '<div style="height:18px; background-color:#E0E9EF; padding:2px 5px;font-size:12px;font-weight:bold;text-align:left;">' + title + '</div>' +
                '<div style="min-height:70px;height:auto !important;height:70px;background-color:#FFF;padding:2px 5px;font-size:12px">' + message + '</div>' +
                '<div style="text-align:right;margin-right:5px;margin-bottom:7px;padding-top:5px"><a href="#" id="modalButton" style="padding:4px 10px;background-color:#E0E9EF;text-decoration:none;color:black;font-size:12px;">' + buttonText + '</a></div>' +
                '</div>';
            container.getElementsByTagName("a")[0].onclick = function () {
                if (callback) {
                    callback();
                }
                _hide();
            };

            modal.appendChild(container);
            document.body.appendChild(modal);

            AMS.utils.addEventHandler(window, "resize", pos);
            initialized = true;
        }
    }

    function pos() {
        var t = (AMS.page.height() / 2) - (modal.offsetHeight / 2);
        t = t < 10 ? 10 : t;
        modal.style.top = (t + AMS.page.top()) + 'px';
        modal.style.left = (AMS.page.width() / 2) - (modal.offsetWidth / 2) + 'px';
    }

    function _hide() {
        if (visible) {
            AMS.blocker.unblock();
            modal.style.display = "none";
            visible = false;
        }
    }

    function setFocus() {
        container.getElementsByTagName("a")[0].focus();
    }

    return {
        show: function () {
            init();
            if (!visible) {
                AMS.blocker.block(true);
                modal.style.display = "block";
                pos();
                setFocus();
                visible = true;
            }
        },

        isVisible: function() {
            return visible;
        },

        hide: _hide
    };
};

AMS.closeSearchInPopupDiv = function () {
    $("#searchInPopupDiv").dialog("close");
};
//(domElem, pwidth, searchIn, searchFor, searchForMinLength, callBackToLoadItem, searchDate) {

AMS.searchInPopupDiv = function(domElem, searchIn, searchFor, searchForMinLength, callBackToLoadItem, width, searchDate) {
    AMS.utils.log("searchInPopupDiv-e");
    if (searchIn == "") return;

    if (searchFor.length < searchForMinLength){
        alert("At least " + searchForMinLength + " character must enter to search.");
        return;
    }

    if (searchDate == undefined) {
        searchDate = "";
    }

    if (width == undefined) {
        width = 950;
    }
    $.ajax({
        type        : "GET",
        url         : "/qlab/common/search",
        data        : ({"searchIn":searchIn, "searchFor":searchFor, "callBack":callBackToLoadItem + ";AMS.closeSearchInPopupDiv()", searchDate: searchDate}),
        cache       : false,
        beforeSend : $.blockUI(),
        success     : function(data) {
            $.unblockUI();
            if ("NOT_FOUND" == data) {
                alert("Nothing Found To Display!");
            } else {
                $("#searchInPopupDiv").dialog({width: width,
                    title:searchIn + " List",
                    modal:false,
                    draggable: true,
                    resizable: false,
                    position:["center","center"]
                });
                $("#searchInPopupDiv").html(data);
                function setupTableFilter() {
                    $("#listTable").eq(0).tableFilter({imagePath:'/qlab/resources/images/icons',
                        pageLength:10,
                        enableRowSelector:true,
                        selectEvent: 'mouseover',
                        focusLinksOnSelect: true,
                        selectedClass: 'selected'
                    });
                    $("a.activation:first").focus();
                }
                setTimeout(setupTableFilter, 0);
            }
        },
        error       : function(data) {
            $.unblockUI();
            window.location = "../errorPage.jsp";
        }
    });
};

AMS.popUpLock = false;
//Lock double popUp on double click
AMS.popUp = function(url, name, type, width, height, modal, offset) {
    if (AMS.popUpLock) {
        return;
    }
    AMS.popUpLock = true;

    modal = (modal == null) ? true : modal;
    offset = (offset == null) ? 0 : offset;

    var props = "toolbar=no, menubar=no, copyhistory=no, location=no, titlebar=no, scrollbars=yes, resizable=yes";

    if (AMS.globals.popUpTypeLarge() == type) {
        width = 786;
        height = 500;
    } else if (AMS.globals.popUpTypeMedium() == type) {
        width = AMS.page.width() / 2;
        height = AMS.page.height() / 2;
    } else if (width == null || height == null) {
        return;
    }

    var sw = screen.width;
    var sh = screen.height;
    var x = sw / 2 - width / 2 + offset;
    var y = sh / 2 - height / 2 + offset;
    props = props + ", screenX=" + x + ", screenY=" + y + ", top=" + y + ", left=" + x + ", width=" + width + ", height=" + height;
    var popUpWindow = window.open(url, name, props);
    AMS.popUpLock = false;
};

AMS.refreshParent = function() {
    window.opener.location.href = window.opener.location.href;

    if (window.opener.progressWindow) {
        window.opener.progressWindow.close()
    }
    window.close();
}