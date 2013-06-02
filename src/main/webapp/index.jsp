<%
    StringBuffer fwd = new StringBuffer(request.getContextPath());
    fwd.append("/annotated/dashboard");
    response.sendRedirect(fwd.toString());
%>
