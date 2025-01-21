package com.eastedu.graphics.core.content.merics;

import net.sourceforge.jeuclid.layout.JEuclidView;

/**
 * 度量信息
 *
 * @author luozhenzhong
 */
public class FormulaSegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {

    /**
     * Instantiates a new Formula segment metrics.
     *
     * @param jEuclidView the j euclid view
     */
    public FormulaSegmentMetrics(JEuclidView jEuclidView) {

        int ascent = Math.round(jEuclidView.getAscentHeight());
        int descent = Math.round(jEuclidView.getDescentHeight());
        int height = ascent + descent;
        int width = Math.round(jEuclidView.getWidth());

        setLeading(6);
        setTop(0);
        setAscent(ascent);
        setDescent(descent);
        setBottom(0);
        setWidth(width);
        setHeight(height);

    }

}