package com.mmabas77.backend.service.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class I18NService {

    /* The Application Logger*/
    private static final Logger LOG =  LoggerFactory.getLogger(I18NService.class);

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String msgId) {
        LOG.info("Returning i18n text for messageId : {}",msgId);
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(msgId, locale);
    }

    public String getMessage(String msgId, Locale locale) {
        return messageSource.getMessage(msgId, null, locale);
    }

}
