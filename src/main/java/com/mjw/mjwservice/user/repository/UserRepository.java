package com.mjw.mjwservice.user.repository;

import com.mjw.mjwservice.user.entity.UserInfoDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfoDb, Long> {

    Optional<UserInfoDb> findByEmail(String email);

}
