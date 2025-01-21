package com.eastedu.graphics.context;

import com.eastedu.common.enums.QuestionTypeEnum;

import java.util.List;

/**
 * 试题属性
 *
 * @author ZhenZhong
 */
public interface QuestionAttribute {

    /**
     * 试题ID
     *
     * @return Long id
     */
    Long getId();

    /**
     * 试题名称
     *
     * @return String name
     */
    String getName();

    /**
     * 学科
     *
     * @return String subject
     */
    String getSubject();

    /**
     * 试题类型
     *
     * @return String question type
     */
    QuestionTypeEnum getQuestionType();

    /**
     * 是否为三方试题
     *
     * @return b boolean
     */
    boolean isThirdResource();

    /**
     * 学科类型
     *
     * @return String subject type
     */
    String getSubjectType();

    /**
     * 是否完型填空
     *
     * @return b boolean
     */
    boolean isFullBlanks();

    /**
     * 最大选项宽度
     *
     * @return String max widths
     */
    List<Integer> getMaxWidths();
}
