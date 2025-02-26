package com.mjw.mjwservice.holidays.repository;

import com.mjw.mjwservice.holidays.entity.ItineraryDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<ItineraryDb, Long> {

}
