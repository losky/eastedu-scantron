package com.eastedu.graphics.exception;

import java.util.Objects;

/**
 * 系统错误
 *
 * @author ZhenZhong
 */
public class SystemException extends GraphicsException implements Retryable {
    private final String source;
    private final Object content;

    /**
     * Instantiates a new System exception.
     *
     * @param cause   the cause
     * @param message the message
     * @param source  the source
     * @param content the content
     */
    public SystemException(Throwable cause, String message, String source, Object content) {
        super(cause, message);
        this.source = source;
        this.content = content;
    }

    /**
     * Instantiates a new System exception.
     *
     * @param message the message
     * @param source  the source
     * @param content the content
     */
    public SystemException(String message, String source, Object content) {
        super(message);
        this.source = source;
        this.content = content;
    }

    /**
     * Instantiates a new System exception.
     *
     * @param cause   the cause
     * @param source  the source
     * @param content the content
     */
    public SystemException(Throwable cause, String source, Object content) {
        super(cause);
        this.source = source;
        this.content = content;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public Object getContent() {
        if (Objects.isNull(content)) {
            return "";
        }
        return content;
    }

    @Override
    public String getMessage() {
        return "错误类型: " + source + "\n" +
                "错误内容: \n" + getContent().toString() + "\n" +
                "错误原因: \n" + getError();
    }
}
