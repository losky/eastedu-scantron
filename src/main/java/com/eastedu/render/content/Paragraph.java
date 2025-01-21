package com.eastedu.render.content;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * The type Paragraph.
 * @author superman
 */
@Data
public class Paragraph {
 private final List<Segment> segments = new ArrayList<>();

    /**
     * Add graph content.
     *
     * @param content the content
     */
    public void addGraphContent(Segment content) {
        segments.add(content);
    }
}
