package com.mmabas77.test.integration;

import com.mmabas77.SpringBootCourseApplication;
import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.persistence.repositories.PlanRepository;
import com.mmabas77.backend.persistence.repositories.RoleRepository;
import com.mmabas77.backend.persistence.repositories.UserRepository;
import com.mmabas77.enums.PlansEnum;
import com.mmabas77.enums.RolesEnum;
import com.mmabas77.utils.UserUtils;
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
        Plan retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId()).get();
        Assert.notNull(retrievedPlan, "{RetrievedPlan} is null");
    }

    @Test
    public void testCreateNewRole() {
        Role basicRole = createBasicRole();
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId()).get();
        Assert.notNull(retrievedRole, "{RetrievedRole} is null");
    }

    @Test
    public void testCreateNewUser() {

        //----------Check User----------//
        User user = createBasicUser("createUser",
                "createUser@test.com");
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

    @Test
    public void testDeleteUser() {
        User user = createBasicUser("deleteUser","deleteUser@Test.com");
        userRepository.deleteById(user.getId());
    }

    //--------------->Private Methods
    private Plan createBasicPlan() {
        return new Plan(PlansEnum.BASIC);
    }

    private Role createBasicRole() {
        return new Role(RolesEnum.BASIC);
    }

    private User createBasicUser(String username, String email) {

        //----------Add User----------//
        User user = UserUtils.createBasicUser(username, email);

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
        UserRole userRole = new UserRole(user, role);
        //-Add To Relation->
        userRoles.add(userRole);

        //-Add Relation To User-//
        user.getUserRoles().addAll(userRoles);

        //----------Save User----------//
        user = userRepository.save(user);

        return user;
    }

}
