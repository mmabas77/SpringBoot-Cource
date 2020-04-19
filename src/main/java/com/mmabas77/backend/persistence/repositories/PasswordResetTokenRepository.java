package com.mmabas77.backend.persistence.repositories;

import com.mmabas77.backend.persistence.domain.backend.PasswordResetToken;
import com.mmabas77.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken,
        Integer> {

    PasswordResetToken findByToken(String token);

    Set<PasswordResetToken> findAllByUserId(int user_id);

}
