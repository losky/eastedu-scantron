package com.eastedu.graphics.core.content.style;

import com.eastedu.graphics.domain.SvgParameter;
import lombok.Data;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Style.
 *
 * @author ZhenZhong
 */
@Data
public class Style {
    private Map<AttributedCharacterIterator.Attribute, Object> attributeMap = new HashMap<>();
    private boolean bold = false;
    private boolean italic = false;
    private boolean subscript = false;
    private boolean superscript = false;
    private boolean emphasisDot = false;
    private boolean underlineSingle = false;
    private boolean underlineDouble = false;
    private boolean underlineDotted = false;
    private boolean underlineWave = false;
    private boolean strike = false;
    private float imageWidth = 0;
    private float imageScale = 1;
    private int wordSpace = 0;
    private Color color;
    private Float fontSize;
    private String fontFamily;

    /**
     * Instantiates a new Style.
     */
    public Style() {
        this.fontFamily = SvgParameter.get().getDefaultFontFamily();
        this.fontSize = SvgParameter.get().getDefaultFontSize();
        this.color = SvgParameter.get().getDefaultFontColor();
        this.attributeMap.put(TextAttribute.SIZE, this.fontSize);
        this.attributeMap.put(TextAttribute.FAMILY, this.fontFamily);
        this.attributeMap.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);

    }

    /**
     * Sets bold.
     *
     * @param bold the bold
     */
    public void setBold(boolean bold) {
        this.bold = bold;
        this.attributeMap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
    }

    /**
     * Sets italic.
     *
     * @param italic the italic
     */
    public void setItalic(boolean italic) {
        this.italic = italic;
        this.attributeMap.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
    }

    /**
     * Sets subscript.
     *
     * @param subscript the subscript
     */
    public void setSubscript(boolean subscript) {
        this.subscript = subscript;
        this.attributeMap.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
    }

    /**
     * Sets superscript.
     *
     * @param superscript the superscript
     */
    public void setSuperscript(boolean superscript) {
        this.superscript = superscript;
        this.attributeMap.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
    }

    /**
     * Sets font size.
     *
     * @param fontSize the font size
     */
    public void setFontSize(Float fontSize) {
        this.fontSize = fontSize;
        this.attributeMap.put(TextAttribute.SIZE, fontSize);
    }

    /**
     * Sets font family.
     *
     * @param fontFamily the font family
     */
    public void setFontFamily(String fontFamily) {
        if (!SvgParameter.get().isUseDefaultFont()) {
            this.fontFamily = fontFamily;
            this.attributeMap.put(TextAttribute.FAMILY, fontFamily);
        }
    }

    /**
     * Sets word space.
     *
     * @param wordSpace the word space
     */
    public void setWordSpace(int wordSpace) {
        this.wordSpace = wordSpace;
        this.attributeMap.put(TextAttribute.TRACKING, wordSpace);
    }

    /**
     * Sets emphasis dot.
     *
     * @param emphasisDot the emphasis dot
     */
    public void setEmphasisDot(boolean emphasisDot) {
        this.emphasisDot = emphasisDot;
    }

    /**
     * Sets underline single.
     *
     * @param underlineSingle the underline single
     */
    public void setUnderlineSingle(boolean underlineSingle) {
        this.underlineSingle = underlineSingle;
        this.attributeMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
    }

    /**
     * Sets underline double.
     *
     * @param underlineDouble the underline double
     */
    public void setUnderlineDouble(boolean underlineDouble) {
        this.underlineDouble = underlineDouble;
        //        this.attributeMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL);
    }

    /**
     * Sets underline dotted.
     *
     * @param underlineDotted the underline dotted
     */
    public void setUnderlineDotted(boolean underlineDotted) {
        this.underlineDotted = underlineDotted;
        this.attributeMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DASHED);
    }

    /**
     * Sets underline wave.
     *
     * @param underlineWave the underline wave
     */
    public void setUnderlineWave(boolean underlineWave) {
        this.underlineWave = underlineWave;
    }

    /**
     * Sets strike.
     *
     * @param strike the strike
     */
    public void setStrike(boolean strike) {
        this.strike = strike;
        this.attributeMap.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
    }

    /**
     * Sets image width.
     *
     * @param imageWidth the image width
     */
    public void setImageWidth(float imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * Sets image scale.
     *
     * @param imageScale the image scale
     */
    public void setImageScale(float imageScale) {
        this.imageScale = imageScale;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
        this.attributeMap.put(TextAttribute.FOREGROUND, color);
    }

    /**
     * Gets font.
     *
     * @return the font
     */
    public Font getFont() {
        return new Font(attributeMap);
    }


    /**
     * Gets style.
     *
     * @return the style
     */
    public Style getStyle() {
        return this;
    }

}
