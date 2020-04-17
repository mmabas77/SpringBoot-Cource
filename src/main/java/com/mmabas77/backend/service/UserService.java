package com.mmabas77.backend.service;

import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.persistence.repositories.PlanRepository;
import com.mmabas77.backend.persistence.repositories.RoleRepository;
import com.mmabas77.backend.persistence.repositories.UserRepository;
import com.mmabas77.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PlanRepository planRepository;

    @Transactional
    public User createUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {
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
        //Save User//
        user = userRepository.save(user);
        return user;
    }
}
