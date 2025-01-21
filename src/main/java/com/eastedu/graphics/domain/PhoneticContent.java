package com.eastedu.graphics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * The type Phonetic content.
 *
 * @author luozz
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneticContent {
    private boolean matts;
    private String phonetic;
    private String word;
}