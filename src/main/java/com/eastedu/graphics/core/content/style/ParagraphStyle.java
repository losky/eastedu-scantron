package com.eastedu.graphics.core.content.style;

import com.eastedu.common.enums.AlignmentTypeEnum;
import com.eastedu.graphics.enums.ListPatternType;
import lombok.Data;

import java.util.Objects;

/**
 * The type Paragraph style.
 *
 * @author ZhenZhong
 */
@Data
public class ParagraphStyle {
    /**
     * 首行缩进
     */
    private float indentation = 0;
    private float listIndentation = 0;
    private AlignmentTypeEnum alignment = AlignmentTypeEnum.Left;
    private boolean list = false;
    private char listSymbol = 0;
    private int listGroup = 0;
    private ListPatternType listPatternType = ListPatternType.NONE;
    /**
     * 行距
     */
    private float lineSpace = 0;

    /**
     * Gets alignment.
     *
     * @return the alignment
     */
    public AlignmentTypeEnum getAlignment() {
        if (Objects.isNull(alignment)) {
            this.alignment = AlignmentTypeEnum.Left;
        }
        return alignment;
    }
}
