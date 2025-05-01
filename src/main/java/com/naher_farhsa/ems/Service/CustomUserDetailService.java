package com.naher_farhsa.ems.Service;

import com.naher_farhsa.ems.Entity.User;
import com.naher_farhsa.ems.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getUserPassword())
                    .roles(user.getUserRole().replace("ROLE_", "")) // e.g., ADMIN/STUDENT
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + userName);
    }
}
