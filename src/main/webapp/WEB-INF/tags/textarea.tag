<%@ tag pageEncoding="UTF-8" isELIgnored="false" body-content="scriptless" %>
<%@ taglib uri="/c.tld" prefix="c" %>
<%@ taglib uri="/spring-form.tld" prefix="form" %>

<%@attribute name="path" required="true" %>

<%@attribute name="rows" type="java.lang.Integer" %>
<%@attribute name="cols" type="java.lang.Integer" %>
<%@attribute name="editable" type="java.lang.Boolean" %>

<c:if test="${empty rows}"><c:set var="rows" value="3"/></c:if>
<c:if test="${empty cols}"><c:set var="cols" value="70"/></c:if>
<c:if test="${empty editable}"><c:set var="editable" value="true"/></c:if>

<form:textarea id="${path}_fe" path="${path}" rows="${rows}" cols="${cols}" htmlEscape="true" disabled="${!editable}"/>
<form:errors path="${path}" cssClass="ferror"/>
<jsp:doBody/>
