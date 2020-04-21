package com.mmabas77.web.controllers;

import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.domain.backend.UserRole;
import com.mmabas77.backend.service.PlanService;
import com.mmabas77.backend.service.UserService;
import com.mmabas77.enums.PlansEnum;
import com.mmabas77.enums.RolesEnum;
import com.mmabas77.utils.UserUtils;
import com.mmabas77.web.domain.frontend.BasicAccountPayload;
import com.mmabas77.web.domain.frontend.ProAccountPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class SignUpController {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    UserService userService;
    @Autowired
    PlanService planService;

    public static final String SIGN_UP_URL_MAPPING = "/sign-up";
    private static final String SIGN_UP_VIEW_NAME = "registration/signup";
    public static final String PAYLOAD_KEY = "PAYLOAD_KEY";

    /* Keys */
    public static final String ERROR = "ERROR";
    public static final String MESSAGE = "MESSAGE";
    public static final String SUCCESS = "SUCCESS";


    @GetMapping(SIGN_UP_URL_MAPPING)
    public String signUpGet(
            @RequestParam("planId") int planId,
            ModelMap modelMap) {

        if (!(planId == PlansEnum.BASIC.getId() ||
                planId == PlansEnum.PRO.getId())) {
            throw new IllegalArgumentException("Provide A Valid Plan Id");
        }

        modelMap.addAttribute(PAYLOAD_KEY, new ProAccountPayload());

        return SIGN_UP_VIEW_NAME;
    }


    @PostMapping(SIGN_UP_URL_MAPPING)
    public String signUpPost(
            @RequestParam(value = "planId", required = true) int planId,

            @ModelAttribute(PAYLOAD_KEY)
            @Valid ProAccountPayload proAccountPayload,

            ModelMap modelMap) {

        if (planId != PlansEnum.BASIC.getId() && planId != PlansEnum.PRO.getId()) {
            modelMap.addAttribute(ERROR, true);
            modelMap.addAttribute(MESSAGE,
                    "Plan id : " + planId + "not exist!");
            return SIGN_UP_VIEW_NAME;

        } else if (checkForDuplicates(proAccountPayload, modelMap)) {
            return SIGN_UP_VIEW_NAME;
        }

        User user = UserUtils.fromWebToDomainUser(proAccountPayload);
        Plan plan = planService.findById(planId);
        if (plan == null) {
            modelMap.addAttribute(ERROR, true);
            modelMap.addAttribute(MESSAGE, "Plan Not Found");
            return SIGN_UP_VIEW_NAME;
        }
        user.setPlan(plan);

        User registeredUser = null;
        Set<UserRole> userRoles = new HashSet<>();
        if (planId == PlansEnum.BASIC.getId()) {
            userRoles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
            registeredUser = userService.createUser(user, PlansEnum.BASIC, userRoles);
        } else if (planId == PlansEnum.PRO.getId()) {
            userRoles.add(new UserRole(user, new Role(RolesEnum.PRO)));
            registeredUser = userService.createUser(user, PlansEnum.PRO, userRoles);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                registeredUser, null, registeredUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        modelMap.addAttribute(SUCCESS, true);
        return SIGN_UP_VIEW_NAME;
    }

    //----------------------------------->Private Utility
    private boolean checkForDuplicates(BasicAccountPayload basicAccountPayload,
                                       ModelMap modelMap) {
        if (userService.findByUsername(basicAccountPayload.getUsername()) != null) {
            modelMap.addAttribute(ERROR, true);
            modelMap.addAttribute(MESSAGE, "Username Already Used!");
            return true;
        }
        if (userService.findByEmail(basicAccountPayload.getEmail()) != null) {
            modelMap.addAttribute(ERROR, true);
            modelMap.addAttribute(MESSAGE, "Email Already Used!");
            return true;
        }
        return false;
    }

}
