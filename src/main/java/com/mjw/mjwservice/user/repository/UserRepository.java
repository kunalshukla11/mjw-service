package com.mjw.mjwservice.user.repository;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfoDatabaseImpl, Long> {
}
