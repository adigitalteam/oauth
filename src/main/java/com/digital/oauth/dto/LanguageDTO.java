package com.digital.oauth.dto;

import com.digital.oauth.entity.Language;
import lombok.Data;

import java.util.List;

@Data
public class LanguageDTO {
    Long id;
    String locale;
    String key;
    String content;
    List<Language> translations;
}
