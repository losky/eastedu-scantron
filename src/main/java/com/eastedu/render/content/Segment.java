package com.eastedu.render.content;

import com.eastedu.render.style.TextStyle;

import lombok.Data;

/**
 * The type Segment.
 * @author superman
 */
@Data
public class Segment {
    private String content;
    private String type;
    private TextStyle style;

    /**
     * Instantiates a new Segment.
     *
     * @param content the content
     * @param type    the type
     * @param style   the style
     */
    public Segment(String content, String type, TextStyle style) {
        this.content = content;
        this.type = type;
        this.style = style;
    }
}

