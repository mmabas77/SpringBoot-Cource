package com.mmabas77.utils;

import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.web.controllers.ForgotMyPasswordController;
import com.mmabas77.web.domain.frontend.BasicAccountPayload;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    private UserUtils() {
        throw new AssertionError("UsersUtils Is Not Instantiable");
    }

    public static User createBasicUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setStripeCustomerId("stripe-id");
        user.setProfileImageUrl("img/url.img");
        user.setPhoneNumber("+0123456789");
        user.setPassword("password");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEnabled(true);
        user.setEmail(email);
        user.setDescription("User Description!");
        user.setCountry("EGP");
        return user;
    }

    public static String createPasswordResetUrl(HttpServletRequest request,
                                                int userId,
                                                String token) {
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                "?id=" + userId +
                "&token=" + token;
    }

    public static <T extends BasicAccountPayload>
    User fromWebToDomainUser(T frontEndPayload) {

        User user = new User();
        user.setUsername(frontEndPayload.getUsername());
        user.setPhoneNumber(frontEndPayload.getPhone());
        user.setPassword(frontEndPayload.getPassword());
        user.setLastName(frontEndPayload.getLname());
        user.setFirstName(frontEndPayload.getFname());
        user.setEmail(frontEndPayload.getEmail());
        user.setDescription(frontEndPayload.getDescription());
        user.setCountry(frontEndPayload.getCountry());

        user.setEnabled(true);

        return user;
    }
}
