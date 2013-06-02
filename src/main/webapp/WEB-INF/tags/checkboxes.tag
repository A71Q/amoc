<%@ tag pageEncoding="UTF-8" isELIgnored="false" body-content="scriptless" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/spring-form.tld" prefix="form" %>

<%@attribute name="path" required="true" %>
<%@attribute name="items" required="true" type="java.lang.Object" %>

<%@attribute name="editable" type="java.lang.Boolean" %>
<%--
ListTypePrimitive (True) if labelProp and valueProp is not provided.
ListTypePrimitive (False) If labelProp And valueProp is provided.
Primitive List<Integer, String, Boolean> / Map<Integer,String, Boolean>.
--%>
<%@attribute name="listTypePrimitive" type="java.lang.Boolean" %>
<%@attribute name="itemLabel" type="java.lang.String" %>
<%@attribute name="itemValue" type="java.lang.String" %>

<c:if test="${empty listTypePrimitive}"><c:set var="listTypePrimitive" value="true"/></c:if>
<c:if test="${!empty itemLabel && !empty itemValue}"><c:set var="listTypePrimitive" value="false"/></c:if>

<c:if test="${empty editable}"><c:set var="editable" value="true"/></c:if>

<c:choose>
    <c:when test="${listTypePrimitive}">
            <form:checkboxes path="${path}" id="${path}_fe" disabled="${!editable}" items="${items}" htmlEscape="true"/>
    </c:when>
    <c:otherwise>
            <form:checkboxes path="${path}" id="${path}_fe" disabled="${!editable}" items="${item}" itemLabel="${itemLabel}" itemValue="${itemValue}" htmlEscape="true"/>
    </c:otherwise>
</c:choose>
<form:errors path="${path}" cssClass="ferror"/>
<jsp:doBody/>
