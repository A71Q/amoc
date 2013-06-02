<%@ tag pageEncoding="UTF-8" isELIgnored="false" body-content="scriptless" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/spring-form.tld" prefix="form" %>

<%@attribute name="path" required="true" %>
<%@attribute name="items" required="true" type="java.lang.Object" %>

<%@attribute name="multiple" type="java.lang.Boolean" %>
<%@attribute name="size" type="java.lang.Integer" %>
<%@attribute name="showPleaseSelect" type="java.lang.Boolean" %>
<%@attribute name="editable" type="java.lang.Boolean" %>
<%--
ListTypePrimitive (True) if labelProp and valueProp is not provided.
ListTypePrimitive (False) If labelProp And valueProp is provided.
Primitive List<Integer, String, Boolean> / Map<Integer,String, Boolean>.
--%>
<%@attribute name="listTypePrimitive" type="java.lang.Boolean" %>
<%@attribute name="itemLabel" type="java.lang.String" %>
<%@attribute name="itemValue" type="java.lang.String" %>
<%@attribute name="onchange" type="java.lang.String"%>

<c:if test="${empty listTypePrimitive}"><c:set var="listTypePrimitive" value="true"/></c:if>
<c:if test="${!empty itemLabel && !empty itemValue}"><c:set var="listTypePrimitive" value="false"/></c:if>

<c:if test="${empty multiple}"><c:set var="multiple" value="false"/></c:if>
<c:if test="${empty size}"><c:set var="size" value="5"/></c:if>
<c:if test="${empty showPleaseSelect}"><c:set var="showPleaseSelect" value="true"/></c:if>
<c:if test="${empty editable}"><c:set var="editable" value="true"/></c:if>
<c:set var="plzSelectLabel" value="-Please Select-"/>
<c:if test="${empty onchange}"><c:set var="onchange" value=""/></c:if>

<c:choose>
    <c:when test="${listTypePrimitive}">
        <c:choose>
            <c:when test="${multiple}">
                <form:select path="${path}" id="${path}_fe" multiple="true" size="${size}" disabled="${!editable}" items="${items}" onchange="${onchange}" htmlEscape="true"/>
            </c:when>
            <c:otherwise>
                <form:select path="${path}" id="${path}_fe" disabled="${!editable}" onchange="${onchange}">
                    <c:if test="${showPleaseSelect}">
                        <form:option value="" label="${plzSelectLabel}"/>
                    </c:if>
                    <form:options items="${items}" htmlEscape="true"/>
                </form:select>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${multiple}">
                <form:select path="${path}" id="${path}_fe" multiple="true" size="${size}" disabled="${!editable}" items="${items}" itemLabel="${itemLabel}" itemValue="${itemValue}" onchange="${onchange}" htmlEscape="true"/>
            </c:when>
            <c:otherwise>
                <form:select path="${path}" id="${path}_fe" disabled="${!editable}" onchange="${onchange}" >
                    <c:if test="${showPleaseSelect}">
                        <form:option value="" label="${plzSelectLabel}"/>
                    </c:if>
                    <form:options items="${items}" itemLabel="${itemLabel}" itemValue="${itemValue}" htmlEscape="true"/>
                </form:select>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<form:errors path="${path}" cssClass="ferror"/>
<jsp:doBody/>
