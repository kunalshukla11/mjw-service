package com.mjw.mjwservice.holidays.repository;

import com.mjw.mjwservice.holidays.entity.CategoryDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDb, Long> {
}
