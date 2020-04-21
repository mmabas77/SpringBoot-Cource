package com.mmabas77.test.unit;


import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.utils.UserUtils;
import com.mmabas77.web.controllers.ForgotMyPasswordController;
import com.mmabas77.web.domain.frontend.BasicAccountPayload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.UUID;

/**
 * Created by tedonema on 15/04/2016.
 */
public class UserUtilsUnitTest {

    private MockHttpServletRequest mockHttpServletRequest;

    private PodamFactory factory;

    @Before
    public void init() {
        mockHttpServletRequest = new MockHttpServletRequest();
        factory = new PodamFactoryImpl();
    }

    @Test
    public void testPasswordResetEmailUrlConstruction() throws Exception {

        mockHttpServletRequest.setServerPort(8080); //Default is 80

        String token = UUID.randomUUID().toString();
        int userId = 123456;

        String expectedUrl = "http://localhost:8080" +
                ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" + userId + "&token=" + token;

        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId, token);

        Assert.assertEquals(expectedUrl, actualUrl);

    }

    @Test
    public void mapWebUserToDomainUser() {
        BasicAccountPayload webUser =
                factory.manufacturePojoWithFullData(BasicAccountPayload.class);
        webUser.setEmail("webuser@email.com");

        User user = UserUtils.fromWebToDomainUser(webUser);

        Assert.assertNotNull(user);
        Assert.assertEquals(webUser.getUsername(), user.getUsername());
        Assert.assertEquals(webUser.getPhone(), user.getPhoneNumber());
        Assert.assertEquals(webUser.getPassword(), user.getPassword());
        Assert.assertEquals(webUser.getLname(), user.getLastName());
        Assert.assertEquals(webUser.getFname(), user.getFirstName());
        Assert.assertEquals(webUser.getEmail(), user.getEmail());
        Assert.assertEquals(webUser.getDescription(), user.getDescription());
        Assert.assertEquals(webUser.getCountry(), user.getCountry());

    }

}