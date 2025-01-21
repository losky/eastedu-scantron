package com.eastedu.graphics.enums;


import lombok.Getter;

/**
 * 内容类别
 *
 * @author ZhenZhong
 */
@Getter
public enum ContentPart {
    /**
     * 内容类别
     */
    STEM(1, "题干"),
    /**
     * Option content part.
     */
    OPTION(2, "选项"),
    /**
     * Answer content part.
     */
    ANSWER(3, "答案"),
    /**
     * Explanation content part.
     */
    EXPLANATION(4, "解析"),
    /**
     * Answer position content part.
     */
    ANSWER_POSITION(5, "作答内容"),
    /**
     * Unknown content part.
     */
    UNKNOWN(-1, "未知");

    private final int code;
    private final String name;

    ContentPart(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Value of code content part.
     *
     * @param code the code
     * @return the content part
     */
    public static ContentPart valueOfCode(int code) {
        for (ContentPart part : values()) {
            if (part.getCode() == code) {
                return part;
            }
        }
        return UNKNOWN;
    }
}
