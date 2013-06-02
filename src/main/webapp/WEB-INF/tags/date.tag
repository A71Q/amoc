<%@ tag pageEncoding="UTF-8" isELIgnored="false" body-content="scriptless" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/spring-form.tld" prefix="form" %>
<%@ taglib uri="/fmt.tld" prefix="fmt" %>

<%@attribute name="path" required="true" %>

<%@attribute name="editable" type="java.lang.Boolean" %>

<c:if test="${empty editable}"><c:set var="editable" value="true"/></c:if>

<c:choose>
    <c:when test="${editable}">
        <form:input id="${path}_fe" path="${path}" size="12" maxlength="10" cssClass="date-ui"/>
        <form:errors path="${path}" cssClass="ferror"/>
    </c:when>
    <c:otherwise>
        <form:input id="${path}_fe" path="${path}" size="12" maxlength="10" disabled="true"/>
    </c:otherwise>
</c:choose>
<jsp:doBody/>
