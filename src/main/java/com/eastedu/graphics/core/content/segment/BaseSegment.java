package com.eastedu.graphics.core.content.segment;

import com.eastedu.common.enums.MediaTypeEnum;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.graphics.core.content.merics.SegmentMetrics;
import com.eastedu.graphics.core.content.style.Style;
import com.eastedu.graphics.domain.GlobalProperty;
import com.eastedu.graphics.utils.LayoutUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Segment.
 *
 * @author ZhenZhong
 */
@Slf4j
public abstract class BaseSegment {

    /**
     * -- GETTER --
     * Gets row.
     */
    @Getter
    private final int row;
    /**
     * -- GETTER --
     * Gets style.
     */
    @Getter
    private final Style style;
    /**
     * -- GETTER --
     * Gets media type.
     */
    @Getter
    private final MediaTypeEnum mediaType;
    private final SegmentMetricsCreator segmentMetricsCreator;
    /**
     * 填空结构化，多个空格连在一起时，看起来像只有一个空格，因此增加间距参数
     */
    protected int marge;
    private SegmentMetrics segmentMetrics;
    /**
     * -- GETTER --
     * Gets additional.
     */
    @Getter
    private Map<String, Object> additional;
    /**
     * 用于选项排版时，解决中线定位不准确的问题，强制使用该宽度，忽略内容本身宽度
     */
    private Integer mandatoryWidth;

    /**
     * Instantiates a new Segment.
     *
     * @param row      the row
     * @param fragment the fragment
     */
    protected BaseSegment(int row, Fragment fragment) {
        this.row = row;
        this.style = LayoutUtil.convert(fragment.getStyles(), this.includeStyle(), this.excludeStyle());
        this.mediaType = fragment.getMediaType();
        this.segmentMetricsCreator = this::initFontMetrics;
        this.setAdditional(fragment.getAdditional());
    }

    /**
     * Instantiates a new Segment.
     *
     * @param row       the row
     * @param style     the style
     * @param mediaType the media type
     */
    protected BaseSegment(int row, Style style, MediaTypeEnum mediaType) {
        this.row = row;
        this.style = style;
        this.mediaType = mediaType;
        this.segmentMetricsCreator = this::initFontMetrics;
        this.setAdditional(new ConcurrentHashMap<>(0));
    }

    /**
     * 获取支持的样式
     *
     * @return the supported style
     */
    protected List<String> includeStyle() {
        return Collections.singletonList("*");
    }

    /**
     * Exclude style list.
     *
     * @return the list
     */
    protected List<String> excludeStyle() {
        return Collections.emptyList();
    }

    private void initGraphics(Graphics2D graphics2D) {
        graphics2D.setColor(getStyle().getColor());
        graphics2D.setFont(getStyle().getFont());
    }

    /**
     * Draw.
     *
     * @param graphics the graphics
     */
    public void draw(Graphics2D graphics) {
        try {
            if (GlobalProperty.isDebug()) {
                graphics.setColor(Color.black);
                graphics.drawRect(0, (int) (0 - getDimension().getHeight()), (int) (getDimension().getWidth()), (int) (getDimension().getHeight() + getDescentHeight()));

                // ascent
                graphics.setColor(Color.green);
                graphics.drawLine(0, (int) (0 - getDimension().getHeight()), (int) (getDimension().getWidth()), (int) (0 - getDimension().getHeight()));

                // baseline
                graphics.setColor(Color.red);
                graphics.drawLine(0, 0, (int) (getDimension().getWidth()), 0);

                // descent
                graphics.setColor(Color.green);
                graphics.drawLine(0, (int) (0 + getDescentHeight()), (int) (getDimension().getWidth()), (int) (0 + getDescentHeight()));

                graphics.setColor(Color.black);
                graphics.setFont(new Font("", Font.BOLD, 10));
                graphics.drawString(String.valueOf(0), 0, (int) (0 + getDescentHeight()) + 10F);

                log.debug("内容: " + getContent() + " - X坐标：" + 0 + " - 尺寸： " + segmentMetrics.toString());
            }
            this.initGraphics(graphics);
            this.doDraw(graphics);
        } finally {
            graphics.translate(getWidth(), 0);
        }

    }

    /**
     * Gets segment metrics.
     *
     * @return the segment metrics
     */
    public SegmentMetrics getSegmentMetrics() {
        if (Objects.isNull(segmentMetrics)) {
            this.segmentMetrics = segmentMetricsCreator.create();
        }
        return segmentMetrics;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        if (Objects.nonNull(this.mandatoryWidth)) {
            return this.mandatoryWidth;
        }
        return getSegmentMetrics().getWidth();
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return getSegmentMetrics().getHeight();
    }

    /**
     * 这个是基线以上高度（也就是y坐标），加上leading是为了增加行距，
     *
     * @return the ascent height
     */
    public float getAscentHeight() {
        return getSegmentMetrics().getAscent() + getSegmentMetrics().getLeading();
    }

    /**
     * 这个是基线以下高度（下沉）
     *
     * @return the descent height
     */
    public float getDescentHeight() {
        return getSegmentMetrics().getDescent();
    }

    /**
     * 这个是行距，下沉+底部高度
     *
     * @return the leading
     */
    public float getLineSpace() {
        return getSegmentMetrics().getDescent() + getSegmentMetrics().getBottom();
    }


    /**
     * 获取尺寸信息
     *
     * @return Dimension dimension
     */
    protected Dimension getDimension() {
        return getSegmentMetrics().getDimension();
    }

    /**
     * 是否文本
     *
     * @return boolean boolean
     */
    public boolean isTextual() {
        return false;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return "";
    }

    /**
     * Sub segment segment.
     *
     * @param content the content
     * @param start   the start
     * @param end     the end
     * @return the segment
     */
    public BaseSegment subSegment(String content, int start, int end) {
        return this.sub(content.substring(start, end));
    }

    /**
     * Sets additional.
     *
     * @param additional the additional
     */
    public void setAdditional(Map<String, Object> additional) {
        this.additional = additional;
        Object width = additional.get("width");
        if (Objects.nonNull(width)) {
            this.mandatoryWidth = (Integer) width;
        }
        Object marge = additional.get("marge");
        if (Objects.nonNull(marge)) {
            this.marge = (Integer) marge;
        }
    }

    /**
     * 初始化内容度量信息
     *
     * @return SegmentMetrics segment metrics
     */
    protected abstract SegmentMetrics initFontMetrics();

    /**
     * 打印
     *
     * @param graphics2D graphics2D
     */
    protected abstract void doDraw(Graphics2D graphics2D);


    /**
     * 截断
     *
     * @param content content
     * @return Segment segment
     */
    protected abstract BaseSegment sub(String content);

    /**
     * 将一个完整的segment进行拆分（主要是为了支持拼音）
     *
     * @return list list
     */
    public List<? extends BaseSegment> split() {
        return Collections.singletonList(this);
    }

    /**
     * The interface Segment metrics creator.
     */
    interface SegmentMetricsCreator {
        /**
         * 创建度量信息
         *
         * @return SegmentMetrics segment metrics
         */
        SegmentMetrics create();
    }

}
