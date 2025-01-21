package com.eastedu.graphics.core.content.container;

import com.eastedu.common.model.question.Fragment;
import com.eastedu.exception.ServiceException;
import com.eastedu.graphics.core.ScalableDimension;
import com.eastedu.graphics.core.content.Element;
import com.eastedu.graphics.core.content.Location;
import com.eastedu.graphics.core.content.paragraph.Paragraph;
import com.eastedu.graphics.core.content.segment.BaseSegment;
import com.eastedu.graphics.domain.CaptureParameter;
import com.eastedu.graphics.domain.SvgParameter;
import com.eastedu.graphics.enums.Format;
import com.eastedu.graphics.utils.RenderUtil;
import com.eastedu.graphics.utils.SvgUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.function.BiFunction;

/**
 * svg渲染
 *
 * @author ZhenZhong
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractContentContainer implements ContentContainer {
    private static final List<String> ZH_CHART = Arrays.asList("。", "，", "？", "！", "、", "；", "：", "）", "”", "》", ">", "°");
    private static final List<String> EN_CHART = Arrays.asList(".", "\\,", "?", "!", ";", ":", ")", "\\”");

    private final Dimension dimension;
    private final SVGGraphics2D main;
    private final float scale;
    private final Set<String> punctuationSet = new HashSet<>(16);

    /**
     * Instantiates a new Svg content container.
     *
     * @param graphics2D the graphics 2 d
     * @param parameter  the parameter
     */
    protected AbstractContentContainer(SVGGraphics2D graphics2D, CaptureParameter parameter) {
        Location.clear();
        this.dimension = new Dimension(parameter.getWidth(), 0);
        this.main = graphics2D;
        this.main.setSVGCanvasSize(this.dimension);
        Location.clear();
        RenderUtil.initStyle(parameter);
        this.scale = parameter.getScale();

        this.punctuationSet.addAll(ZH_CHART);
        this.punctuationSet.addAll(EN_CHART);
    }


    @Override
    public void start() {
        double maxWidth = this.getDimension().getWidth();
        Dimension renderDimension = this.paint(maxWidth);
        this.dimension.setSize(renderDimension.getWidth(), renderDimension.getHeight());
        main.setSVGCanvasSize(dimension);
    }

    /**
     * 固定宽度，公式图片按照固定宽度，自动缩放
     *
     * @param maxWidth the max width
     * @return 宽度 double
     */
    protected abstract Dimension paint(double maxWidth);

    /**
     * 任务是否在运行中
     *
     * @return the boolean
     */
    protected abstract boolean isRunning();

    /**
     * 添加片段
     *
     * @param maxWidth  maxWidth
     * @param graphics  graphics
     * @param i         i
     * @param paragraph paragraph
     * @param fragment  fragment
     * @return 结果 paragraph
     */
    protected Paragraph appendSegment(double maxWidth, SVGGraphics2D graphics, int i, Paragraph paragraph, Fragment fragment) {
        BaseSegment segmentWhole = RenderUtil.createSegment(i, fragment, this.dimension.getWidth(), this.main);
        List<? extends BaseSegment> segments = segmentWhole.split();
        for (BaseSegment segment : segments) {
            if (!segment.isTextual()) {
                // 需要计算带有公式或者图片的内容，如果超长，则直接换行
                if ((paragraph.getWidth() + segment.getWidth()) > maxWidth) {
                    paragraph = paragraph.renderAndBreakLine(graphics);
                }
                paragraph.addSegment(segment);
            } else {
                paragraph = draw(maxWidth, paragraph, graphics, segment);
            }
        }
        return paragraph;
    }

    /**
     * Draw paragraph.
     *
     * @param maxWidth   the max width
     * @param paragraph  the paragraph
     * @param graphics2D the graphics 2 d
     * @param segment    the segment
     * @return the paragraph
     */
    protected Paragraph draw(double maxWidth, Paragraph paragraph, Graphics2D graphics2D, BaseSegment segment) {
        float currentWidth = paragraph.getWidth();
        float currentLineWidth = (float) (currentWidth + segment.getWidth());

        /*
          如果小于最大宽度，直接追加
          如果等于最大宽度，直接追加并换行
          如果大于最大宽度，则计算自动断行
         */
        if (currentLineWidth < maxWidth) {
            paragraph.addSegment(segment);
        } else if (currentLineWidth == maxWidth) {
            paragraph.addSegment(segment);
            paragraph = paragraph.renderAndBreakLine(graphics2D);
        } else {
            paragraph = autoBreakLine(maxWidth, paragraph, graphics2D, segment, currentWidth);
        }
        return paragraph;
    }

    /**
     * 自动换行计算
     *
     * @param maxWidth     maxWidth
     * @param paragraph    paragraph
     * @param graphics2D   graphics2D
     * @param segment      segment
     * @param currentWidth currentWidth
     * @return Paragraph
     */
    private Paragraph autoBreakLine(double maxWidth, Paragraph paragraph, Graphics2D graphics2D, BaseSegment segment, float currentWidth) {
        String fragmentContent = segment.getContent();
        float currentLineWidth;
        for (int i = 0; i < fragmentContent.length() && isRunning(); i++) {
            segment = segment.subSegment(fragmentContent, i, i + 1);
            String afterEle = "";
            if (i + 1 < fragmentContent.length()) {
                afterEle = segment.subSegment(fragmentContent, i + 1, i + 2).getContent();
            }
            currentLineWidth = (float) (currentWidth + segment.getWidth());
            if (currentLineWidth == maxWidth) {
                if (this.punctuationSet.contains(afterEle)) {
                    paragraph = paragraph.renderAndBreakLine(graphics2D);
                    paragraph.addSegment(segment);
                } else {
                    paragraph.addSegment(segment);
                    paragraph = paragraph.renderAndBreakLine(graphics2D);
                }
            } else if (currentLineWidth > maxWidth) {
                if (this.punctuationSet.contains(segment.getContent())) {
                    BaseSegment lastSegment = paragraph.removeLastSegment();
                    paragraph = paragraph.renderAndBreakLine(graphics2D);
                    paragraph.addSegment(lastSegment);
                    paragraph.addSegment(segment);
                } else {
                    paragraph = paragraph.renderAndBreakLine(graphics2D);
                    paragraph.addSegment(segment);
                }
            } else {
                paragraph.addSegment(segment);
            }
            currentWidth = paragraph.getWidth();
        }
        return paragraph;
    }

    @Override
    public Dimension getDimension() {
        return new Dimension((int) dimension.getWidth(), (int) dimension.getHeight());
    }

    /**
     * 输出图片
     *
     * @param outputStream outputStream
     * @param format       format
     */
    @Override
    public void flush(OutputStream outputStream, Format format) throws IOException, TranscoderException {
        try {
            switch (format) {
                case JPG:
                    SvgUtils.flush(new JPEGTranscoder(), new ScalableDimension(this.dimension, 1), getInputStream(), outputStream);
                    break;
                case PNG:
                    SvgUtils.flush(new PNGTranscoder(), new ScalableDimension(this.dimension, 1), getInputStream(), outputStream);
                    break;
                default:
                    flush(outputStream);
            }
        } finally {
            main.dispose();
            Location.clear();
            SvgParameter.clear();
        }
    }

    /**
     * 输出svg
     *
     * @param outputStream outputStream
     */
    @Override
    public void flush(OutputStream outputStream) throws IOException {
        if (Objects.isNull(main)) {
            throw new ServiceException("内容尚未渲染");
        }
        try {
            SvgUtils.writeToSvg(outputStream, main, false);
        } finally {
            main.dispose();
            Location.clear();
            SvgParameter.clear();
        }
    }

    /**
     * 根据format格式转换对应的inputStream
     *
     * @param format format
     * @return 结果
     */
    @Override
    public InputStream getInputStream(Format format) throws IOException {
        return format.convert(getInputStream(), new ScalableDimension(this.dimension, 1));
    }

    /**
     * 获取原始的inputStream
     *
     * @return 结果
     */
    @Override
    public InputStream getInputStream() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            flush(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
    }

    @Override
    public List<Element> getOtherElements() {
        return Collections.emptyList();
    }

    @Override
    public <T> T convert(Format format, BiFunction<InputStream, Dimension, T> function) throws IOException {
        return function.apply(getInputStream(format), getDimension());
    }
}
