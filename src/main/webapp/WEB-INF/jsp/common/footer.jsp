<%@ taglib uri="/fmt.tld" prefix="fmt" %>
<div id="footer">
    <span id="copyright">
        <i>
            &copy; Copyright AMS, All Rights Reserved.
            <fmt:bundle basename="version">
                Version: <fmt:message key="app.version"/> (<fmt:message key="build.timestamp"/>)
            </fmt:bundle>
        </i>
    </span>
</div>