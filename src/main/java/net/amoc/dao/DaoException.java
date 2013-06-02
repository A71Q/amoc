package net.amoc.dao;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class DaoException extends RuntimeException {
    private String msgKey;

    public DaoException(String msgKey) {
        super(msgKey);
        this.msgKey = msgKey;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }
}
