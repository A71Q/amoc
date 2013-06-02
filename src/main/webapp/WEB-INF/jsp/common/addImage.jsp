<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/fmt.tld" prefix="fmt" %>

<html>
<head>
    <meta name="decorator" content="popup-decorator">
</head>
<body onLoad="init">

<div id="sections" align="center" style="width: 60%; margin-left: 40px;">
    <div class="tblHead"><h1><fmt:message key="label.imageUpload"/></h1></div>
    <form name='imgFrm' action="upload" method="post" encType="multipart/form-data">
        <div>
            <input type="hidden" name="singleImageId" value="<c:out value='${singleImageId}' />">
            <input type="hidden" name="imageDimensionWidthMax" value="<c:out value='${imageDimensionWidthMax}' />">
            <input type="hidden" name="imageDimensionHeightMax" value="<c:out value='${imageDimensionHeightMax}' />">
            <input type="hidden" name="imageTypeAfterUpload" value="<c:out value='${imageTypeAfterUpload}' />">

            <br/>
            <table class="form" width="100%" cellpadding="1" cellspacing="0">
            <%--<table border="0" cellpadding="5" cellspacing="0" class="form" cellspacing="0">--%>

                <tr>
                    <td colspan="2" align='right'>&nbsp;</td>
                </tr>
                <tr>
                    <th class="form" align="left" valign='top' id="image_id">
                        <fmt:message key="prompt.name"/>
                    </th>
                    <td id="image_fe">
                        <input type="file" name="image"><br/>
                        <span class='ferror'>
                        <c:if test="${nameEmpty}"><fmt:message key="required"/><br/></c:if>
                        <c:if test="${invalidFormat}"><fmt:message key="error.image.size"/><br/></c:if>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align='right'>&nbsp;</td>
                </tr>
            </table>
            <br/>

            <div id="buttons">
				<span class="left">
				    <input type="button" name="btnCancel"  value="<fmt:message key='button.cancel'/>" onClick="window.close();">
				</span>
				<span class="right">
				    <input type="submit" name='uploadButton' value="<fmt:message key='label.imageUpload' />">
				</span>
            </div>
        </div>
    </form>
</div>
<div id="msg" style="text-align:center;padding-top:100px">
</div>
<script>
    function startProcess() {
        document.getElementById("sections").style.display = "none";
        document.getElementById("msg").innerHTML = "<h1><fmt:message key='label.uploading' /> ...</h1>";
    }
    function init() {
        document.getElementById("sections").style.visibility = "visible";
        document.getElementById("msg").innerHTML = "&nbsp;";
    }
</script>
</body>
</html>
