<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<html>
<head>
    <s:htmlEscape defaultHtmlEscape="true"/>
    <c:if test="${!empty doneBean.formOpenMode}">
        <c:if test="${doneBean.formOpenMode == 'popup'}">
            <meta name="decorator" content="popup-decorator">
        </c:if>
    </c:if>
    <title><fmt:message key="success.message"/></title>
    <meta name="ignoreQuickLink" content="true">
    <style type="text/css">
        .quick-menu {
            padding-top: 1px;
            text-align: center;
            border-top: 4px #065184 solid;
            border-bottom: 4px #065184 solid;
        }
    </style>

    <script type="text/javascript">
        function printForm() {
            var wind = window.open();
            $.get("${doneBean.commonLinkMap['print.form']}",function(data) {
                wind.location = "";
                wind.document.write(data);
                wind.print();
                wind.close();
            });
        }
    </script>
</head>

<body>

<br>
<br>

<div style="text-align: center;">
    <div
            <c:choose>
                <c:when test="${doneBean.type == 1 || doneBean.type == 3}">id="greenmsg" </c:when>
                <c:when test="${doneBean.type == 4}">id="orangemsg" </c:when>
                <c:otherwise>id="redmsg"</c:otherwise>
            </c:choose>
            >
        <h1 class="donemsg">
            <c:if test="${!empty doneBean.message}">
                ${doneBean.message}
            </c:if>
            <c:if test="${!empty doneBean.msgKey}">
                <fmt:message key="${doneBean.msgKey}"/>.
            </c:if>
        </h1>
    </div>
    <c:if test="${doneBean.backLink != null && doneBean.backType != 1}">
        <br/>
        <div>
            <c:choose>
                <c:when test="${doneBean.backType == 12}">
                    <a href="/common/logout"><fmt:message key="p.logout"/></a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='${doneBean.backLink}'/>">
                        <fmt:message key="${doneBean.backCaptionKey}"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
    <c:if test="${doneBean.commonLinkMap != null}">
        <br/>
        <div>
            <c:forEach items="${doneBean.commonLinkMap}" var="item">
                <c:if test="${item.key != 'print.form' and item.key != 'print.param'}">
                    <a href="<c:url value='${item.value}'/>">
                        <s:message code='${item.key}'/>
                    </a>
                    <br/>
                    <br/>
                </c:if>
                <c:if test="${item.key eq 'print.form'}">
                    <a href="#" onclick="printForm()">
                        <s:message code='${item.key}'/> &nbsp;${doneBean.commonLinkMap['print.param']}
                    </a>
                </c:if>
            </c:forEach>

        </div>
    </c:if>
</div>

</body>
</html>
