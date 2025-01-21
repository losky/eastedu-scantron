package com.eastedu.graphics.context;

import com.eastedu.common.enums.QuestionTypeEnum;
import com.eastedu.graphics.enums.ContentPart;
import lombok.Getter;

import java.util.List;
import java.util.StringJoiner;

/**
 * The type Question item attribute.
 *
 * @author ZhenZhong
 */
@Getter
public class QuestionItemAttribute extends QuestionBaseAttribute {
    private final Long itemId;
    private final ContentPart contentPart;

    private final QuestionTypeEnum questionType;

    private final int order;
    private boolean autoCreateOrderAndIdentityFlag;

    private final List<Integer> subMaxWidths;

    /**
     * Instantiates a new Question item attribute.
     *
     * @param itemId                         the item id
     * @param questionType                   the question type
     * @param contentPart                    the content part
     * @param questionAttribute              the question attribute
     * @param order                          the order
     * @param autoCreateOrderAndIdentityFlag the auto create order and identity flag
     * @param subMaxWidths                   the sub max widths
     */
    public QuestionItemAttribute(Long itemId, QuestionTypeEnum questionType, ContentPart contentPart, QuestionBaseAttribute questionAttribute, int order, boolean autoCreateOrderAndIdentityFlag, List<Integer> subMaxWidths) {
        super(questionAttribute);
        this.itemId = itemId;
        this.contentPart = contentPart;
        this.questionType = questionType;
        this.order = order;
        this.autoCreateOrderAndIdentityFlag = autoCreateOrderAndIdentityFlag;
        this.subMaxWidths = subMaxWidths;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "Content [", "]")
                .add("itemId=" + itemId)
                .add("contentPart=" + contentPart)
                .toString();
    }


    @Override
    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }


    /**
     * Sets auto create order and identity flag.
     *
     * @param autoCreateOrderAndIdentityFlag the auto create order and identity flag
     */
    public void setAutoCreateOrderAndIdentityFlag(boolean autoCreateOrderAndIdentityFlag) {
        this.autoCreateOrderAndIdentityFlag = autoCreateOrderAndIdentityFlag;
    }

}
