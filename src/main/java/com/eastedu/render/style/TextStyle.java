package com.eastedu.render.style;

import com.eastedu.render.enums.ScriptStyle;
import com.eastedu.render.enums.UnderlineStyle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;

/**
 * 内容样式
 *
 * @author superman
 */
@Getter
@ToString
@Builder
public class TextStyle {
    @Builder.Default
    private final boolean bold = false;
    @Builder.Default
    private final boolean italic = false;
    @Builder.Default
    private final ScriptStyle scriptStyle = ScriptStyle.NONE;
    @Builder.Default
    private final boolean emphasisDot = false;
    @Builder.Default
    private final boolean strike = false;
    @Builder.Default
    private final float imageWidth = 0;
    @Builder.Default
    private final float imageScale = 1;
    @Builder.Default
    private final int wordSpace = 0;
    @Builder.Default
    private final Color color = Color.BLACK;
    @Builder.Default
    private final int fontSize = 16;
    @Builder.Default
    private final String fontFamily = "宋体";
    @Builder.Default
    private UnderlineStyle underlineStyle = UnderlineStyle.NONE;

    /**
     * Ge font font.
     *
     * @return the font
     */
    public Font getFont() {
        int fontStyle = Font.PLAIN;
        if (bold) {
            fontStyle |= Font.BOLD;
        }
        if (italic) {
            fontStyle |= Font.ITALIC;
        }
        return new Font(fontFamily, fontStyle, fontSize);
    }
}