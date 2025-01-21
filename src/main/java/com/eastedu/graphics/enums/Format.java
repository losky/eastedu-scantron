package com.eastedu.graphics.enums;

import com.eastedu.graphics.utils.SvgUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * The enum Format.
 *
 * @author ZhenZhong
 */
@Slf4j
@Getter
public enum Format {
    /**
     * The Svg.
     */
    SVG(1) {
        @Override
        public InputStream convert(InputStream inputStream, Dimension dimension) {
            try {
                inputStream.reset();
            } catch (IOException e) {
                log.error("内容转换失败", e);
            }
            return inputStream;
        }
    },
    /**
     * The Png.
     */
    PNG(2) {
        @Override
        public InputStream convert(InputStream inputStream, Dimension dimension) {
            PNGTranscoder transcoder = new PNGTranscoder();
            return SvgUtils.convert2Image(inputStream, dimension, transcoder);
        }
    },
    /**
     * The Jpg.
     */
    JPG(3) {
        @Override
        public InputStream convert(InputStream inputStream, Dimension dimension) {
            JPEGTranscoder transcoder = new JPEGTranscoder();
            return SvgUtils.convert2Image(inputStream, dimension, transcoder);
        }

    },
    /**
     * The Null.
     */
    NULL(-1) {
        @Override
        public InputStream convert(InputStream inputStream, Dimension dimension) {
            return inputStream;
        }
    };
    private final int code;

    Format(int code) {
        this.code = code;
    }

    /**
     * Get format.
     *
     * @param extension the extension
     * @return the format
     */
    public static Format get(String extension) {
        for (Format value : values()) {
            if (value.name().equalsIgnoreCase(extension)) {
                return value;
            }
        }
        return NULL;
    }

    /**
     * Convert input stream.
     *
     * @param inputStream the input stream
     * @param dimension   the dimension
     * @return the input stream
     */
    public abstract InputStream convert(InputStream inputStream, Dimension dimension);

}
