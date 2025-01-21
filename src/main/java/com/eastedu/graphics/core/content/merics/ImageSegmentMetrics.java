package com.eastedu.graphics.core.content.merics;

import java.awt.*;

/**
 * 度量信息
 *
 * @author luozhenzhong
 */
public class ImageSegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {

    /**
     * Instantiates a new Image segment metrics.
     *
     * @param image the image
     */
    public ImageSegmentMetrics(Image image) {
        int height = image.getHeight(null);
        int width = image.getWidth(null);

        setLeading(0);
        setTop(0);
        setAscent((float) (height * 0.55));
        setDescent((float) (height * 0.45));
        setBottom(5);
        setWidth(width);
        setHeight(height);

    }

}