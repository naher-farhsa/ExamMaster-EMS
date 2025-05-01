package com.naher_farhsa.ems.Repository;

import com.naher_farhsa.ems.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String userName);
}
