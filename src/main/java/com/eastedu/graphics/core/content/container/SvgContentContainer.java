package com.eastedu.graphics.core.content.container;

import com.eastedu.common.enums.MediaTypeEnum;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.common.model.question.QuestionItemContent;
import com.eastedu.graphics.context.QuestionItemAttribute;
import com.eastedu.graphics.core.content.Element;
import com.eastedu.graphics.core.content.Location;
import com.eastedu.graphics.core.content.paragraph.Paragraph;
import com.eastedu.graphics.core.listener.HandlerSemaphore;
import com.eastedu.graphics.domain.CaptureParameter;
import com.eastedu.graphics.utils.LayoutUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type Svg content container.
 *
 * @author ZhenZhong
 */
@Slf4j
public class SvgContentContainer extends AbstractContentContainer implements ContentContainer {
    private static final Pattern BREAK_PATTERN = Pattern.compile("[\r\n]");

    private final List<QuestionItemContent> questionItemContents;
    private final QuestionItemAttribute questionItemAttribute;

    private final List<Element> otherElements = new ArrayList<>();


    /**
     * Instantiates a new Svg content container.
     *
     * @param questionContents      the question contents
     * @param graphics2D            the graphics 2 d
     * @param questionItemAttribute the question item attribute
     * @param parameter             the parameter
     */
    public SvgContentContainer(List<QuestionItemContent> questionContents, SVGGraphics2D graphics2D, QuestionItemAttribute questionItemAttribute, CaptureParameter parameter) {
        super(graphics2D, parameter);
        if (CollectionUtils.isEmpty(questionContents)) {
            this.questionItemContents = new ArrayList<>();
        } else {
            this.questionItemContents = questionContents;
        }
        this.questionItemAttribute = questionItemAttribute;


    }

    private static Dimension getDimension(double maxWidth) {
        float maxHeight = Location.get().yOffset + 10;
        Dimension dimension = new Dimension();
        dimension.setSize(maxWidth, maxHeight);
        return dimension;
    }

    @Override
    protected Dimension paint(double maxWidth) {
        // 开始渲染
        int size = questionItemContents.size();
        for (int i = 0; i < size && isRunning(); i++) {
            QuestionItemContent content = questionItemContents.get(i);
            if (!render(maxWidth, getMain(), i, content)) {
                return getDimension(maxWidth);
            }
        }
        return getDimension(maxWidth);
    }

    @Override
    protected boolean isRunning() {
        return HandlerSemaphore.isRunning(this.questionItemAttribute.getId());
    }

    /**
     * 渲染
     *
     * @param maxWidth maxWidth
     * @param graphics graphics
     * @param i        i
     * @param content  content
     * @return boolean
     */
    private boolean render(double maxWidth, SVGGraphics2D graphics, int i, QuestionItemContent content) {
        List<Fragment> fragments = content.getFragments();
        Paragraph paragraph = new Paragraph(LayoutUtil.convert(content));
        for (Fragment fragment : fragments) {
            if (!isRunning()) {
                return false;
            }
            String fragmentContent = fragment.getContent();
            if (StringUtils.isEmpty(fragmentContent)) {
                continue;
            }
            if (needPickUp(fragment.getMediaType())) {
                otherElements.add(new Element(fragmentContent, 0, fragment.getMediaType(), new Dimension(0, 0)));
                continue;
            }
            if (fragment.getMediaType() == MediaTypeEnum.TEXT && BREAK_PATTERN.matcher(fragmentContent).find()) {
                paragraph = breakLine(maxWidth, graphics, i, paragraph, fragment, fragmentContent);
                continue;
            }
            paragraph = appendSegment(maxWidth, graphics, i, paragraph, fragment);
        }
        paragraph.draw(graphics);
        return true;
    }

    /**
     * 处理\r\n换行
     *
     * @param maxWidth        maxWidth
     * @param graphics        graphics
     * @param i               i
     * @param paragraph       paragraph
     * @param fragment        fragment
     * @param fragmentContent fragmentContent
     * @return Paragraph
     */
    private Paragraph breakLine(double maxWidth, SVGGraphics2D graphics, int i, Paragraph paragraph, Fragment fragment, String fragmentContent) {
        String[] split = BREAK_PATTERN.split(fragmentContent);

        if (split.length == 0) {
            Fragment f = new Fragment();
            f.setContent("");
            f.setMediaType(fragment.getMediaType());
            f.setStyles(fragment.getStyles());
            paragraph = appendSegment(maxWidth, graphics, i, paragraph, f);
        } else {
            for (int i1 = 0; i1 < split.length; i1++) {
                Fragment f = new Fragment();
                String content = split[i1];
                if (StringUtils.isEmpty(content)) {
                    continue;
                }
                f.setContent(content);
                f.setMediaType(fragment.getMediaType());
                f.setStyles(fragment.getStyles());
                paragraph = appendSegment(maxWidth, graphics, i1, paragraph, f);
                // 最后一个片段之后不换行
                if (i1 < split.length - 1) {
                    paragraph = paragraph.renderAndBreakLine(graphics);
                }
            }
        }
        return paragraph;
    }

    private boolean needPickUp(MediaTypeEnum mediaType) {
        return mediaType == MediaTypeEnum.AUDIO || mediaType == MediaTypeEnum.VIDEO || mediaType == MediaTypeEnum.FLUSH;
    }

    @Override
    public List<Element> getOtherElements() {
        return otherElements;
    }
}
