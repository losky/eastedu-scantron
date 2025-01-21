package com.eastedu.graphics.utils;

import com.eastedu.common.enums.MediaTypeEnum;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.graphics.core.content.segment.*;
import com.eastedu.graphics.core.content.style.Style;
import com.eastedu.graphics.core.style.CustomStyleHandler;
import com.eastedu.graphics.domain.CaptureParameter;
import com.eastedu.graphics.domain.SvgParameter;
import com.eastedu.graphics.exception.InvalidContentException;
import com.eastedu.graphics.io.Base64ImageResource;
import org.apache.batik.anim.dom.SVG12DOMImplementation;
import org.apache.batik.anim.dom.SVG12OMDocument;
import org.apache.batik.dom.GenericDocumentType;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.UrlResource;
import org.w3c.dom.svg.SVGDocument;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.util.List;
import java.util.*;

/**
 * render 工具类
 *
 * @author luozhenzhong
 */
public class RenderUtil {

    private static final String MEDIA_TYPE = "mediaType=";

    /**
     * 将fragment转换为segment，进行度量计算
     *
     * @param i        the
     * @param fragment the fragment
     * @param width    the width
     * @param main     the main
     * @return Segment segment
     */
    public static BaseSegment createSegment(int i, Fragment fragment, double width, SVGGraphics2D main) {
        BaseSegment segment;
        if (fragment.getMediaType() == MediaTypeEnum.FORMULA) {
            if (fragment.getContent().contains(MEDIA_TYPE)) {
                // todo 临时方案， 将Table放入formula中，并添加mediaType标记
                segment = new TableSegment(i, fragment, width);
            } else {
                segment = new FormulaSegment(i, fragment, width, main);
            }
        } else if (fragment.getMediaType() == MediaTypeEnum.IMAGE) {
            segment = new ImageSegment(i, fragment, width);
        } else if (fragment.getMediaType() == MediaTypeEnum.TABLE) {
            segment = new TableSegment(i, fragment, width);
        } else if (fragment.getMediaType() == MediaTypeEnum.HTML) {
            segment = new HtmlSegment(i, fragment);
        } else {
            segment = new TextSegment(i, fragment);
        }
        return segment;
    }

    /**
     * 创建空白fragment
     *
     * @param width 自定义宽度
     * @return Fragment fragment
     */
    public static Fragment createWhiteSpaceFragment(Integer width) {
        Fragment fragment = new Fragment();
        fragment.setContent(" ");
        fragment.setMediaType(MediaTypeEnum.TEXT);
        fragment.setAdditional("width", width);
        return fragment;
    }

    /**
     * 获取空白字符的宽度
     *
     * @return 空白字符宽度 white space width
     */
    public static int getWhiteSpaceWidth() {
        Fragment fragment = new Fragment();
        fragment.setContent(StringUtils.repeat(" ", 1));
        fragment.setMediaType(MediaTypeEnum.TEXT);
        return (int) RenderUtil.createSegment(0, fragment, 0, null).getWidth();
    }

    /**
     * 初始化样式
     *
     * @param parameter the parameter
     */
    public static void initStyle(CaptureParameter parameter) {
        SvgParameter svgParameter = SvgParameter.get();
        svgParameter.setLineSpaceRatio(parameter.getLineSpaceRatio());
        svgParameter.setLineSpace(parameter.getLineSpace());
        svgParameter.setStyleDescentHeight(parameter.getStyleDescentHeight());
        svgParameter.setMarge(parameter.getMargin());
        svgParameter.setDefaultFontSize(parameter.getDefaultFontSize());
        svgParameter.setDefaultFontFamily(parameter.getDefaultFontFamily());
        svgParameter.setScale(parameter.getScale());
        svgParameter.setUseDefaultFont(parameter.getUseDefaultFont());
        svgParameter.setDefaultFontColor(parameter.getDefaultFontColor());
    }

    /**
     * 清除样式
     */
    public static void clearStyle() {
        SvgParameter.clear();
    }


    /**
     * 初始化指定个数的宽度列表
     *
     * @param count 个数
     * @return 最大寬度 list
     */
    public static List<Integer> initOptionWidth(Integer count) {
        List<Integer> maxWidth = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            maxWidth.add(i, 0);
        }
        return maxWidth;
    }

    /**
     * 创建默认的画布，用于选项计算
     *
     * @return the svg graphics 2 d
     */
    public static SVGGraphics2D createDefaultGraphics2D() {
        SVGDocument document = new SVG12OMDocument(new GenericDocumentType("svg", "pi", "si"), new SVG12DOMImplementation());
        SVGGeneratorContext context = SVGGeneratorContext.createDefault(document);
        context.setStyleHandler(new CustomStyleHandler());
        return new SVGGraphics2D(context, false);
    }

    /**
     * Create image image.
     *
     * @param content     the content
     * @param styles      the styles
     * @param canvasWidth the canvas width
     * @return the image
     */
    public static Image createImage(String content, Set<String> styles, double canvasWidth) {
        try {
            InputStreamSource resource;
            try {
                resource = new UrlResource(content);
            } catch (MalformedURLException e) {
                resource = new Base64ImageResource(content);
            }
            BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());

            if (Objects.isNull(bufferedImage)) {
                // 无法识别的图片，直接返回空图片
                return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }
            float scale = (float) bufferedImage.getHeight() / bufferedImage.getWidth();

            Style sty = LayoutUtil.convert(styles, Collections.singletonList("*"), Collections.emptyList());

            if (sty.getImageWidth() <= 0 || sty.getImageWidth() > canvasWidth) {
                int width = (int) (bufferedImage.getWidth() > canvasWidth ? canvasWidth : bufferedImage.getWidth());
                int height = (int) (width * scale);
                return bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            } else {
                int width = (int) sty.getImageWidth();
                int height = (int) (bufferedImage.getHeight() * sty.getImageScale());
                return bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            throw new InvalidContentException(e, "图片错误", content);
        }
    }
}
