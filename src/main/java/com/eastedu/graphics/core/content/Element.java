package com.eastedu.graphics.core.content;

import com.eastedu.common.enums.MediaTypeEnum;
import lombok.Getter;

import java.awt.*;
import java.util.Objects;

/**
 * The type Element.
 *
 * @author ZhenZhong
 */
@Getter
public final class Element {
    /**
     * -- GETTER --
     *  Gets url.
     *
     */
    private final String url;
    /**
     * -- GETTER --
     *  Gets size.
     *
     */
    private final int size;
    /**
     * -- GETTER --
     *  Gets extension.
     *
     */
    private final String extension;
    /**
     * -- GETTER --
     *  Gets dimension.
     *
     */
    private final Dimension dimension;

    /**
     * Instantiates a new Element.
     *
     * @param url       the url
     * @param size      the size
     * @param mediaType the media type
     * @param dimension the dimension
     */
    public Element(String url, int size, MediaTypeEnum mediaType, Dimension dimension) {
        this.url = url;
        this.size = size;
        this.extension = mediaType.name().toLowerCase();
        this.dimension = dimension;
    }

    /**
     * Instantiates a new Element.
     *
     * @param url       the url
     * @param size      the size
     * @param extension the extension
     * @param dimension the dimension
     */
    public Element(String url, int size, String extension, Dimension dimension) {
        this.url = url;
        this.size = size;
        this.extension = extension;
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Element element = (Element) o;
        return url.equals(element.url) && extension.equals(element.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, extension);
    }
}
