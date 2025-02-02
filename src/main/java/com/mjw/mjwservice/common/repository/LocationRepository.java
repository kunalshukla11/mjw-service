package com.mjw.mjwservice.common.repository;

import com.mjw.mjwservice.common.entity.LocationDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationDb, Long> {

}
