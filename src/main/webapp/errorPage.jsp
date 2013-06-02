<%@ page isErrorPage="true" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" session="false" %>
<%@ page import="net.amoc.track.RequestTraceFilter" %>
<%@ page import="java.net.SocketException" %>
<%@ page import="net.amoc.util.DebugUtils" %>
<%@ page import="net.amoc.util.Utils" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.log4j.Logger" %>
<html>
<head>
    <title>Error</title>

    <link href='<c:url value="/resources/images/favicon.ico"/>' rel="shortcut icon" type="image/x-icon">
    <style type="text/css">
        #apperrmsg {
            margin: auto;
            padding: 25px;
            height: auto;
            width: 300px;
            left: 35%;
            background-color: #ffffdf;
            border: 1px solid #d7d7d7;
            text-align: left;
            display: table;
        }

        #apperrmsg h1 {
            padding: 0;
            display: block;
            font-family: Georgia, "Times New Roman", Times, serif;
            font-size: 20px;
            font-weight: bold;
            margin: 0;
            white-space: nowrap;
            color: #FF0000;
        }

        #apperrmsg p {
            white-space: nowrap;
            color: #000000;
            font-family: arial, verdana, lucida, helvetica, sans-serif;
            font-size: 13px;
        }

    </style>
</head>

<body>


<%! private static final Logger log = Logger.getLogger("org.qunatum.errorPage"); %>
<%
    if (exception instanceof SocketException || exception.getCause() instanceof SocketException) {
        log.debug("Eating up SocketException", exception);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return;
    }

    StringBuilder buffer = new StringBuilder("Error in view layer\n");

    try {
        StringBuilder url = new StringBuilder(request.getRequestURI());
        String qs = request.getQueryString();
        if (qs != null) {
            url.append("?");
            url.append(qs);
        }

        String method = request.getMethod();
        String m = "NULL";
        if (method != null) {
            m = method.substring(0, 1);
        }

        DebugUtils.printGlobalExceptionDebugInfo(request);

        buffer.append("\nURL=[");
        buffer.append(url.toString());
        buffer.append("], M=");
        buffer.append(m);
        buffer.append("\n");

        if (request.getSession() != null) {
            buffer.append("\nRequest Trace:\n");
            RequestTraceFilter.printToBuffer(request.getSession(), "\t", buffer);
        }

        buffer.append("\nRequest Headers:\n");
        DebugUtils.dumpAllHeaders(request, buffer);
        if ("POST".equals(request.getMethod())) {
            buffer.append("\nPost Parameters:\n");
            DebugUtils.dumpPostParameters(request, buffer);
        }

        buffer.append("\nServer Name: ").append(Utils.getHostName());
    } catch (Exception e) {
        buffer.append("\nJSP Error Page Exception:\n");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        buffer.append(sw.toString());
    }

    buffer.append("\nException:\n");
    log.error(buffer.toString(), exception);
%>

<div style="text-align: center;">
    <div id="apperrmsg">
        <h1>Error</h1>

        <p>There has been an error in processing your request. QLAB Support Team has been automatically notified.
            <br/><br/><a href="<%=response.encodeRedirectURL(request.getContextPath() + "/annotated/dashboard")%>">Back to
                Home</a></p>
    </div>
</div>


</body>
</html>