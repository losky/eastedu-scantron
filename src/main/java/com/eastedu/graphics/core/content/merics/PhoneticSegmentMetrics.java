package com.eastedu.graphics.core.content.merics;

import com.eastedu.graphics.core.content.segment.TextSegment;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

/**
 * The type Html segment metrics.
 *
 * @author luozhenzhong
 */
public class PhoneticSegmentMetrics extends AbstractSegmentMetrics implements SegmentMetrics {
    private final SegmentMetrics phoneticMetrics;
    private final SegmentMetrics wordMetrics;
    private final SegmentMetrics mattsMetrics;

    /**
     * Instantiates a new Phonetic segment metrics.
     *
     * @param phonetic the phonetic
     * @param word     the word
     * @param matts    the matts
     */
    public PhoneticSegmentMetrics(TextSegment phonetic, TextSegment word, boolean matts) {
        this.phoneticMetrics = phonetic.getSegmentMetrics();
        this.wordMetrics = word.getSegmentMetrics();
        this.mattsMetrics = matts ? new MattsSegmentMetrics(word.getStyle().getFont()) : new EmptySegmentMetrics();

        this.initMetrics(word);
    }

    private void initMetrics(TextSegment word) {
        // 控制行间距
        setLeading(2);
        // 拼音的top
        setTop(phoneticMetrics.getTop());

        // 文字和田字格是一体的，所以要获取最大ascent（文字和田字格）
        setAscent(phoneticMetrics.getAscent() + phoneticMetrics.getDescent() + Math.max(wordMetrics.getAscent(), mattsMetrics.getAscent()));
        // 文字和田字格是一体的，所以要获取最大descent（文字和田字格）
        setDescent(Math.max(wordMetrics.getDescent(), mattsMetrics.getDescent()));
        // 文字和田字格是一体的，所以要获取最大bottom（文字和田字格）
        setBottom(Math.max(wordMetrics.getBottom(), mattsMetrics.getBottom()));

        setVirtualWidth(Math.max(Math.max(phoneticMetrics.getWidth(), wordMetrics.getVirtualWidth()), mattsMetrics.getWidth()));

        double offset = 0;
        if (word.getStyle().getWordSpace() > 0) {
            offset = Math.max(phoneticMetrics.getVirtualWidth(), wordMetrics.getVirtualWidth()) / 2;
        }

        // 获取文字与田字格最大宽度
        double width = Math.max(mattsMetrics.getWidth(), wordMetrics.getWidth());
        // 获取最大宽度（田字格、拼音和文字的宽度比较）
        if (phoneticMetrics.getWidth() >= width) {
            // 如果拼音的宽度大于文字（田字格）的宽度，则设置间隙
            width = Math.max(phoneticMetrics.getWidth() + 4, wordMetrics.getWidth());
        } else {
            // 如果拼音的宽度小于文字（田字格）的宽度，则获取拼音的虚拟宽度（该宽度比逻辑宽度小）进行对比，是为了让田字格无缝
            width = Math.max(phoneticMetrics.getVirtualWidth(), width);
        }
        setWidth(width + offset);
        // 文字和田字格是一体的, 所有获取最大高度（田字格、拼音的高度比较）
        setHeight(phoneticMetrics.getHeight() + Math.max(wordMetrics.getHeight(), mattsMetrics.getHeight()));
    }

    private SegmentMetrics initSegmentMetrics(String content, Font font) {
        if (StringUtils.isNotBlank(content)) {
            return new TextSegmentMetrics(content, font);
        } else {
            return new EmptySegmentMetrics();
        }
    }


    /**
     * Gets matts length.
     *
     * @return the matts length
     */
    public double getMattsLength() {
        return this.mattsMetrics.getWidth();
    }


    /**
     * Gets matts abscissa offset.
     *
     * @return the matts abscissa offset
     */
    public float getMattsAbscissaOffset() {
        return (float) (Math.abs(getWidth() - getMattsLength()) / 2);
    }


    /**
     * Gets matts ordinate offset.
     *
     * @return the matts ordinate offset
     */
    public float getMattsOrdinateOffset() {
        return -Math.max(wordMetrics.getAscent(), mattsMetrics.getAscent());
    }


    /**
     * Gets phonetic abscissa offset.
     *
     * @return the phonetic abscissa offset
     */
    public float getPhoneticAbscissaOffset() {
        return (float) (Math.abs(getVirtualWidth() - phoneticMetrics.getWidth()) / 2);
    }


    /**
     * Gets phonetic ordinate offset.
     *
     * @return the phonetic ordinate offset
     */
    public float getPhoneticOrdinateOffset() {
        return this.getMattsOrdinateOffset() - Math.max(wordMetrics.getLeading(), mattsMetrics.getLeading());
    }


    /**
     * Gets word abscissa offset.
     *
     * @param matts the matts
     * @return the word abscissa offset
     */
    public float getWordAbscissaOffset(boolean matts) {
        return matts ? (float) (Math.abs(getWidth() - wordMetrics.getWidth()) / 2)
                : (float) (Math.abs(getVirtualWidth() - wordMetrics.getVirtualWidth()) / 2);
    }
}
