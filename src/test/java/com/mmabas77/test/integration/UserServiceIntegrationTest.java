package com.mmabas77.test.integration;

import com.mmabas77.SpringBootCourseApplication;
import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.service.UserService;
import com.mmabas77.enums.PlansEnum;
import com.mmabas77.enums.RolesEnum;
import com.mmabas77.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@SpringJUnitConfig(classes = SpringBootCourseApplication.class)
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testCreateNewUser(){
        Set<UserRole> userRoles = new HashSet<>();
        User basicUser = UserUtils.createBasicUser();
        userRoles.add(new UserRole(basicUser,new Role(RolesEnum.BASIC)));

        User user = userService.createUser(basicUser, PlansEnum.BASIC,userRoles);
        Assert.notNull(user,"User Is Null");
        Assert.notNull(user.getId(),"User ID Is Null");
    }
}
