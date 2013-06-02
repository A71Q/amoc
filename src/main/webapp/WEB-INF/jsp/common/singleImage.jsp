<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="/c.tld" prefix="c" %>

<html>
<head>
    <title>Process Image Upload</title>
    <script type="text/javascript" src="<c:url value='/resources/jquery-ui-1.9.2/js/jquery-1.8.3.min.js'/>"></script>


    <script language="JavaScript">

        $(document).ready(function() {
            addImage();
        });

        function addImage() {
            window.close();
            window.opener.document.forms[0].elements["<c:out value="${singleImageId}" />"].value =<c:out value="${tmpImgId}" />;

        <c:set var="thumbImgContent" >
        <c:url value="/annotated/image/imageContent">
        <c:param name="imgId"   value="${tmpImgId}"/>
        <c:param name="imgType" value="THUMB"/>
        <c:param name="tmpImg" value="true"/>
        </c:url>
        </c:set>

        <c:set var="origImgContent" >
        <c:url value="/annotated/image/imageContent">
        <c:param name="imgId"   value="${tmpImgId}"/>
        <c:param name="imgType" value="ORIG"/>
        </c:url>
        </c:set>

        <c:if test="${imageTypeAfterUpload == 'ORIG'}">
            window.opener.document.getElementById("<c:out value="${singleImageId}" />_img").innerHTML = "<a href='javascript:largePopup(\"<c:out value='${origImgContent}' />" + "\",\"uploadImage\", -10);'><img src='<c:out value="${origImgContent}" />'></a>";
        </c:if>
        <c:if test="${imageTypeAfterUpload == 'THUMB'}">
            window.opener.document.getElementById("<c:out value="${singleImageId}" />_img").innerHTML = "<a href='javascript:largePopup(\"<c:out value='${origImgContent}' />" + "\",\"uploadImage\", -10);'><img src='<c:out value="${thumbImgContent}" />'></a>";
        </c:if>
        }
    </script>

</head>

<body>
Processing Image Upload ... ... ...
</body>
</html>
