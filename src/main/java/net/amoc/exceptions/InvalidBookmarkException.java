package net.amoc.exceptions;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class InvalidBookmarkException extends NestableRuntimeException {

    public InvalidBookmarkException(String s) {
        super(s);
    }

    public InvalidBookmarkException() {
        this("Invalid Bookmark Exception!");
    }
}