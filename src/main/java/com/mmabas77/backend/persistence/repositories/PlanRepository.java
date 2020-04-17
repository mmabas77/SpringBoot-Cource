package com.mmabas77.backend.persistence.repositories;

import com.mmabas77.backend.persistence.domain.backend.Plan;
import com.mmabas77.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends CrudRepository<Plan, Integer> {
}
