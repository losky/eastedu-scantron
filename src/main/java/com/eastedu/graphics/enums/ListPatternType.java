package com.eastedu.graphics.enums;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The enum List pattern type.
 *
 * @author ZhenZhong
 */
@Slf4j
public enum ListPatternType {
    /**
     * 列表编号类型
     */
    LOW_ROMA(true) {
        @Override
        protected String format(int order) {
            return LOW_ROMA_SYMBOL[order];
        }
    },
    /**
     * The Upper roma.
     */
    UPPER_ROMA(true) {
        @Override
        protected String format(int order) {
            return UPPER_ROMA_SYMBOL[order];
        }
    },
    /**
     * The Digital.
     */
    DIGITAL(true) {
        @Override
        protected String format(int order) {
            return DIGITAL_NUM[order];
        }
    },
    /**
     * The Low letter.
     */
    LOW_LETTER(true) {
        @Override
        protected String format(int order) {
            return (char) ('a' + order) + ".";
        }
    },
    /**
     * The Upper letter.
     */
    UPPER_LETTER(true) {
        @Override
        protected String format(int order) {
            return (char) ('A' + order) + ".";
        }
    },
    /**
     * The Square.
     */
    SQUARE(false) {
        @Override
        protected String format(int order) {
            return null;
        }
    },
    /**
     * The Circle.
     */
    CIRCLE(false) {
        @Override
        protected String format(int order) {
            return null;
        }
    },
    /**
     * The None.
     */
    NONE(false) {
        @Override
        protected String format(int order) {
            return null;
        }
    };


    /**
     * The constant DIGITAL_NUM.
     */
    public static final String[] DIGITAL_NUM = {"1. ", "2. ", "3. ", "4. ", "5. ", "6. ", "7. ", "8. ", "9. ", "10. ",
            "11. ", "12. ", "13. ", "⒕", "⒖", "⒗", "⒘", "⒙", "⒚", "⒛"};
    /**
     * The constant UPPER_ROMA_SYMBOL.
     */
    public static final String[] UPPER_ROMA_SYMBOL = {"Ⅰ. ", "Ⅱ. ", "Ⅲ. ", "Ⅳ. ", "Ⅴ. ", "Ⅵ. ", "Ⅶ. ", "Ⅷ. ", "Ⅸ. ",
            "Ⅹ. ", "Ⅺ. ", "Ⅻ. ", "XIII. ", "XIV. ", "XV. ", "XVI. "};
    /**
     * The constant LOW_ROMA_SYMBOL.
     */
    public static final String[] LOW_ROMA_SYMBOL = {"ⅰ. ", "ⅱ. ", "ⅲ. ", "ⅳ. ", "ⅴ. ", "ⅵ. ", "ⅶ. ", "ⅷ. ", "ⅸ. ",
            "ⅹ. ", "ⅺ. ", "ⅻ. ", "xiii. ", "xiv. ", "xv. ", "xvi. "};
    private static final Map<Integer, ListPatternType> CACHE = new ConcurrentHashMap<>();

    static {
        CACHE.put((int) 'I', UPPER_ROMA);
        CACHE.put((int) 'i', LOW_ROMA);
        CACHE.put((int) 'A', UPPER_LETTER);
        CACHE.put((int) 'a', LOW_LETTER);
        CACHE.put((int) '1', DIGITAL);
    }

    private final boolean ordered;

    ListPatternType(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Parse list pattern type.
     *
     * @param symbol the symbol
     * @return the list pattern type
     */
    public static ListPatternType parse(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return NONE;
        }
        return parse(symbol.charAt(0));
    }

    /**
     * Parse list pattern type.
     *
     * @param symbol the symbol
     * @return the list pattern type
     */
    public static ListPatternType parse(char symbol) {
        if (CACHE.containsKey((int) symbol)) {
            return CACHE.get((int) symbol);
        }
        log.warn("不支持的项目编号");
        return NONE;
    }

    /**
     * Is ordered boolean.
     *
     * @return the boolean
     */
    public boolean isOrdered() {
        return ordered;
    }

    /**
     * Gets symbol.
     *
     * @param order the order
     * @return the symbol
     */
    public String getSymbol(int order) {
        return format(order);
    }

    /**
     * Format string.
     *
     * @param order the order
     * @return the string
     */
    protected abstract String format(int order);
}
