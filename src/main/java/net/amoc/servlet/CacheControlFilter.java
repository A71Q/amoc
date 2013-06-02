package net.amoc.servlet;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Adds a Cache-Control header to the response.  This filter is
 * intended to be used for suggesting browser caching of static css
 * and image files.  It will add a Cache-Control header with a max age
 * to all pages filtered through it.
 * <p/>
 * At present, the caching time is hard coded to 3 hours.
 */
public class CacheControlFilter implements Filter {

    private static final Logger log = Logger.getLogger(CacheControlFilter.class);
    private static final int DEFAULT_MAX_AGE = 3 * 3600;

    private String value = null;

    public void init(FilterConfig config) {
        String param = config.getInitParameter("max-age");
        int maxAge;
        if (param == null) {
            maxAge = DEFAULT_MAX_AGE;
        } else {
            maxAge = Integer.parseInt(param);
        }
        value = "max-age=" + maxAge;
        log.debug("Iniitializing CacheControlFilter, Cache-Control value=[" + value + "]");
    }

    public void destroy() {
        log.debug("Destroying CacheControlFilter");
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        if (response instanceof HttpServletResponse) {
            log.debug("uri=[" + ((HttpServletRequest) request).getRequestURI() + "]");
            ((HttpServletResponse) response).addHeader("Cache-Control", value);
        }
    }
}
