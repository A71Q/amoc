package net.amoc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlGenerator {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String baseUrl;
    private Map<String, String> params;

    public UrlGenerator() {
    }

    /**
     * Creates a UrlGenerator
     *
     * @param request
     */
    public UrlGenerator(HttpServletRequest request) {

        this.request = request;
        this.response = null;
    }

    public UrlGenerator(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;
        this.response = response;
    }

    public UrlGenerator(HttpServletRequest request, HttpServletResponse response, String baseurl) {

        this(request, response);
        this.baseUrl = baseurl;
    }

    public UrlGenerator addParam(String paramName, String paramValue) {

        if (params == null)
            params = new LinkedHashMap<String, String>();

        params.put(paramName, paramValue);

        return this;
    }

    public UrlGenerator addParam(String paramName, Object paramValue) {

        return addParam(paramName, paramValue.toString());
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * This method returns the the baseUrl with the parameters properly
     * encoded and appended. The context path is ignored.
     *
     * @return The url
     */
    public String getRawUrl() {

        return appendParameter(baseUrl);
    }

    /**
     * This method returns the the baseUrl with the parameters properly
     * encoded and appended. The context path is appened at the begining
     * of the baseUrl.
     *
     * @return The url
     */
    public String getUrlWithContext() {

        return encodeRedirectURL(appendContextPath(appendParameter(baseUrl)));
    }

    /**
     * Adds session id in url when cookie is disabled in browser.
     * If response is null url is returned as given. Giving null response is
     * not generally intended but to be only used in Controller layer if {@link HttpServletResponse}
     * is not available. The user must note to encodeRedirectURL once in the view layer.
     *
     * @param url
     * @return
     */
    public String encodeRedirectURL(String url) {
        return (response == null) ? url : response.encodeRedirectURL(url);
    }

    protected String appendParameter(String url) {

        String paramValue;

        if (params == null)
            return url;

        boolean first = url.contains("?") ? false : true;
        if (first)
            url += "?";
        for (String paramName : params.keySet()) {
            try {
                paramValue = params.get(paramName);
                if (paramValue == null) continue;

                if (!first)
                    url += "&";

                url += paramName + "=" + URLEncoder.encode(paramValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // we will never be here.
            }
            first = false;
        }

        return url;
    }

    protected String appendContextPath(String url) {

        if (url.startsWith("/"))
            return request.getContextPath() + url;
        return url;
    }
}
