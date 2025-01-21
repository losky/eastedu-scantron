package com.eastedu.graphics.core.content.style.underline;

import java.awt.*;

/**
 * The type Underline dashed.
 *
 * @author ZhenZhong
 */
public class UnderlineDashed extends BaseUnderline {


    /**
     * Instantiates a new Underline dashed.
     *
     * @param bold    the bold
     * @param width   the width
     * @param content the content
     * @param g       the g
     */
    public UnderlineDashed(boolean bold, double width, String content, Graphics2D g) {
        super(bold, width, content, g);

    }

    @Override
    protected void setBold() {
        Graphics2D g = getGraphics2D();
        g.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{3F}, 1));
    }

}
