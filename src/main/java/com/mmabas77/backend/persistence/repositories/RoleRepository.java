package com.mmabas77.backend.persistence.repositories;

import com.mmabas77.backend.persistence.domain.backend.Role;
import com.mmabas77.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
