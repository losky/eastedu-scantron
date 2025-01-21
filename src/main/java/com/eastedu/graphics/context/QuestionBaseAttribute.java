package com.eastedu.graphics.context;

import com.eastedu.common.enums.QuestionTypeEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.eastedu.common.enums.SubjectQuestionTypeEnum.FILL_BLANKS;
import static com.eastedu.common.enums.SubjectQuestionTypeEnum.LISTEN;

/**
 * 试题属性
 *
 * @author ZhenZhong
 */
@Getter
public class QuestionBaseAttribute implements QuestionAttribute {
    private final Long id;
    private final String name;
    private final String subject;
    /**
     * 学科题型
     */
    private final String subjectType;
    private final QuestionTypeEnum questionType;
    private final boolean thirdResource;

    /**
     * 最大段落宽度（选项或者答案）
     */
    private List<Integer> maxWidths;

    /**
     * Instantiates a new Question attribute.
     *
     * @param id            the id
     * @param name          the name
     * @param subject       the subject
     * @param subjectType   the subject type
     * @param questionType  the question type
     * @param thirdResource the third resource
     */
    public QuestionBaseAttribute(Long id, String name, String subject, String subjectType, QuestionTypeEnum questionType, boolean thirdResource) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.subjectType = subjectType;
        this.questionType = questionType;
        this.thirdResource = thirdResource;
    }

    /**
     * Instantiates a new Question attribute.
     *
     * @param questionAttribute the question attribute
     */
    public QuestionBaseAttribute(QuestionBaseAttribute questionAttribute) {
        this.id = questionAttribute.getId();
        this.name = questionAttribute.getName();
        this.subject = questionAttribute.getSubject();
        this.subjectType = questionAttribute.getSubjectType();
        this.questionType = questionAttribute.getQuestionType();
        this.thirdResource = questionAttribute.isThirdResource();
        this.maxWidths = questionAttribute.getMaxWidths();
    }

    /**
     * 是否完型填空
     *
     * @return b
     */
    @Override
    public boolean isFullBlanks() {
        return (StringUtils.isNotBlank(subjectType) && (subjectType.equals(FILL_BLANKS.getValue()) || subjectType.equals(LISTEN.getValue())));
    }

    @Override
    public List<Integer> getMaxWidths() {
        if (Objects.isNull(maxWidths)) {
            this.maxWidths = Collections.emptyList();
        }
        return maxWidths;
    }

    /**
     * Sets max widths.
     *
     * @param maxWidths the max widths
     */
    public void setMaxWidths(List<Integer> maxWidths) {
        this.maxWidths = maxWidths;
    }
}
