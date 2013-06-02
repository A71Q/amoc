package net.amoc.exceptions;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class VersionMismatchException extends NestableRuntimeException {
    public VersionMismatchException(String s) {
        super(s);
    }

}
