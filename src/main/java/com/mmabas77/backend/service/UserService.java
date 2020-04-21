package com.mmabas77.backend.service;

import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.persistence.repositories.PlanRepository;
import com.mmabas77.backend.persistence.repositories.RoleRepository;
import com.mmabas77.backend.persistence.repositories.UserRepository;
import com.mmabas77.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {

        //BCrypt The Password//
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Save User//
        user = userRepository.save(user);

        //Set Plan//
        Plan plan = new Plan(plansEnum);
        if (!planRepository.existsById(plansEnum.getId())) {
            plan = planRepository.save(plan);
        }
        user.setPlan(plan);

        //Set Roles//
        for (UserRole userRole : userRoles) {
            roleRepository.save(userRole.getRole());
        }
        user.getUserRoles().addAll(userRoles);

        //Update User//
        user = userRepository.save(user);

        return user;
    }

    @Transactional
    public void updateUserPassword(User user, String password) {
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        userRepository.save(user);
        LOG.debug("Updated Password For User : {}", user.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
