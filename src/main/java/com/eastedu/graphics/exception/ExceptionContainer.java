package com.eastedu.graphics.exception;

import lombok.Builder;
import lombok.Data;

/**
 * The type Exception container.
 *
 * @author luozhenzhong
 */
@Data
@Builder
public class ExceptionContainer {
    private final boolean wakeup;
    private final boolean failure;
    private final boolean retryable;
    private final Throwable throwable;

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return throwable.getMessage();
    }
}
