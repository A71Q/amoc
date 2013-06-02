<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<decorator:usePage id="currentPage"/>

<html>
<head>
    <title>AMOC - <decorator:title default="Welcome!"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href='<c:url value="/resources/css/amoc.css"/>'/>
    <link rel="stylesheet" href='<c:url value="/resources/jquery-ui-1.9.2/css/redmond/jquery-ui-1.9.2.custom.min.css"/>'/>
    <link rel="stylesheet" href='<c:url value="/resources/jquery-jgrowl-1.2.5/jquery.jgrowl.css"/>'/>

    <script type="text/javascript" src="<c:url value='/resources/jquery-ui-1.9.2/js/jquery-1.8.3.min.js'/>"></script>

    <decorator:head/>
</head>
<body>
<div id="container">
    <div id="headerNew">
            <%@ include file="/WEB-INF/jsp/common/header.jsp" %>
        <div class="clearBoth"></div>
    </div>
    <div id="content">
        <div id="topHeight" style="height:5px; clear:both">
        </div>

        <!-- ~~~ BEGIN BODY ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
        <decorator:body/>
        <!-- ~~~ END BODY ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

        <div id="bottomHeight" style="height:15px; clear:both">
        </div>
    </div>

    <%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</div>
<!-- container -->
</body>
</html>