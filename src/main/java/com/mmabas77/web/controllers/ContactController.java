package com.mmabas77.web.controllers;

import com.mmabas77.web.domain.frontend.FeedbackPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactController {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

    private static final String FEEDBACK_MODEL_KEY = "FEEDBACK_MODEL_KEY";
    private static final String CONTACT_US_VIEW_NAME = "contact/contact";

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contactGet(ModelMap model) {
        FeedbackPojo feedbackPojo = new FeedbackPojo();
        model.addAttribute(FEEDBACK_MODEL_KEY, feedbackPojo);
        return CONTACT_US_VIEW_NAME;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) FeedbackPojo feedbackPojo) {
        LOG.info("Feedback Pojo : {}", feedbackPojo);
        return CONTACT_US_VIEW_NAME;
    }
}
