package com.eastedu.graphics.core.content.segment;

import com.eastedu.boot.third.formula.ConvertResponse;
import com.eastedu.boot.third.formula.HtmlRender;
import com.eastedu.common.model.question.Fragment;
import com.eastedu.content.style.StyleParseHelper;
import com.eastedu.graphics.exception.InvalidContentException;
import com.eastedu.graphics.exception.SystemException;
import com.eastedu.graphics.io.Base64ImageResource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.core.io.InputStreamSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设置图片的y坐标往下移动时，需要重置当前图片的y值， 该值被用于下行的y坐标计算
 *
 * @author ZhenZhong
 */
public class TableSegment extends ImageSegment {

    /**
     * Instantiates a new Table segment.
     *
     * @param row         the row
     * @param fragment    the fragment
     * @param canvasWidth the canvas width
     */
    public TableSegment(int row, Fragment fragment, double canvasWidth) {
        super(row, fragment, canvasWidth);
    }

    @Override
    protected Image initImage(Fragment fragment, double canvasWidth) {
        try {
            String content = fragment.getContent();
            org.jsoup.nodes.Document document = Jsoup.parse(content);
            Element body = document.body();
            resetWidth(body.childNodes());
            String attributeKey = "style";
            if (body.hasAttr(attributeKey)) {
                String style = body.attr(attributeKey);
                Map<String, String> extractStyle = StyleParseHelper.extractStyle(style);
                extractStyle.put("font-family", getStyle().getFontFamily());
                extractStyle.put("font-size", String.valueOf(getStyle().getFontSize()));
                String collect = extractStyle.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(";"));
                body.attr(attributeKey, collect);
            } else {
                String style = "font-family:" + getStyle().getFontFamily() + ";";
                style += "font-size:" + getStyle().getFontSize() + "px;";
                body.attr(attributeKey, style);
            }
            ConvertResponse convertResponse = HtmlRender.html2Img(body.outerHtml(), (int) canvasWidth);
            InputStreamSource resource = new Base64ImageResource(convertResponse.getImage(), false);
            int width = (int) (convertResponse.getWidth() > canvasWidth ? canvasWidth : convertResponse.getWidth());
            int height = (int) (convertResponse.getWidth() > canvasWidth ? convertResponse.getHeight() * canvasWidth / convertResponse.getWidth() : convertResponse.getHeight());
            BufferedImage image = ImageIO.read(resource.getInputStream());
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new SystemException(e, "Table解析错误", fragment.getContent());
        } catch (Exception e) {
            throw new InvalidContentException(e, "Table内容错误", fragment.getContent());
        }
    }

    private void resetWidth(List<Node> nodes) {
        for (Node childNode : nodes) {
            if (childNode.hasAttr("width")) {
                childNode.attr("width", String.valueOf(Integer.parseInt(childNode.attr("width")) * 2));
            }
            resetWidth(childNode.childNodes());
        }
    }
}
