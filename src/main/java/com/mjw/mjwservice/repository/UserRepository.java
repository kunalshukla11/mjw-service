package com.mjw.mjwservice.repository;

import com.mjw.mjwservice.entity.UserInfoDatabaseImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfoDatabaseImpl, Long> {}
