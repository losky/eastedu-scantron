package com.eastedu.graphics.io;

import org.springframework.core.io.InputStreamSource;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Base 64 image resource.
 *
 * @author ZhenZhong
 */
public class Base64ImageResource implements InputStreamSource {

    private final byte[] byteArray;

    /**
     * Instantiates a new Base 64 image resource.
     *
     * @param base64Image the base 64 image
     */
    public Base64ImageResource(String base64Image) {
        this(base64Image, true);
    }

    /**
     * Instantiates a new Base 64 image resource.
     *
     * @param base64Image the base 64 image
     * @param hasPrefix   the has prefix
     */
    public Base64ImageResource(String base64Image, boolean hasPrefix) {
        String imageStr;
        if (hasPrefix) {
            Pattern pattern = Pattern.compile("(data:image/).+(;base64),");
            imageStr = pattern.matcher(base64Image).replaceAll("");
        } else {
            imageStr = base64Image;
        }
        Assert.notNull(imageStr, "base64String must not be null");
        this.byteArray = Base64.getDecoder().decode(imageStr);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (Objects.isNull(this.byteArray) || this.byteArray.length == 0) {
            throw new StreamCorruptedException("图片数据不合法");
        }
        return new ByteArrayInputStream(this.byteArray);
    }
}
