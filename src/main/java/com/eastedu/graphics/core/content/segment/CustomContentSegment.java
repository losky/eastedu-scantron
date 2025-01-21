package com.eastedu.graphics.core.content.segment;

import com.eastedu.graphics.document.FragmentCustom;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.font.TextLayout;

/**
 * The type Custom content segment.
 *
 * @author ZhenZhong
 */
public class CustomContentSegment extends TextSegment {

    /**
     * Instantiates a new Custom content segment.
     *
     * @param row     the row
     * @param content the content
     */
    public CustomContentSegment(int row, String content) {
        super(row, new FragmentCustom(content));
    }

    @Override
    protected void doDraw(Graphics2D graphics2D) {
        if (StringUtils.isBlank(getContent())) {
            return;
        }
        TextLayout layout = new TextLayout(getContent(), graphics2D.getFont(), graphics2D.getFontRenderContext());
        layout.draw(graphics2D, 0, 0);
    }

    @Override
    public Dimension getDimension() {
        Dimension dimension = super.getDimension();
        return new Dimension(50, (int) dimension.getHeight());
    }
}
