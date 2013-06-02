package net.amoc.util;

import net.amoc.common.web.DoneBean;
import net.amoc.web.SessionKeys;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class DoneUtils {

    public static String commonSuccess(HttpServletRequest request) {
        DoneBean doneBean = new DoneBean(DoneBean.TYPE_SUCCESS, "success.message.saved", true);
        request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);

        //noinspection SpringMVCViewInspection
        return "redirect:/annotated/common/done";
    }

    public static String commonSuccess(HttpServletRequest request, String backToSameForm) {
        DoneBean doneBean = new DoneBean(DoneBean.TYPE_SUCCESS, "success.message.saved", true);


        HashMap<String, String> commonLinkMap = new LinkedHashMap<String, String>();
        commonLinkMap.put(DoneBean.BACK_TO_SAME_FORM, backToSameForm);
        doneBean.setCommonLinkMap(commonLinkMap);

        request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);

        //noinspection SpringMVCViewInspection
        return "redirect:/annotated/common/done";
    }

    public static String commonSuccess(HttpServletRequest request, String backToSameForm, String createNewForm) {
        DoneBean doneBean = new DoneBean(DoneBean.TYPE_SUCCESS, "success.message.saved", true);

        HashMap<String, String> commonLinkMap = new LinkedHashMap<String, String>();
        commonLinkMap.put(DoneBean.BACK_TO_SAME_FORM, backToSameForm);
        commonLinkMap.put(DoneBean.CREATE_NEW, createNewForm);
        doneBean.setCommonLinkMap(commonLinkMap);

        request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);

        //noinspection SpringMVCViewInspection
        return "redirect:/annotated/common/done";
    }

    public static String commonSuccess(HttpServletRequest request,
                                       String createNewForm, String printForm, String printParam) {

        DoneBean doneBean = new DoneBean(DoneBean.TYPE_SUCCESS, "success.message.saved", true);

        HashMap<String, String> commonLinkMap = new LinkedHashMap<String, String>();
        commonLinkMap.put(DoneBean.CREATE_NEW, createNewForm);
        commonLinkMap.put(DoneBean.PRINT_FORM, printForm);
        commonLinkMap.put(DoneBean.PRINT_PARAM, printParam);
        doneBean.setCommonLinkMap(commonLinkMap);

        request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);

        //noinspection SpringMVCViewInspection
        return "redirect:/annotated/common/done";
    }

    public static String commonSuccess(HttpServletRequest request, String backToSameForm,
                                       String createNewForm, String printForm, String printParam) {

        DoneBean doneBean = new DoneBean(DoneBean.TYPE_SUCCESS, "success.message.saved", true);

        HashMap<String, String> commonLinkMap = new LinkedHashMap<String, String>();
        commonLinkMap.put(DoneBean.BACK_TO_SAME_FORM, backToSameForm);
        commonLinkMap.put(DoneBean.CREATE_NEW, createNewForm);
        commonLinkMap.put(DoneBean.PRINT_FORM, printForm);
        commonLinkMap.put(DoneBean.PRINT_PARAM, printParam);
        doneBean.setCommonLinkMap(commonLinkMap);

        request.getSession().setAttribute(SessionKeys.CFW_DONE_BEAN, doneBean);

        //noinspection SpringMVCViewInspection
        return "redirect:/annotated/common/done";
    }

}
