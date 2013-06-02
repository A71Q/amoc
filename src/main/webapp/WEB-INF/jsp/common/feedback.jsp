<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<html>
<head>
    <title>Feedback</title>
</head>
<body>
<div id="content">
    <form:form method="post">
        <div class="tblHead">
            <h1>Feedback</h1>
        </div>
        <fieldset class="main">
            <fieldset class="summary">
                <table width="100%" cellpadding="1" cellspacing="0">
                    <tr>
                        <th class="label" width="80px" valign="top">
                            <label for="subject">Subject:</label>
                        </th>
                        <td valign="top">
                            <form:input id="subject" path="subject" size="70"/>
                            <form:errors path="subject" cssClass="ferror"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="label" width="80px" valign="top">
                            <label for="text">Summary:</label>
                        </th>
                        <td valign="top">
                            <form:textarea id="text" path="text" cols="80" rows="10"/>
                            <form:errors path="text" cssClass="ferror"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </fieldset>
        <div id="buttons">
            <span class="right">
                <input id="send" name="send" value="Send" type="submit">
            </span>
        </div>
    </form:form>
</div>
</body>
</html>
