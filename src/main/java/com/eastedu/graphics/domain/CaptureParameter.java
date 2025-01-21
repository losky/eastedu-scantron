package com.eastedu.graphics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.util.Objects;

/**
 * 截图参数
 *
 * @author luozhenzhong
 */
@Getter
@Builder
@JsonDeserialize(builder = CaptureParameter.CaptureParameterBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptureParameter {
    private final Integer width;
    private final Float lineSpaceRatio;
    private final Float lineSpace;
    private final Float styleDescentHeight;
    private final Float margin;
    private final Float defaultFontSize;
    private final String defaultFontFamily;
    private final Color defaultFontColor;
    private final Float scale;
    private final Boolean useDefaultFont;

    /**
     * Gets width.
     *
     * @return the width
     */
    public Integer getWidth() {
        if (Objects.isNull(width)) {
            return 1024;
        }

        return width;
    }


    /**
     * Gets line space ratio.
     *
     * @return the line space ratio
     */
    public Float getLineSpaceRatio() {
        if (Objects.isNull(lineSpaceRatio)) {
            return 1F;
        }
        return lineSpaceRatio;
    }

    /**
     * Gets line space.
     *
     * @return the line space
     */
    public Float getLineSpace() {
        if (Objects.isNull(lineSpace)) {
            return 10F;
        }
        return lineSpace;
    }

    /**
     * Gets style descent height.
     *
     * @return the style descent height
     */
    public Float getStyleDescentHeight() {
        if (Objects.isNull(styleDescentHeight)) {
            return 4F;
        }
        return styleDescentHeight;
    }

    /**
     * Gets margin.
     *
     * @return the margin
     */
    public Float getMargin() {
        if (Objects.isNull(margin)) {
            return 0F;
        }
        return margin;
    }

    /**
     * Gets default font size.
     *
     * @return the default font size
     */
    public Float getDefaultFontSize() {
        if (Objects.isNull(defaultFontSize)) {
            return 28F;
        }
        return defaultFontSize;
    }

    /**
     * Gets default font family.
     *
     * @return the default font family
     */
    public String getDefaultFontFamily() {
        if (Objects.isNull(defaultFontFamily)) {
            return "Microsoft YaHei PingFang SC sans-serif";
        }
        return defaultFontFamily;
    }

    /**
     * Gets default font color.
     *
     * @return the default font color
     */
    public Color getDefaultFontColor() {
        if (Objects.isNull(defaultFontColor)) {
            return Color.BLACK;
        }
        return defaultFontColor;
    }

    /**
     * Gets scale.
     *
     * @return the scale
     */
    public Float getScale() {
        if (Objects.isNull(scale)) {
            return 1F;
        }
        return scale;
    }


    /**
     * Gets use default font.
     *
     * @return the use default font
     */
    public Boolean getUseDefaultFont() {
        if (Objects.isNull(useDefaultFont)) {
            return false;
        }
        return useDefaultFont;
    }

    /**
     * The type Capture parameter builder.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class CaptureParameterBuilder {

    }
}
