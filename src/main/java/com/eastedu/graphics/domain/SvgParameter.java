package com.eastedu.graphics.domain;

import lombok.Data;

import java.awt.*;

/**
 * The type Svg parameter.
 *
 * @author ZhenZhong
 */
@Data
public class SvgParameter {
    private static final ThreadLocal<SvgParameter> THREAD_LOCAL = ThreadLocal.withInitial(SvgParameter::new);

    private float lineSpaceRatio = 1;
    private float lineSpace = 2F;
    private float marge = 0;
    private float styleDescentHeight = 4;
    private float defaultFontSize = 10F;
    private boolean useDefaultFont = true;
    private Color defaultFontColor = Color.BLACK;
    private String defaultFontFamily = "Microsoft YaHei PingFang SC sans-serif";
    private Float scale = 1F;

    /**
     * Get svg parameter.
     *
     * @return the svg parameter
     */
    public static SvgParameter get() {
        return THREAD_LOCAL.get();
    }

    /**
     * Clear.
     */
    public static void clear() {
        THREAD_LOCAL.remove();
    }

    /**
     * Gets line space ratio.
     *
     * @return the line space ratio
     */
    public float getLineSpaceRatio() {
        if (lineSpaceRatio <= 0) {
            return 1;
        }
        return lineSpaceRatio;
    }
}
