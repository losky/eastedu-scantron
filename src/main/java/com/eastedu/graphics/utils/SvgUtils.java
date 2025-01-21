package com.eastedu.graphics.utils;

import com.eastedu.exception.ServiceException;
import com.eastedu.graphics.domain.SvgParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

/**
 * 输出
 *
 * @author ZhenZhong
 */
@Slf4j
public class SvgUtils {
    /**
     * Writes a file containing the SVG element.
     *
     * @param os         the OutputStream ({@code null} not permitted).
     * @param svgElement the SVG element ({@code null} not permitted).
     * @param zip        compress the output.
     * @throws IOException if there is an I/O problem.
     * @since 3.0
     */
    public static void writeToSvg(OutputStream os, String svgElement, boolean zip)
            throws IOException {
        if (zip) {
            os = new GZIPOutputStream(os);
        }
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        try (BufferedWriter writer = new BufferedWriter(osw)) {
            writer.write(svgElement + "\n");
            writer.flush();
        }
    }

    /**
     * Writes a file containing the SVG element.
     *
     * @param os         the OutputStream ({@code null} not permitted).
     * @param graphics2D the SVG element ({@code null} not permitted).
     * @param zip        compress the output.
     * @throws IOException if there is an I/O problem.
     * @since 3.0
     */
    public static void writeToSvg(OutputStream os, SVGGraphics2D graphics2D, boolean zip)
            throws IOException {
        if (zip) {
            os = new GZIPOutputStream(os);
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            graphics2D.stream(writer, false, false);
            writer.flush();
        }
    }

    /**
     * 转换svg为图片
     *
     * @param inputStream the input stream
     * @param dimension   the dimension
     * @param transcoder  the transcoder
     * @return 结果 input stream
     */
    public static InputStream convert2Image(InputStream inputStream, Dimension dimension, ImageTranscoder transcoder) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            inputStream.reset();
            flush(transcoder, dimension, inputStream, outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (TranscoderException | IOException e) {
            log.error("转图片失败", e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 输出流
     *
     * @param transcoder   the transcoder
     * @param dimension    the dimension
     * @param inputStream  the input stream
     * @param outputStream the output stream
     * @throws TranscoderException the transcoder exception
     * @throws IOException         the io exception
     */
    public static void flush(ImageTranscoder transcoder, Dimension dimension, InputStream inputStream, OutputStream outputStream) throws TranscoderException, IOException {
        try {
            transcoder(dimension, transcoder, inputStream, outputStream);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("输出内容失败", e);
                }
            }
        }

    }

    private static void transcoder(Dimension dimension, ImageTranscoder transcoder, InputStream inputStream, OutputStream outputStream) throws TranscoderException {
        double scaleWidth = dimension.getWidth();
        double scaleHeight = dimension.getHeight();

        TranscoderInput input = new TranscoderInput(inputStream);
        TranscoderOutput output = new TranscoderOutput(outputStream);
        transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, (float) scaleWidth);
        transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, (float) scaleHeight);
        transcoder.addTranscodingHint(ImageTranscoder.KEY_DEFAULT_FONT_FAMILY, SvgParameter.get().getDefaultFontFamily());
        transcoder.transcode(input, output);
    }


    /**
     * Gets out dir.
     *
     * @return the out dir
     */
    public static File getOutDir() {
        final File outDir = new File("temp");
        if (!outDir.isDirectory()) {
            final boolean success = outDir.mkdirs();
            if (!success) {

                throw new ServiceException(
                        "Failed to create temp directory. Please delete all files / directories named temp"
                );
            }
        }
        return outDir;
    }
}
