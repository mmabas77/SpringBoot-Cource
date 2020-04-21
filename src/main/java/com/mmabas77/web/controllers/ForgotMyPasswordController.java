package com.mmabas77.web.controllers;

import com.mmabas77.backend.persistence.domain.backend.PasswordResetToken;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.service.PasswordResetTokenService;
import com.mmabas77.backend.service.UserService;
import com.mmabas77.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
public class ForgotMyPasswordController {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);

    public static final String EMAIL_ADDRESS_VIEW_NAME = "forgotpassword/emailform";

    public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgot-password";

    public static final String MAIL_SENT_KEY = "MAIL_SENT_KEY";

    public static final String CHANGE_PASSWORD_PATH = "/change-user-password";

    public static final String CHANGE_PASSWORD_VIEW = "forgotpassword/changePassword";


    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    UserService userService;

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

    @RequestMapping(value = CHANGE_PASSWORD_PATH, method = RequestMethod.GET)
    public String changeUserPasswordGet(@RequestParam("id") int id,
                                        @RequestParam("token") String token,
                                        Locale locale,
                                        ModelMap modelMap) {
        PasswordResetToken resetTokenObj = passwordResetTokenService
                .findByToken(token);
        if (!(
                resetTokenObj.getUser().getId() == id &&
                        resetTokenObj.getToken().equals(token) &&
                        LocalDateTime.now(Clock.systemUTC())
                                .isBefore(resetTokenObj.getExpireDate())
        )) {
            //Not Valid//
            modelMap.addAttribute("ERROR", true);
        } else {
            //Is Valid//
            modelMap.addAttribute("principal_id", id);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            resetTokenObj.getUser(),
                            null,
                            resetTokenObj.getUser().getAuthorities()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        }

        return CHANGE_PASSWORD_VIEW;
    }

    @RequestMapping(value = CHANGE_PASSWORD_PATH, method = RequestMethod.POST)
    public String changeUserPasswordPost(
            @RequestParam("principal_id") int principal_id,
            @RequestParam("password") String password,
            ModelMap modelMap
    ) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return EMAIL_ADDRESS_VIEW_NAME;
        }

        User user = (User) authentication.getPrincipal();
        if (user.getId() != principal_id) {
            throw new Exception("You Are Not The User!");
        }

        userService.updateUserPassword(user, password);
        modelMap.addAttribute("SUCCESS", true);

        return CHANGE_PASSWORD_VIEW;
    }

}
