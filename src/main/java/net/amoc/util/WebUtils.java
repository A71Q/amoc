package net.amoc.util;

import net.amoc.common.web.DoneBean;
import net.amoc.web.Forwards;
import net.amoc.web.SessionKeys;
import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * User: atiq2
 * Date: May 29, 2010
 */


public abstract class WebUtils {
    private static final Logger log = Logger.getLogger(WebUtils.class);

    public static boolean isFormSubmission(HttpServletRequest request) {
        return "POST".equals(request.getMethod());
    }

    public static boolean isGet(HttpServletRequest request) {
        return "GET".equals(request.getMethod());
    }

    public static DoneBean makeErrorDoneBean(HttpServletRequest request, String msgKey) {
        String backLink = ServletRequestUtils.getStringParameter(request, "backLink", null);
        int backType = ServletRequestUtils.getIntParameter(request, "backType", 0);

        UrlGenerator backLinkUrl = new UrlGenerator(request);
        String baseBackLinkUrl = backLink;
        if (baseBackLinkUrl != null && !baseBackLinkUrl.startsWith(request.getServletPath())
                && (baseBackLinkUrl.contains("lister") || baseBackLinkUrl.contains("Lister"))) {
            baseBackLinkUrl = request.getServletPath() + "/" + baseBackLinkUrl;
        }
        backLinkUrl.setBaseUrl(baseBackLinkUrl);

        DoneBean doneBean = new DoneBean();
        doneBean.setType(DoneBean.TYPE_ERROR);

        if (backLink != null && backType != 0) {
            doneBean.setBackType(String.valueOf(backType));
            doneBean.setBackLink(backLinkUrl.getRawUrl());
        } else {
            doneBean.setBackType(DoneBean.BACK_HOME);
            doneBean.setBackLink(Forwards.DASHBOARD);
        }

        StringBuffer url = new StringBuffer(request.getRequestURI());
        String qs = request.getQueryString();
        if (qs != null) {
            url.append("?");
            url.append(qs);
        }

        if (url.toString().contains("formOpenMode=popup") || url.toString().contains("popup=true")) {
            doneBean.setFormOpenMode("popup");
            doneBean.setBackType(DoneBean.CLOSE_THIS_WINDOW);
            doneBean.setBackLink(Forwards.POPUP_CLOSE_ONLY);
        }

        doneBean.setMsgKey(msgKey);
        request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);

        return doneBean;

    }

    public static String getCurrentPageUrl(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        String contextPath = request.getContextPath();
        requestUrl = requestUrl.replaceFirst(contextPath, "");
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestUrl += "?" + queryString;
        }
        return requestUrl;
    }

    public static boolean isButtonPressed(HttpServletRequest request, String buttonName) {
        String value = ServletRequestUtils.getStringParameter(request, buttonName, null);
        return StringUtils.isNotEmpty(value);
    }
}