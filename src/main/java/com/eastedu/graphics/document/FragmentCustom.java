package com.eastedu.graphics.document;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Fragment custom.
 *
 * @author ZhenZhong
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FragmentCustom extends com.eastedu.common.model.question.Fragment {

    /**
     * Instantiates a new Fragment custom.
     */
    public FragmentCustom() {

    }

    /**
     * Instantiates a new Fragment custom.
     *
     * @param content the content
     */
    public FragmentCustom(String content) {
        super.setContent(content);
    }


}
