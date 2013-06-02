<%@ tag pageEncoding="UTF-8" isELIgnored="false" body-content="scriptless" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/spring-form.tld" prefix="form" %>

<%@attribute name="path" required="true" %>

<%@attribute name="size" type="java.lang.Integer" %>
<%@attribute name="maxLength" type="java.lang.Integer" %>
<%@attribute name="editable" type="java.lang.Boolean" %>

<c:if test="${empty size}"><c:set var="size" value="20"/></c:if>
<c:if test="${empty maxLength}"><c:set var="maxLength" value="64"/></c:if>
<c:if test="${empty editable}"><c:set var="editable" value="true"/></c:if>

<form:input id="${path}_fe" path="${path}" size="${size}" maxlength="${maxLength}" htmlEscape="true" disabled="${!editable}"/>
<form:errors path="${path}" cssClass="ferror"/>
<jsp:doBody/>
