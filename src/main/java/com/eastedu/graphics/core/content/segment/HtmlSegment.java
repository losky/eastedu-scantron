package com.eastedu.graphics.core.content.segment;

import com.eastedu.common.enums.MediaTypeEnum;
import com.eastedu.common.json.JsonUtil;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.graphics.core.content.merics.EmptySegmentMetrics;
import com.eastedu.graphics.core.content.merics.PhoneticSegmentMetrics;
import com.eastedu.graphics.core.content.merics.SegmentMetrics;
import com.eastedu.graphics.core.content.style.Style;
import com.eastedu.graphics.core.content.style.background.ChineseCharacterPracticeGrid;
import com.eastedu.graphics.domain.PhoneticContent;
import com.eastedu.graphics.utils.LayoutUtil;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 设置图片的y坐标往下移动时，需要重置当前图片的y值， 该值被用于下行的y坐标计算
 *
 * @author ZhenZhong
 */
public class HtmlSegment extends BaseSegment {

    private final List<PhoneticSegment> contents = new ArrayList<>();

    /**
     * Instantiates a new Html segment.
     *
     * @param row      the row
     * @param fragment the fragment
     */
    public HtmlSegment(int row, Fragment fragment) {
        super(row, fragment);
        String content = fragment.getContent();
        List<PhoneticContent> phoneticContents = JsonUtil.parseArray(content, PhoneticContent.class);
        for (PhoneticContent phoneticContent : phoneticContents) {
            contents.add(new PhoneticSegment(row, fragment.getStyles(), MediaTypeEnum.HTML, phoneticContent));
        }
    }

    @Override
    protected SegmentMetrics initFontMetrics() {
        return new EmptySegmentMetrics();
    }

    @Override
    protected void doDraw(Graphics2D graphics2D) {

    }

    @Override
    public List<? extends BaseSegment> split() {
        return contents;
    }

    @Override
    protected BaseSegment sub(String content) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Override
    protected List<String> includeStyle() {
        return Arrays.asList("Color", "FontSize", "Font", "Bold", "Italic");
    }

    /**
     * The type Pin yin segment.
     */
    public static class PhoneticSegment extends BaseSegment {
        private final PhoneticContent phoneticContent;
        private final TextSegment phonetic;
        private final TextSegment word;

        /**
         * Instantiates a new Pin yin segment.
         *
         * @param row             the row
         * @param styles          the styles
         * @param mediaType       the media type
         * @param phoneticContent the phonetic content
         */
        public PhoneticSegment(int row, Set<String> styles, MediaTypeEnum mediaType, PhoneticContent phoneticContent) {
            super(row, new Style(), mediaType);
            this.phoneticContent = phoneticContent;
            Style style = LayoutUtil.convert(styles, this.includeStyle(), this.excludeStyle());
            if (phoneticContent.isMatts()) {
                style.setFontSize(style.getFontSize() * 0.5f);
            }
            this.phonetic = new TextSegment(row, style, MediaTypeEnum.TEXT, phoneticContent.getPhonetic());
            this.word = new TextSegment(row,
                    phoneticContent.isMatts() ? LayoutUtil.convert(styles, this.includeStyle(), this.excludeStyle()) : LayoutUtil.convert(styles, Collections.singletonList("*"), Arrays.asList("VertAlignSuperscript", "VertAlignSubscript")),
                    MediaTypeEnum.TEXT,
                    phoneticContent.getWord());
        }

        @Override
        protected SegmentMetrics initFontMetrics() {
            return new PhoneticSegmentMetrics(this.phonetic, this.word, phoneticContent.isMatts());
        }

        @Override
        protected void doDraw(Graphics2D graphics2D) {

            PhoneticSegmentMetrics segmentMetrics = (PhoneticSegmentMetrics) this.getSegmentMetrics();
            if (phoneticContent.isMatts()) {
                ChineseCharacterPracticeGrid.draw(graphics2D, segmentMetrics.getMattsAbscissaOffset(), segmentMetrics.getMattsOrdinateOffset(), (int) segmentMetrics.getMattsLength(), false);
                // 还原颜色
                graphics2D.setColor(getStyle().getColor());
            }
            String phonetic = this.phoneticContent.getPhonetic();
            if (StringUtils.isNotEmpty(phonetic)) {
                Graphics2D graphics = (Graphics2D) graphics2D.create();
                graphics.translate((int) segmentMetrics.getPhoneticAbscissaOffset(), (int) segmentMetrics.getPhoneticOrdinateOffset());
                this.phonetic.draw(graphics);
            }

            String word = this.phoneticContent.getWord();
            if (StringUtils.isNotEmpty(word)) {
                Graphics2D graphics = (Graphics2D) graphics2D.create();
                graphics.translate((int) segmentMetrics.getWordAbscissaOffset(phoneticContent.isMatts()), -5);
                this.word.draw(graphics);
            }
        }

        @Override
        public float getDescentHeight() {
            return super.getDescentHeight();
        }

        @Override
        protected BaseSegment sub(String content) {
            throw new UnsupportedOperationException("不支持的操作");
        }

        @Override
        protected List<String> includeStyle() {
            return Arrays.asList("Color", "FontSize", "Font", "Bold", "Italic");
        }
    }

}
