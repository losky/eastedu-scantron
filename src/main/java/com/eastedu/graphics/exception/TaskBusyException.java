package com.eastedu.graphics.exception;

import com.eastedu.exception.ServiceException;

/**
 * The type Task busy exception.
 *
 * @author ZhenZhong
 */
public class TaskBusyException extends ServiceException {
    private final String error;

    /**
     * Instantiates a new Task busy exception.
     *
     * @param cause the cause
     */
    public TaskBusyException(Throwable cause) {
        super(cause);
        this.error = cause.getMessage();
    }

    /**
     * Instantiates a new Task busy exception.
     *
     * @param cause   the cause
     * @param message the message
     */
    public TaskBusyException(Throwable cause, String message) {
        super(cause, message);
        this.error = message;
    }

    /**
     * Instantiates a new Task busy exception.
     *
     * @param message the message
     */
    public TaskBusyException(String message) {
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
