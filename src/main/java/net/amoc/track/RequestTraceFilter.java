package net.amoc.track;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class RequestTraceFilter implements Filter {
    public static final int REQ_QUEUE_SIZE = 20;

    private static final String REQ_TRACE_BASE = "net.amoc.req.trace";
    private static final String REQ_TRACE_LAST_KEY = "net.amoc.req.last";

    private String[] excludeList;

    public void init(FilterConfig config) throws ServletException {
        String excludes = config.getInitParameter("excludes");
        if (excludes != null) {
            String[] list = excludes.split(",");
            excludeList = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                excludeList[i] = list[i].trim();
            }
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (excludeList != null) {
            int cpLen = request.getContextPath().length();
            String relativeUri = request.getRequestURI().substring(cpLen);
            for (String pattern : excludeList) {
                if (relativeUri.startsWith(pattern)) {
                    chain.doFilter(request, servletResponse);
                    return;
                }
            }
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            chain.doFilter(request, servletResponse);
            return;
        }

        RequestInfo info = new RequestInfo();
        info.begin(request);
        String infoKey;
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (session) {
            Integer last = (Integer) session.getAttribute(REQ_TRACE_LAST_KEY);
            int cur = (last == null) ? 0 : (last + 1) % REQ_QUEUE_SIZE;
            infoKey = new StringBuilder(REQ_TRACE_BASE).append(".").append(cur).toString();
            session.setAttribute(infoKey, info);
            session.setAttribute(REQ_TRACE_LAST_KEY, cur);
        }

        chain.doFilter(request, servletResponse);

        info.end();
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (session) {
            if (request.isRequestedSessionIdValid()) {
                session.setAttribute(infoKey, info);
            }
        }
    }

    public void destroy() {
    }

    public static void printToBuffer(HttpSession session, String prefix, StringBuilder buffer) {
        if (session == null) {
            return;
        }

        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (session) {
            Integer start = (Integer) session.getAttribute(REQ_TRACE_LAST_KEY);
            if (start == null) {
                return;
            }

            int cur = start;
            while (true) {
                String infoKey = new StringBuilder(REQ_TRACE_BASE).append(".").append(cur).toString();
                RequestInfo info = (RequestInfo) session.getAttribute(infoKey);
                if (info == null) {
                    break;
                }

                buffer.append(prefix);
                buffer.append(info.toString());
                buffer.append("\n");

                cur = (cur + REQ_QUEUE_SIZE - 1) % REQ_QUEUE_SIZE;
                if (cur == start) {
                    break;
                }
            }
        }
    }
}