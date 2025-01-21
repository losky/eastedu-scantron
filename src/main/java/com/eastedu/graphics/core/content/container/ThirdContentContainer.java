package com.eastedu.graphics.core.content.container;

import com.eastedu.common.model.question.QuestionItemContent;
import com.eastedu.graphics.context.QuestionItemAttribute;
import com.eastedu.graphics.domain.CaptureParameter;
import org.apache.batik.svggen.SVGGraphics2D;

import java.util.List;

/**
 * The type Third content container.
 *
 * @author ZhenZhong
 */
public class ThirdContentContainer extends SvgContentContainer implements ContentContainer {

    /**
     * Instantiates a new Third content container.
     *
     * @param questionContents      the question contents
     * @param graphics2D            the graphics 2 d
     * @param questionItemAttribute the question item attribute
     * @param parameter             the parameter
     */
    public ThirdContentContainer(List<QuestionItemContent> questionContents, SVGGraphics2D graphics2D, QuestionItemAttribute questionItemAttribute, CaptureParameter parameter) {
        super(questionContents, graphics2D, questionItemAttribute, parameter);
    }

    @Override
    public void start() {
        // todo 这里转换三方试题
        super.start();
    }
}
