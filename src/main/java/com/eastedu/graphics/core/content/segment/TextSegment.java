package com.eastedu.graphics.core.content.segment;

import com.eastedu.common.enums.MediaTypeEnum;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.graphics.core.content.merics.EmptySegmentMetrics;
import com.eastedu.graphics.core.content.merics.SegmentMetrics;
import com.eastedu.graphics.core.content.merics.TextSegmentMetrics;
import com.eastedu.graphics.core.content.style.Style;
import com.eastedu.graphics.core.content.style.underline.EmphasisDot;
import com.eastedu.graphics.core.content.style.underline.UnderlineDouble;
import com.eastedu.graphics.core.content.style.underline.UnderlineWave;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.font.TextLayout;

/**
 * The type Text segment.
 *
 * @author ZhenZhong
 */
@Slf4j
public class TextSegment extends BaseSegment {

    private final String content;

    private int styleOffset = 0;

    /**
     * Instantiates a new Text segment.
     *
     * @param row      the row
     * @param fragment the fragment
     */
    public TextSegment(int row, Fragment fragment) {
        super(row, fragment);
        this.content = fragment.getContent();
    }

    /**
     * Instantiates a new Text segment.
     *
     * @param row       the row
     * @param style     the style
     * @param mediaType the media type
     * @param content   the content
     */
    protected TextSegment(int row, Style style, MediaTypeEnum mediaType, String content) {
        super(row, style, mediaType);
        this.content = content;
    }

    @Override
    protected void doDraw(Graphics2D graphics2D) {
        TextLayout layout = new TextLayout(getContent(), graphics2D.getFont(), graphics2D.getFontRenderContext());
        layout.draw(graphics2D, 0, 0);
        setStyle(graphics2D, getStyle(), getDescentHeight());
    }

    private void setStyle(Graphics2D graphics2D, Style style, float y) {
        // 字符串长度（像素） str要打印的字符串
        double strPixelWidth = getWidth();
        Graphics2D graphicsStyle = (Graphics2D) graphics2D.create();
        graphicsStyle.translate(0, y);
        if (style.isUnderlineDouble()) {
            new UnderlineDouble(style.isBold(), strPixelWidth, getContent(), graphicsStyle).draw();
            styleOffset = 4;
        }
        if (style.isUnderlineWave()) {
            new UnderlineWave(style.isBold(), strPixelWidth, getContent(), graphicsStyle).draw();
            styleOffset = 2;
        }
        if (style.isEmphasisDot()) {
            new EmphasisDot(strPixelWidth, getContent(), graphicsStyle).draw();
            styleOffset = 4;
        }
    }

    @Override
    public float getDescentHeight() {
        return super.getDescentHeight() + styleOffset;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    protected SegmentMetrics initFontMetrics() {
        if (StringUtils.isEmpty(this.content)) {
            return new EmptySegmentMetrics(getStyle().getFont());
        }
        String content = this.content;
        Font font = getStyle().getFont();
        return new TextSegmentMetrics(content, font);
    }

    @Override
    public boolean isTextual() {
        return true;
    }


    @Override
    protected BaseSegment sub(String content) {
        TextSegment textSegment = new TextSegment(this.getRow(), this.getStyle(), this.getMediaType(), content);
        textSegment.setAdditional(this.getAdditional());
        return textSegment;
    }
}
