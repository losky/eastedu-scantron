package com.eastedu.graphics.core.content.container;

import com.eastedu.graphics.core.content.Element;
import com.eastedu.graphics.enums.Format;
import org.apache.batik.transcoder.TranscoderException;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.BiFunction;

/**
 * The interface Content container.
 *
 * @author ZhenZhong
 */
public interface ContentContainer {
    /**
     * 开启内容组装
     */
    void start();

    /**
     * 获取尺寸信息
     *
     * @return 结果 dimension
     */
    Dimension getDimension();

    /**
     * 输出
     *
     * @param outputStream the output stream
     * @param format       the format
     * @throws IOException         the io exception
     * @throws TranscoderException the transcoder exception
     */
    void flush(OutputStream outputStream, Format format) throws IOException, TranscoderException;

    /**
     * 输出
     *
     * @param outputStream the output stream
     * @throws IOException the io exception
     */
    void flush(OutputStream outputStream) throws IOException;

    /**
     * 获取输入流
     *
     * @param format the format
     * @return 结果 input stream
     * @throws IOException the io exception
     */
    InputStream getInputStream(Format format) throws IOException;

    /**
     * 获取输入流
     *
     * @return 结果 input stream
     * @throws IOException the io exception
     */
    InputStream getInputStream() throws IOException;

    /**
     * Gets other elements.
     *
     * @return the other elements
     */
    List<Element> getOtherElements();

    /**
     * 转换stream为指定类型的对象
     *
     * @param <T>      the type parameter
     * @param format   the format
     * @param function the function
     * @return the t
     * @throws IOException the io exception
     */
    <T> T convert(Format format, BiFunction<InputStream, Dimension, T> function) throws IOException;
}
