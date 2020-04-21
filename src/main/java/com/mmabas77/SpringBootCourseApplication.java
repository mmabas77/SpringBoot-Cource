package com.mmabas77;

import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.service.PlanService;
import com.mmabas77.backend.service.UserService;
import com.mmabas77.enums.PlansEnum;
import com.mmabas77.enums.RolesEnum;
import com.mmabas77.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootCourseApplication implements CommandLineRunner {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(SpringBootCourseApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCourseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        LOG.info("Adding Plans To DB");
        planService.createPlan(PlansEnum.BASIC.getId());
        planService.createPlan(PlansEnum.PRO.getId());

        Set<UserRole> userRoles = new HashSet<>();
        User user = UserUtils.createBasicUser("onrun", "onrun@email.com");
        userRoles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
        LOG.debug("Creating User With Username : {}", user.getUsername());
        userService.createUser(user, PlansEnum.PRO, userRoles);
        LOG.debug("Created User : {}", user.getUsername());
    }
}
