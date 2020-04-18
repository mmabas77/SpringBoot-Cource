package com.mmabas77.backend.service;

import com.mmabas77.backend.persistence.domain.backend.User;
import com.mmabas77.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOG.warn("User {} Not Found!", username);
            throw new UsernameNotFoundException("User " + username + "Not Found!");
        }
        return user;
    }
}
