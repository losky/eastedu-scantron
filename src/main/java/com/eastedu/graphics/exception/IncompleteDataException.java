package com.eastedu.graphics.exception;

import com.eastedu.exception.ServiceException;

/**
 * The type Incomplete data exception.
 *
 * @author ZhenZhong
 */
public class IncompleteDataException extends ServiceException implements Retryable {
    private final String error;

    /**
     * Instantiates a new Incomplete data exception.
     *
     * @param cause the cause
     */
    public IncompleteDataException(Throwable cause) {
        super(cause);
        this.error = cause.getMessage();
    }

    /**
     * Instantiates a new Incomplete data exception.
     *
     * @param cause   the cause
     * @param message the message
     */
    public IncompleteDataException(Throwable cause, String message) {
        super(cause, message);
        this.error = message;
    }

    /**
     * Instantiates a new Incomplete data exception.
     *
     * @param message the message
     */
    public IncompleteDataException(String message) {
        super(message);
        this.error = message;

    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

}
