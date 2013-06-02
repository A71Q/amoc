package net.amoc.exceptions;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class ForeignKeyViolationException extends NestableException {
    public ForeignKeyViolationException(String s) {
        super(s);
    }
}