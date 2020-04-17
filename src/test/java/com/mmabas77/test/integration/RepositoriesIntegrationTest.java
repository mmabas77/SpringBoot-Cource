package com.mmabas77.test.integration;

import com.mmabas77.SpringBootCourseApplication;
import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.persistence.repositories.PlanRepository;
import com.mmabas77.backend.persistence.repositories.RoleRepository;
import com.mmabas77.backend.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@SpringJUnitConfig(classes = SpringBootCourseApplication.class)
public class RepositoriesIntegrationTest {

    private static final Integer BASIC_PLAN_ID = 1;
    private static final Integer BASIC_ROLE_ID = 1;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PlanRepository planRepository;

    @BeforeEach
    public void init() {
        Assert.notNull(userRepository, "{UserRepository} is null");
        Assert.notNull(roleRepository, "{RoleRepository} is null");
        Assert.notNull(planRepository, "{PlanRepository} is null");
    }

    @Test
    public void testCreateNewPlan() {
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findById(BASIC_PLAN_ID).get();
        Assert.notNull(retrievedPlan, "{RetrievedPlan} is null");
    }

    @Test
    public void testCreateNewRole() {
        Role basicRole = createBasicRole();
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findById(BASIC_ROLE_ID).get();
        Assert.notNull(retrievedRole, "{RetrievedRole} is null");
    }

    @Test
    public void testCreateNewUser() {
        //----------Add User----------//
        User user = createBasicUser();

        //----------Add Plan----------//
        Plan plan = createBasicPlan();
        planRepository.save(plan);
        user.setPlan(plan);

        //----------Add Roles----------//

        //-Add One Role->
        Role role = createBasicRole();
        //-Add Relation->
        Set<UserRole> userRoles = new HashSet<>();
        //-Combine     ->
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        //-Add To Relation->
        userRoles.addAll(userRoles);
        //Add Roles To DB->
        for (var toDbUserRole : userRoles) {
            roleRepository.save(toDbUserRole.getRole());
        }

        //-Add Relation To User-//
        user.getUserRoles().addAll(userRoles);

        //----------Save User----------//
        user = userRepository.save(user);
        //----------Check User----------//
        User toCheck = userRepository.findById(user.getId()).get();
        Assert.notNull(toCheck, "User Is Null");
        Assert.isTrue(toCheck.getId() == user.getId()
                , "ID Is Not The Same");
        Assert.notNull(toCheck.getPlan(), "Plan Is Null");
        Assert.notNull(toCheck.getPlan().getId(), "Plan ID Is Null");
        Set<UserRole> toCheckRoles = toCheck.getUserRoles();
        for (var toCheckRole : toCheckRoles) {
            Assert.notNull(toCheckRole.getRole(), "Role Is Null");
            Assert.notNull(toCheckRole.getRole().getId(), "Role ID Is Null");
        }
    }

    //--------------->Private Methods
    private Plan createBasicPlan() {
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");
        return plan;
    }

    private Role createBasicRole() {
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("Basic");
        return role;
    }

    private User createBasicUser() {
        User user = new User();
        user.setUsername("UserName");
        user.setStripeCustomerId("stripe-id");
        user.setProfileImageUrl("img/url.img");
        user.setPhoneNumber("+0123456789");
        user.setPassword("password");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEnabled(true);
        user.setEmail("user@email.com");
        user.setDescription("User Description!");
        user.setCountry("EGP");
        return user;
    }
}
