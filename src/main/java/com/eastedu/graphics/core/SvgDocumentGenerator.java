package com.eastedu.graphics.core;

import com.eastedu.common.model.question.Fragment;
import com.eastedu.common.model.question.QuestionItemContent;
import com.eastedu.graphics.context.QuestionItemAttribute;
import com.eastedu.graphics.core.content.container.ContentContainer;
import com.eastedu.graphics.core.content.container.MattsContentContainer;
import com.eastedu.graphics.core.content.container.SvgContentContainer;
import com.eastedu.graphics.core.content.container.ThirdContentContainer;
import com.eastedu.graphics.core.style.CustomStyleHandler;
import com.eastedu.graphics.domain.CaptureParameter;
import org.apache.batik.anim.dom.SVG12DOMImplementation;
import org.apache.batik.anim.dom.SVG12OMDocument;
import org.apache.batik.dom.GenericDocumentType;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.svg.SVGDocument;

import java.util.List;

/**
 * The type Svg document generator.
 *
 * @author ZhenZhong
 */
public class SvgDocumentGenerator {
    private final SVGGeneratorContext context;

    /**
     * Instantiates a new Svg document generator.
     */
    public SvgDocumentGenerator() {
        SVGDocument document = new SVG12OMDocument(new GenericDocumentType("svg", "pi", "si"), new SVG12DOMImplementation());
        this.context = SVGGeneratorContext.createDefault(document);
        this.context.setStyleHandler(new CustomStyleHandler());
    }


    /**
     * Generate content container.
     *
     * @param questionContents      the question contents
     * @param parameter             the parameter
     * @param questionItemAttribute the question item attribute
     * @return the content container
     */
    public ContentContainer generate(List<QuestionItemContent> questionContents,
                                     CaptureParameter parameter, QuestionItemAttribute questionItemAttribute) {
        SVGGraphics2D graphics2D = new SVGGraphics2D(context, false);
        if (questionItemAttribute.isThirdResource()) {
            return new ThirdContentContainer(questionContents, graphics2D, questionItemAttribute, parameter);
        }
        return new SvgContentContainer(questionContents, graphics2D, questionItemAttribute, parameter);
    }

    /**
     * 田字格专用
     *
     * @return the content container
     */
    public ContentContainer generateMatts(Fragment fragment, CaptureParameter parameter) {
        SVGGraphics2D graphics2D = new SVGGraphics2D(context, false);
        return new MattsContentContainer(fragment, graphics2D, parameter);
    }
}
