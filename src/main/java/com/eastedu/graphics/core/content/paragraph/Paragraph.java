package com.eastedu.graphics.core.content.paragraph;

import com.eastedu.graphics.core.content.Location;
import com.eastedu.graphics.core.content.segment.BaseSegment;
import com.eastedu.graphics.core.content.segment.CustomContentSegment;
import com.eastedu.graphics.core.content.segment.ShapeSegment;
import com.eastedu.graphics.core.content.style.ParagraphStyle;
import com.eastedu.graphics.domain.SvgParameter;
import com.eastedu.graphics.enums.ListPatternType;
import com.eastedu.graphics.utils.LayoutUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Paragraph.
 *
 * @author ZhenZhong
 */
@Data
@Slf4j
public class Paragraph {

    private final int minHeightLimit = 25;
    private final Location location = Location.get();
    private final ParagraphStyle style;
    private float width;

    /**
     * 纵坐标
     */
    private List<BaseSegment> segments = new ArrayList<>();

    /**
     * Instantiates a new Paragraph.
     *
     * @param style the style
     */
    public Paragraph(ParagraphStyle style) {
        this.style = style;
        this.width = style.getIndentation();
        this.location.row++;
        this.addItemNumber(style);
    }

    /**
     * 添加项目编号
     */
    private void addItemNumber(ParagraphStyle style) {
        if (style.isList()) {
            ListPatternType listPatternType = style.getListPatternType();
            if (listPatternType.isOrdered()) {
                int order = location.getListSymbol(listPatternType);
                BaseSegment segment = new CustomContentSegment(this.location.row, listPatternType.getSymbol(order));
                style.setListIndentation((float) segment.getWidth());
                this.addSegment(segment);
            } else {
                Ellipse2D oval = new Ellipse2D.Float((int) this.getWidth(), 23, 7, 7);
                BaseSegment segment = new ShapeSegment(this.location.row, oval);
                style.setListIndentation((float) segment.getWidth());
                this.addSegment(segment);
            }
        }
    }

    /**
     * Add segment.
     *
     * @param segment the segment
     */
    public void addSegment(BaseSegment segment) {
        this.width += (float) segment.getWidth();
        this.segments.add(segment);
    }

    /**
     * Remove last segment segment.
     *
     * @return the segment
     */
    public BaseSegment removeLastSegment() {
        BaseSegment lastSegment = this.segments.get(segments.size() - 1);
        this.segments.remove(segments.size() - 1);
        this.width -= (float) lastSegment.getWidth();
        return lastSegment;
    }

    private float initY() {
        //        默认行高25

        double maxHeight = segments.stream().mapToDouble(BaseSegment::getAscentHeight).max().orElse(minHeightLimit);
        if (maxHeight < minHeightLimit) {
            maxHeight = minHeightLimit;
        }
        //        Y坐标位置，（字体高度 + 上段下沉(调用draw后设置) + 行间距 + 全局行高）* 行高倍数
        location.yOffset += (float) ((maxHeight + style.getLineSpace() + SvgParameter.get().getLineSpace()) * SvgParameter.get().getLineSpaceRatio());
        return location.yOffset;
    }

    /**
     * Draw.
     *
     * @param graphics2D the graphics 2 d
     */
    public void draw(Graphics2D graphics2D) {
        try {
            Graphics2D graphicsParagraph = createParagraphGraphics(graphics2D);
            for (BaseSegment segment : getSegments()) {
                segment.draw(graphicsParagraph);
            }
            this.setDescentHeight();
        } finally {
            segments.clear();
        }
    }


    private Graphics2D createParagraphGraphics(Graphics2D graphics2D) {
        float startX = LayoutUtil.calcOriginalAbscissa(style.getAlignment(), this.width, LayoutUtil.getCanvasWidth(graphics2D));
        float startY = this.initY();
        Graphics2D graphicsParagraph = (Graphics2D) graphics2D.create();
        graphicsParagraph.translate(startX, startY);
        return graphicsParagraph;
    }

    /**
     * Sets descent height.
     */
    protected void setDescentHeight() {
        float leading = (float) segments.stream().mapToDouble(BaseSegment::getLineSpace).max().orElse(0);
        // toto 增加了行间距，可能会出现行间距过大问题，后续优化
        location.yOffset += (leading + style.getLineSpace());
    }

    /**
     * 内容超长，输出并换行操作
     *
     * @param graphics2D the graphics 2 d
     * @return paragraph paragraph
     */
    public Paragraph renderAndBreakLine(Graphics2D graphics2D) {
        this.draw(graphics2D);
        ParagraphStyle paragraphStyle = this.getStyle();
        paragraphStyle.setIndentation(style.getListIndentation());
        paragraphStyle.setList(false);
        return new Paragraph(paragraphStyle);
    }
}
