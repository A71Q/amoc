<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<page:apply-decorator name="default-decorator">
    <html>
    <style>
        * {
            margin: 0;
            padding: 0
        }

        body {
            background: #333
        }

        div#x {
            height: 240px;
            left: 50%;
            margin: -160px 0 0 -400px;
            position: fixed;
            top: 50%;
            width: 800px;
        }

        div#y {
            height: 240px;
            margin: 0 auto;
            padding: 104px 0 0 193px;
            background: #ffffff;
        }

        h1 {
            color: #333;
            font: 800 22px Verdana, Helvetica, Arial;
            margin-bottom: 10px
        }

        p {
            color: #333;
            font: 100 14px Verdana, Helvetica, Arial;
            line-height: 17px;
            width: 450px;
        }
    </style>
    <head>
        <title>
            AMOC :: <s:message code="title.incorrect.url"/>
        </title>
        <%--<link href='<c:url value="/resources/images/favicon.ico"/>' rel="shortcut icon" type="image/x-icon">--%>
    </head>

    <body>
        <div id="x">
            <div id="y">
                <h1><s:message code="title.incorrect.url"/></h1>
                <p><s:message code="errors.incorrect.url"/></p>
            </div>
        </div>
    </body>
    </html>
</page:apply-decorator>