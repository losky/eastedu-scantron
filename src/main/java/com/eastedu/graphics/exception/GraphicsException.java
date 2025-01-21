package com.eastedu.graphics.exception;

import com.eastedu.exception.ServiceException;

/**
 * 截图异常
 *
 * @author ZhenZhong
 */
public class GraphicsException extends ServiceException {
    private final String error;

    /**
     * Instantiates a new Graphics exception.
     *
     * @param cause the cause
     */
    protected GraphicsException(Throwable cause) {
        super(cause);
        this.error = cause.getMessage();
    }

    /**
     * Instantiates a new Graphics exception.
     *
     * @param cause   the cause
     * @param message the message
     */
    protected GraphicsException(Throwable cause, String message) {
        super(cause, message);
        this.error = message;
    }

    /**
     * Instantiates a new Graphics exception.
     *
     * @param message the message
     */
    protected GraphicsException(String message) {
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
