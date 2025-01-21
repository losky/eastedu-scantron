package com.eastedu.graphics.core.content.container;

import com.eastedu.common.enums.MediaTypeEnum;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.common.model.question.QuestionItemContent;
import com.eastedu.graphics.core.content.Location;
import com.eastedu.graphics.core.content.paragraph.Paragraph;
import com.eastedu.graphics.domain.CaptureParameter;
import com.eastedu.graphics.utils.LayoutUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

/**
 * 田字格专用
 *
 * @author ZhenZhong
 */
@Slf4j
public class MattsContentContainer extends AbstractContentContainer implements ContentContainer {
    private final Fragment fragment;

    /**
     * Instantiates a new Svg content container.
     *
     * @param fragment   the fragment
     * @param graphics2D the graphics 2 d
     * @param parameter  the parameter
     */
    public MattsContentContainer(Fragment fragment, SVGGraphics2D graphics2D, CaptureParameter parameter) {
        super(graphics2D, parameter);
        this.fragment = fragment;
        Location.clear();
    }

    @Override
    protected Dimension paint(double maxWidth) {
        float width = this.render(maxWidth, getMain(), fragment);
        float maxHeight = Location.get().yOffset;
        Dimension dimension = new Dimension();
        dimension.setSize(width, maxHeight);
        return dimension;
    }

    @Override
    protected boolean isRunning() {
        return true;
    }

    private float render(double maxWidth, SVGGraphics2D graphics, Fragment fragment) {
        Paragraph paragraph = new Paragraph(LayoutUtil.convert(new QuestionItemContent()));
        String fragmentContent = fragment.getContent();
        if (StringUtils.isEmpty(fragmentContent) || fragment.getMediaType() != MediaTypeEnum.HTML) {
            return 0;
        }
        paragraph = appendSegment(maxWidth, graphics, 0, paragraph, fragment);
        paragraph.draw(graphics);
        return paragraph.getWidth();
    }


}
