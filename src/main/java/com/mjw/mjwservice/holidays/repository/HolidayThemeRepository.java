package com.mjw.mjwservice.holidays.repository;

import com.mjw.mjwservice.holidays.entity.HolidayThemeDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayThemeRepository extends JpaRepository<HolidayThemeDb, Long> {
}
