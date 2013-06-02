package net.amoc.common.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class DoneBean implements Serializable {

    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_ERROR = 2;
    public static final int TYPE_INFO = 3;
    public static final int TYPE_ATTN = 4;

    public static final String BACK_HOME = "back.home";
    public static final String BACK_LIST = "back.list";
    public static final String BACK_SEARCH_RESULT = "back.search.result";
    public static final String CLOSE_THIS_WINDOW = "link.close";
    public static final String BACK_TO_SAME_FORM = "back.to.form";
    public static final String CREATE_NEW = "back.createNew";
    public static final String PRINT_FORM = "print.form";
    public static final String PRINT_PARAM = "print.param";


    private Map<String, String> commonLinkMap;
    private Map<String, String> commonCaptionMap;

    private Map<String, String> linkMap;

    private int type;
    private String message;
    private String backLink;
    private String backType;
    private int id;
    private String msgKey;
    private String[] msgParams;
    private String formOpenMode;
    private Map parameterMap;
    private int status;
    private boolean popup;
    private Map linksToNewForm;     // set KEY = message_code & VALUE = link_url;
    private String quickLinks;


    public DoneBean() {
        init();
    }


    public DoneBean(int doneBeanType, String msgOrMsgKey, boolean isMsgKey) {
        init();
        setType(doneBeanType);
        if (isMsgKey) {
            setMsgKey(msgOrMsgKey);
        } else {
            setMessage(msgOrMsgKey);
        }
    }

    public DoneBean(int doneBeanType, String msgKey, int id) {
        init();
        setType(doneBeanType);
        setMsgKey(msgKey);
        setId(id);
    }

    public DoneBean(int doneBeanType, String msgKey, String... msgParams) {
        init();
        setType(doneBeanType);
        setMsgKey(msgKey);
        setMsgParams(msgParams);
    }

    private void init() {
        commonCaptionMap = new HashMap<String, String>();
        commonCaptionMap.put(BACK_HOME, "back.home");
        commonCaptionMap.put(BACK_LIST, "back.list");
        commonCaptionMap.put(BACK_SEARCH_RESULT, "back.search.result");
        commonCaptionMap.put(CLOSE_THIS_WINDOW, "link.close");
        commonCaptionMap.put(BACK_TO_SAME_FORM, "back.to.form");
        commonCaptionMap.put(CREATE_NEW, "back.createNew");
    }

    /**
     * Return app message resource key corresponding to backType.
     */
    public String getBackCaptionKey() {
        return "back.to.form";
    }

    public String getCommonCaption(String key) {
        return commonCaptionMap.get(key);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getMsgParams() {
        return msgParams;
    }

    public void setMsgParams(String[] msgParams) {
        this.msgParams = msgParams;
    }

    public String getBackLink() {
        return backLink;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public Map<String, String> getCommonLinkMap() {
        return commonLinkMap;
    }

    public void setCommonLinkMap(Map<String, String> commonLinkMap) {
        this.commonLinkMap = commonLinkMap;
    }

    public Map<String, String> getLinkMap() {
        return linkMap;
    }

    public void setLinkMap(Map<String, String> linkMap) {
        this.linkMap = linkMap;
    }

    public String getFormOpenMode() {
        return formOpenMode;
    }

    public void setFormOpenMode(String formOpenMode) {
        this.formOpenMode = formOpenMode;
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public Map getLinksToNewForm() {
        return linksToNewForm;
    }

    public void setLinksToNewForm(Map linksToNewForm) {
        this.linksToNewForm = linksToNewForm;
    }

    public String getQuickLinks() {
        return quickLinks;
    }

    public void setQuickLinks(String quickLinks) {
        this.quickLinks = quickLinks;
    }
}