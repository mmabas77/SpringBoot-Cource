package com.mmabas77.utils;

import com.mmabas77.backend.persistence.domain.backend.User;

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
}
