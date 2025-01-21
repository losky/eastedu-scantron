package com.eastedu.render.style;

import java.awt.Color;
import java.awt.Font;

import com.eastedu.render.enums.ScriptStyle;
import com.eastedu.render.enums.UnderlineStyle;

import lombok.Builder;
import lombok.Data;

/**
 * 内容样式
 */
@Data
@Builder
public class TextStyle {
     @Builder.Default
     private boolean bold = false;
     @Builder.Default
     private boolean italic = false;
     @Builder.Default
     private ScriptStyle scriptStyle = ScriptStyle.NONE;
     @Builder.Default
     private UnderlineStyle underlineStyle = UnderlineStyle.NONE;
     @Builder.Default
     private boolean emphasisDot = false;
     @Builder.Default
     private boolean strike = false;
     @Builder.Default
     private float imageWidth = 0;
     @Builder.Default
     private float imageScale = 1;
     @Builder.Default
     private int wordSpace = 0;
     @Builder.Default
     private Color color = Color.BLACK;
     @Builder.Default
     private Float fontSize = 16f;
     @Builder.Default
     private String fontFamily = "宋体";

    /**
     * Ge font font.
     *
     * @return the font
     */
    public Font geFont() {
          int fontStyle = Font.PLAIN;
          if (bold)
               fontStyle |= Font.BOLD;
          if (italic)
               fontStyle |= Font.ITALIC;
          return new Font(fontFamily, fontStyle, fontSize.intValue());
     }

}