package com.eastedu.graphics.exception;

/**
 * 不合法的内容
 *
 * @author ZhenZhong
 */
public class InvalidContentException extends GraphicsException {
    private final String source;
    private transient final Object content;

    /**
     * Instantiates a new Invalid content exception.
     *
     * @param cause   the cause
     * @param message the message
     * @param source  the source
     * @param content the content
     */
    public InvalidContentException(Throwable cause, String message, String source, Object content) {
        super(cause, message);
        this.source = source;
        this.content = content;
    }

    /**
     * Instantiates a new Invalid content exception.
     *
     * @param message the message
     * @param source  the source
     * @param content the content
     */
    public InvalidContentException(String message, String source, Object content) {
        super(message);
        this.source = source;
        this.content = content;
    }

    /**
     * Instantiates a new Invalid content exception.
     *
     * @param cause   the cause
     * @param source  the source
     * @param content the content
     */
    public InvalidContentException(Throwable cause, String source, Object content) {
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
        return content;
    }

    @Override
    public String getMessage() {
        return "错误类型: " + source + "\n" +
                "错误内容: \n" + content.toString() + "\n" +
                "错误原因: \n" + getError();
    }
}
