package com.eastedu.graphics.core.content.segment;

import com.eastedu.common.model.question.Fragment;
import com.eastedu.exception.ServiceException;
import com.eastedu.graphics.core.content.merics.FormulaSegmentMetrics;
import com.eastedu.graphics.core.content.merics.SegmentMetrics;
import com.eastedu.graphics.domain.SvgParameter;
import com.eastedu.graphics.exception.InvalidContentException;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.layout.JEuclidView;
import org.w3c.dom.Document;

import java.awt.*;

/**
 * The type Formula segment.
 *
 * @author ZhenZhong
 */
public class FormulaSegment extends BaseSegment {

    private final JEuclidView jEuclidView;
    private final float fontSize = SvgParameter.get().getDefaultFontSize();

    /**
     * Instantiates a new Formula segment.
     *
     * @param row         the row
     * @param fragment    the fragment
     * @param canvasWidth the canvas width
     * @param graphics    the graphics
     */
    public FormulaSegment(int row, Fragment fragment, double canvasWidth, Graphics2D graphics) {
        super(row, fragment);
        if (canvasWidth <= 0) {
            throw new ServiceException("公式渲染的画布宽度必须大于0");
        }
        this.jEuclidView = initFormulaView(fragment, graphics, canvasWidth);
    }

    /**
     * 转半角的函数(DBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input 任意字符串
     * @return 半角字符串 string
     */
    public static String toDbc(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    private JEuclidView initFormulaView(Fragment fragment, Graphics2D graphics, double canvasWidth) {
        String content = fragment.getContent();
        try {
            String pipe = "|";
            String s = "&#x007C;";
            if (content.contains(pipe) || content.contains(s)) {
                String replacement = toSbc(pipe);
                content = content.replaceAll("[|]", replacement)
                        .replaceAll(s, replacement);
            }

            String leftShift = "<<";
            if (content.contains(leftShift)) {
                content = content.replaceAll(leftShift, "&lt;<");
            }
            String rightShift = ">>";
            if (content.contains(rightShift)) {
                content = content.replaceAll(rightShift, ">&gt;");
            }
            final Document doc = MathMLParserSupport
                    .parseString(content);
            final MutableLayoutContext params = new LayoutContextImpl(
                    LayoutContextImpl.getDefaultLayoutContext());

            params.setParameter(Parameter.MATHSIZE, fontSize);
            JEuclidView view = new JEuclidView(doc, params, graphics);
            int width = (int) Math.ceil(view.getWidth());
            if (width > canvasWidth) {
                double v = fontSize * canvasWidth / width;
                params.setParameter(Parameter.MATHSIZE, v);
                return new JEuclidView(doc, params, graphics);
            }

            return view;
        } catch (Exception e) {
            throw new InvalidContentException(e, "公式错误", content);
        }
    }

    @Override
    protected void doDraw(Graphics2D graphics) {
        jEuclidView.draw(graphics, 0, 0);
    }

    @Override
    public float getAscentHeight() {
        return super.getAscentHeight() + 8;
    }

    @Override
    protected SegmentMetrics initFontMetrics() {
        return new FormulaSegmentMetrics(jEuclidView);
    }

    @Override
    protected BaseSegment sub(String content) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    /**
     * 转全角的方法(SBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input 任意字符串
     * @return 半角字符串
     */
    private String toSbc(String input) {
        //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

}
