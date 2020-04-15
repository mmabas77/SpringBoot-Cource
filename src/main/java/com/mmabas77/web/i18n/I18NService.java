package com.mmabas77.web.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class I18NService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String msgId) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(msgId, locale);
    }

    public String getMessage(String msgId, Locale locale) {
        return messageSource.getMessage(msgId, null, locale);
    }

}
