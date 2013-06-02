<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/sitemesh-decorator" prefix="decorator" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/fmt.tld" prefix="fmt" %>
<decorator:usePage id="currentPage"/>

<html>
<head>
    <title>QMIS - <decorator:title default="Welcome!"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href='<c:url value="/resources/css/qlab.css"/>' type="text/css" media="screen"/>
    <link rel="stylesheet" href='<c:url value="/resources/css/ui-lightness/jquery-ui-1.8.16.custom.css"/>' type="text/css" media="screen"/>
    <link rel="stylesheet" href="<c:url value='/resources/css/jquery.jgrowl.css'/>" type="text/css" media="screen"/>
    <link rel="stylesheet" href="<c:url value='/resources/css/chat.css'/>" type="text/css" media="screen"/>

    <link href='<c:url value="/resources/images/favicon.ico"/>' rel="shortcut icon" type="image/x-icon">

    <script type="text/javascript" src="<c:url value='/resources/jquery-ui-1.9.2/js/jquery-1.8.3.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/jquery-ui-1.9.2/js/jquery-ui-1.9.2.custom.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/jquery-jgrowl-1.2.5/jquery.jgrowl_compressed.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/jquery/chat.js'/>"></script>

    <%String searchInPopupWithLister = currentPage.getProperty("meta.searchInPopupWithLister");%>

    <% if (searchInPopupWithLister != null && "true".equals(searchInPopupWithLister)) { %>
    <%}%>

    <script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery.timeentry.pack.js'/>"></script>


    <decorator:head/>
</head>
<body  <decorator:getProperty property="body.onload" writeEntireProperty="true"/><decorator:getProperty property="body.onkeypress" writeEntireProperty="true"/>>
<div id="container">
    <div id="headerNew">
        <%--<%@ include file="/WEB-INF/jsp/common/header-new.jsp" %>--%>
        <div class="clearBoth"></div>
    </div>
    <div id="content">
        <div id="topHeight" style="height:15px; clear:both">
        </div>

        <!-- ~~~ BEGIN BODY ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
        <decorator:body/>
        <!-- ~~~ END BODY ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

        <div id="bottomHeight" style="height:15px; clear:both">
        </div>
        <div id="searchInPopupDiv" class="flora"></div>
    </div>

    <%--  <c:if test="${formOpenMode!='popup'}">
        <%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
    </c:if>--%>
</div>
<!-- container -->
<iframe class="mifClass" width='0' scrolling='no' height='0' frameborder='0' id='menu_child_iframe' style="display:none;position:absolute;z-index:99" src="javascript:false"></iframe>
</body>
</html>
