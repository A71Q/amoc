package net.amoc.common.web;

import net.amoc.util.DebugUtils;
import net.amoc.util.WebUtils;
import net.amoc.exceptions.*;
import net.amoc.web.Forwards;
import net.amoc.web.SessionKeys;
import net.amoc.track.RequestTraceFilter;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static final Logger log = Logger.getLogger(GlobalExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        StringBuffer url = new StringBuffer(request.getRequestURI());
        String qs = request.getQueryString();
        if (qs != null) {
            url.append("?");
            url.append(qs);
        }
        request.setAttribute("org.amoc.springErrorView", Boolean.TRUE);

        if ((ex instanceof WebSecurityException) || (ex.getCause() instanceof WebSecurityException)) {
            WebSecurityException wEx;
            if (ex instanceof WebSecurityException) {
                wEx = (WebSecurityException) ex;
            } else {
                wEx = (WebSecurityException) ex.getCause();
            }
            WebUtils.makeErrorDoneBean(request, wEx.getMsgKey());

            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof ObjectOptimisticLockingFailureException) || (ex.getCause() instanceof ObjectOptimisticLockingFailureException)) {

            log.debug("ObjectOptimisticLockingFailureException: URL=[" + url.toString() + "]", ex);
            WebUtils.makeErrorDoneBean(request, "errors.recordAlreadyModified");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof OptimisticLockingFailureException) || (ex.getCause() instanceof OptimisticLockingFailureException)) {
            log.debug("OptimisticLockingFailureException: URL=[" + url.toString() + "]", ex);
            WebUtils.makeErrorDoneBean(request, "errors.records.alreay.modified");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof RecordNotFoundException) || (ex.getCause() instanceof RecordNotFoundException)) {

            log.debug("RecordNotFoundException: URL=[" + url.toString() + "]", ex);
            WebUtils.makeErrorDoneBean(request, "errors.recordNotFound");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof ObjectNotFoundException) || (ex.getCause() instanceof ObjectNotFoundException)) {

            log.debug("RecordNotFoundException: URL=[" + url.toString() + "]", ex);
            WebUtils.makeErrorDoneBean(request, "errors.recordNotFound");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof ObjectRetrievalFailureException) || (ex.getCause() instanceof ObjectRetrievalFailureException)) {

            log.debug("ObjectRetrievalFailureException: URL=[" + url.toString() + "]", ex);
            WebUtils.makeErrorDoneBean(request, "errors.recordNotFound");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof InvalidBookmarkException) || (ex.getCause() instanceof InvalidBookmarkException)) {

            log.debug("Invalid Bookmark: Command Object is null.", ex);
            DoneBean doneBean = WebUtils.makeErrorDoneBean(request, "errors.donot.bookmark");
            doneBean.setBackLink(null);
            request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof VersionMismatchException) || (ex.getCause() instanceof VersionMismatchException)) {
            log.debug("Row has been updated or deleted by another user");
            WebUtils.makeErrorDoneBean(request, "errors.recordAlreadyModified");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof ForeignKeyViolationException) || (ex.getCause() instanceof ForeignKeyViolationException)) {
            log.debug("Child record found");
            WebUtils.makeErrorDoneBean(request, "error.foreignKeyViolation");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else if ((ex instanceof DataIntegrityViolationException) || (ex.getCause() instanceof DataIntegrityViolationException)) {
            log.debug("Data Integrity Violation Exception");
            WebUtils.makeErrorDoneBean(request, "error.dataIntegrityViolation");
            return new ModelAndView(new RedirectView(Forwards.COMMON_DONE, true));

        } else {
            log.debug("Unknown Error Occurred", ex);
        }

        String method = request.getMethod();
        String m = "NULL";
        if (method != null) {                       // being too cautious here
            m = method.substring(0, 1);
        }

//        DebugUtils.printGlobalExceptionDebugInfo(request);

        StringBuilder buffer = new StringBuilder("URL=[");
        buffer.append(url.toString());
        buffer.append("], M=");
        buffer.append(m);
        buffer.append("\n");

        HttpSession session = request.getSession(false);
        if (session != null) {
            buffer.append("\nRequest Trace:\n");
            RequestTraceFilter.printToBuffer(session, "\t", buffer);
        }

        buffer.append("\nRequest Headers:\n");
        DebugUtils.dumpAllHeaders(request, buffer);
        if ("POST".equals(request.getMethod())) {
            buffer.append("\nPost Parameters:\n");
            DebugUtils.dumpPostParameters(request, buffer);
        }

        buffer.append("\nStack Trace:\n");
        log.error(buffer.toString(), ex);

        Map<String, Object> model = new HashMap<String, Object>();
        String formOpenMode = null;
        try {
            formOpenMode = ServletRequestUtils.getStringParameter(request, "formOpenMode");
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        if (formOpenMode != null && formOpenMode.equals("popup")) {
            return new ModelAndView(new RedirectView("/common/popup/globalExceptionFeedback", true), model);
        }

        return new ModelAndView("redirect:/common/globalExceptionFeedback", model);
    }
}
