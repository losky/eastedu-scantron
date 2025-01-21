package com.eastedu.graphics.core.content.merics;

import java.awt.*;

/**
 * The interface Segment metrics.
 *
 * @author ZhenZhong
 */
public interface SegmentMetrics {

    /**
     * The recommended dimension of content.
     *
     * @return 结果 dimension
     */
    Dimension getDimension();

    /**
     * The maximum distance above the baseline for the tallest glyph in
     * the font at a given text size.
     *
     * @return 结果 top
     */
    default float getTop() {
        return 0;
    }

    /**
     * The recommended distance above the baseline for singled spaced text.
     *
     * @return 结果 ascent
     */
    default float getAscent() {
        return 0;
    }

    /**
     * The recommended distance below the baseline for singled spaced text.
     *
     * @return 结果 descent
     */
    default float getDescent() {
        return 0;
    }

    /**
     * The maximum distance below the baseline for the lowest glyph in
     * <p>
     * the font at a given text size.
     *
     * @return 结果 bottom
     */
    default float getBottom() {
        return 0;
    }

    /**
     * The recommended additional space to add between lines of text.
     *
     * @return 结果 leading
     */
    default float getLeading() {
        return 0;
    }

    /**
     * The recommended width of content.
     *
     * @return width width
     */
    default double getWidth() {
        return 0;
    }

    /**
     * The recommended height of content.
     *
     * @return height height
     */
    default double getHeight() {
        return 0;
    }

    /**
     * Gets virtual width.
     *
     * @return the virtual width
     */
    default double getVirtualWidth() {
        return 0;
    }
}
