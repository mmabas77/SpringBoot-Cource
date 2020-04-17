package com.mmabas77;

import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.service.UserService;
import com.mmabas77.enums.PlansEnum;
import com.mmabas77.enums.RolesEnum;
import com.mmabas77.utils.UsersUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootCourseApplication implements CommandLineRunner {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(SpringBootCourseApplication.class);

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCourseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Set<UserRole> userRoles = new HashSet<>();
        User user = UsersUtils.createBasicUser();
        userRoles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
        LOG.debug("Creating User With Username : {}", user.getUsername());
        userService.createUser(user, PlansEnum.PRO, userRoles);
        LOG.debug("Created User : {}", user.getUsername());
    }
}
