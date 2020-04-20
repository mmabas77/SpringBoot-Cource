package com.mmabas77.web.controllers;

import com.mmabas77.backend.persistence.domain.backend.PasswordResetToken;
import com.mmabas77.backend.service.PasswordResetTokenService;
import com.mmabas77.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotMyPasswordController {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);

    public static final String EMAIL_ADDRESS_VIEW_NAME = "forgotpassword/emailform";

    public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgot-password";

    public static final String MAIL_SENT_KEY = "MAIL_SENT_KEY";

    public static final String CHANGE_PASSWORD_PATH = "/change-user-password";

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method = RequestMethod.GET)
    public String forgotPasswordGet() {
        return EMAIL_ADDRESS_VIEW_NAME;
    }

    @RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method = RequestMethod.POST)
    public String forgotPasswordPost(
            HttpServletRequest request,
            String email,
            ModelMap modelMap) {

        PasswordResetToken token =
                passwordResetTokenService.createPasswordResetTokenForEmail(email);
        if (token == null) {
            LOG.warn("Email {} Not Found", email);
        } else {
            String resetPasswordUrl = UserUtils.createPasswordResetUrl(
                    request,
                    token.getUser().getId(),
                    token.getToken()
            );
            LOG.warn("Reset Password Url : {}", resetPasswordUrl);
        }

        modelMap.addAttribute(MAIL_SENT_KEY, true);
        return EMAIL_ADDRESS_VIEW_NAME;
    }

}
