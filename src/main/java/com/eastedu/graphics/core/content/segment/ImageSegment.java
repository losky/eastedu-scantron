package com.eastedu.graphics.core.content.segment;

import com.eastedu.common.model.question.Fragment;
import com.eastedu.exception.ServiceException;
import com.eastedu.graphics.core.content.merics.ImageSegmentMetrics;
import com.eastedu.graphics.core.content.merics.SegmentMetrics;
import com.eastedu.graphics.utils.RenderUtil;

import java.awt.*;

/**
 * 设置图片的y坐标往下移动时，需要重置当前图片的y值， 该值被用于下行的y坐标计算
 *
 * @author ZhenZhong
 */
public class ImageSegment extends BaseSegment {

    private final Image image;

    /**
     * Instantiates a new Image segment.
     *
     * @param row         the row
     * @param fragment    the fragment
     * @param canvasWidth the canvas width
     */
    public ImageSegment(int row, Fragment fragment, double canvasWidth) {
        super(row, fragment);
        if (canvasWidth <= 0) {
            throw new ServiceException("图片渲染的画布宽度必须大于0");
        }
        this.image = initImage(fragment, canvasWidth);
    }

    /**
     * 计算图片缩放，如果超过画布大小，则需要进行缩放
     *
     * @param fragment    the fragment
     * @param canvasWidth the canvas width
     * @return image image
     */
    protected Image initImage(Fragment fragment, double canvasWidth) {
        return RenderUtil.createImage(fragment.getContent(), fragment.getStyles(), canvasWidth);
    }

    @Override
    protected void doDraw(Graphics2D graphics2D) {
        // 计算图片的真实偏移量
        double yOffset = getAscentHeight();
        graphics2D.drawImage(image, 0, -(int) yOffset, (int) getWidth(), (int) (getHeight()), null);
    }

    @Override
    protected SegmentMetrics initFontMetrics() {
        return new ImageSegmentMetrics(image);
    }

    @Override
    protected BaseSegment sub(String content) {
        throw new UnsupportedOperationException("不支持的操作");
    }
}
