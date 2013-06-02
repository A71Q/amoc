package net.amoc.exceptions;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class WebSecurityException extends NestableException {
    private String msgKey;

    public WebSecurityException(String s, String msgKey) {
        super(s);
        this.msgKey = msgKey;
    }

    public WebSecurityException(String s) {
        this(s, "error.insufficient.privilege");
    }

    public WebSecurityException() {
        this("You have no privilege to access this page", "error.insufficient.privilege");
    }

    public String getMsgKey() {
        return msgKey;
    }
}