package com.digital.oauth.controller;

import com.digital.oauth.dto.LanguageDTO;
import com.digital.oauth.dto.TranslateDTO;
import com.digital.oauth.dto.TranslationsChangeDTO;
import com.digital.oauth.entity.Language;
import com.digital.oauth.exceptions.AppException;
import com.digital.oauth.repository.LanguageRepository;
import com.digital.oauth.service.LanguageService;
import com.digital.oauth.specification.languages.LanguagesPage;
import com.digital.oauth.specification.languages.LanguagesSearchCriteria;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/language")
public class LanguageController {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    LanguageService languageService;


    @Value("${languages.primaryLang}")
    String primaryLang;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, String> list() {
        return languageService.getLanguages(primaryLang);
    }


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<LanguageDTO> listFull(LanguagesPage languagesPage,
                                      LanguagesSearchCriteria languagesSearchCriteria) {
        return languageService.getList(languagesPage, languagesSearchCriteria);
    }


    @GetMapping("/{lang}")
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, String> list(@PathVariable(required = false) String lang) {
        return languageService.getLanguages(lang);
    }

    @PostMapping("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> create(@RequestParam HashMap<String, String> languages) {
        for (Map.Entry<String, String> language : languages.entrySet()) {
            String key = language.getKey();
            String value = language.getValue();
            try {
                if (languageRepository.findByKeyAndLocale(key, "en") != null) {
                    continue;
                }
            } catch (IncorrectResultSizeDataAccessException exception) {
                continue;
            }
            Language NewMessage = new Language();
            NewMessage.setKey(key);
            NewMessage.setLocale("en");
            NewMessage.setContent(value);
            languageRepository.save(NewMessage);
        }
        return languages;
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/translate")
    @ResponseStatus(HttpStatus.OK)
    public Language translate(@RequestBody TranslateDTO translateDTO) {
        return languageService.translate(translateDTO);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/translates")
    @ResponseStatus(HttpStatus.OK)
    public TranslationsChangeDTO translates(@RequestBody TranslationsChangeDTO translationsChangeDTO) throws AppException {
        return languageService.changeTranslations(translationsChangeDTO);
    }


}
