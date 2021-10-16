package com.digital.oauth.service;

import com.digital.oauth.dto.LanguageDTO;
import com.digital.oauth.dto.TranslateDTO;
import com.digital.oauth.dto.TranslationsChangeDTO;
import com.digital.oauth.dto.TranslationsChangeItemDTO;
import com.digital.oauth.entity.Language;
import com.digital.oauth.exceptions.AppException;
import com.digital.oauth.repository.LanguageRepository;
import com.digital.oauth.specification.languages.LanguagesCriteriaRepository;
import com.digital.oauth.specification.languages.LanguagesPage;
import com.digital.oauth.specification.languages.LanguagesSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    LanguagesCriteriaRepository languagesCriteriaRepository;

    @Value("${languages.primaryLang}")
    String primaryLang;

    public HashMap<String, String> getLanguages(String lang) {
        HashMap<String, String> maps = new HashMap<>();
        List<Language> languages = languageRepository.findLanguageByLocale(lang);
        for (Language language : languages) {
            maps.put(language.getKey(), language.getContent());
        }
        return maps;
    }

    public Page<LanguageDTO> getList(LanguagesPage languagesPage,
                                     LanguagesSearchCriteria languagesSearchCriteria) {
        return languagesCriteriaRepository.findAllWithFilters(languagesPage, languagesSearchCriteria);
    }

    public Language translate(TranslateDTO translateDTO) {
        getOrCreate(translateDTO.getKey(), primaryLang);
        Language langTranslate = languageRepository.findByKeyAndLocale(translateDTO.getKey(), translateDTO.getLocale());
        if (langTranslate == null) {
            Language NewMessage = new Language();
            NewMessage.setKey(translateDTO.getKey());
            NewMessage.setLocale(translateDTO.getLocale());
            NewMessage.setContent(translateDTO.getTranslation());
            return languageRepository.save(NewMessage);
        }
        langTranslate.setContent(translateDTO.getTranslation());
        languageRepository.save(langTranslate);
        return langTranslate;
    }

    public Language getOrCreate(String key, String lang) {
        Language message = languageRepository.findByKeyAndLocale(key, lang);
        if (message == null) {
            message = languageRepository.findByKeyAndLocale(key, primaryLang);
            if (message == null) {
                Language NewMessage = new Language();
                NewMessage.setKey(key);
                NewMessage.setLocale(primaryLang);
                NewMessage.setContent(key);
                message = languageRepository.save(NewMessage);
            }
        }
        return message;
    }

    public List<Language> getTranslations(Language language) {
        return languageRepository.findLanguageByKeyAndLocaleNot(language.getKey(), language.getLocale());
    }

    public TranslationsChangeDTO changeTranslations(TranslationsChangeDTO translationsChangeDTO) throws AppException {
        if(translationsChangeDTO.getTranslations().isEmpty()){
            throw new AppException("Translations list empty");
        }

        List<TranslationsChangeItemDTO> translationsChangeItemDTOS = translationsChangeDTO.getTranslations();

        for (TranslationsChangeItemDTO translationsChangeItemDTO : translationsChangeItemDTOS) {
            Language language = languageRepository.findByKeyAndLocale(translationsChangeDTO.getKey(), translationsChangeItemDTO.getLocale());
            getOrCreate(translationsChangeDTO.getKey(),primaryLang);
            if (language != null) {
                language.setContent(translationsChangeItemDTO.getContent());
                languageRepository.save(language);
            } else {
                Language language1 = new Language();
                language1.setKey(translationsChangeDTO.getKey());
                language1.setLocale(translationsChangeItemDTO.getLocale());
                language1.setContent(translationsChangeItemDTO.getContent());
                languageRepository.save(language1);
            }
        }

        return translationsChangeDTO;
    }
}
