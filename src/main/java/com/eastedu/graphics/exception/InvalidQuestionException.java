package com.eastedu.graphics.exception;

import com.eastedu.exception.ServiceException;

/**
 * 不合法的试题
 *
 * @author ZhenZhong
 */
public class InvalidQuestionException extends ServiceException {
    private final String error;

    /**
     * Instantiates a new Invalid question exception.
     *
     * @param cause the cause
     */
    public InvalidQuestionException(Throwable cause) {
        super(cause);
        this.error = cause.getMessage();
    }

    /**
     * Instantiates a new Invalid question exception.
     *
     * @param cause   the cause
     * @param message the message
     */
    public InvalidQuestionException(Throwable cause, String message) {
        super(cause, message);
        this.error = message;
    }

    /**
     * Instantiates a new Invalid question exception.
     *
     * @param message the message
     */
    public InvalidQuestionException(String message) {
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
