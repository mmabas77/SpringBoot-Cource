package com.mmabas77.backend.service;

import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.repositories.PlanRepository;
import com.mmabas77.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    public Plan findById(int id) {
        Optional<Plan> optionalPlan = planRepository.findById(id);
        Plan plan = optionalPlan.isPresent() ? optionalPlan.get() : null;
        return plan;
    }

    @Transactional
    public Plan createPlan(int id) {
        Plan plan = null;
        if (id == PlansEnum.BASIC.getId())
            plan = planRepository.save(new Plan(PlansEnum.BASIC));
        else if (id == PlansEnum.PRO.getId())
            plan = planRepository.save(new Plan(PlansEnum.PRO));
        else
            throw new IllegalArgumentException("Plan Id " + id + " not recognized");
        return plan;
    }

}
