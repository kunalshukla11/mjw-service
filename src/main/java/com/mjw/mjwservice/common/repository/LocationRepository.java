package com.mjw.mjwservice.common.repository;

import com.mjw.mjwservice.common.entity.LocationDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<LocationDb, Long> {

    @Query(value = "SELECT images_url FROM mjw_service.location WHERE city_code = :cityCode AND state_code = :stateCode AND country_code = :countryCode", nativeQuery = true)
    String[] getImagesUrlByCityStateCountry(@Param("cityCode") String cityCode,
                                            @Param("stateCode") String stateCode,
                                            @Param("countryCode") String countryCode);

    @Query("select distinct i from LOCATION l join l.imagesUrl i where l.stateCode = :stateCode and l.countryCode = :countryCode")
    Set<String> getImagesUrlByStateCodeAndCountryCode(@Param("stateCode") String stateCode,
                                                      @Param("countryCode") String countryCode);

    @Query("select distinct i from LOCATION l join l.imagesUrl i where l.countryCode = :countryCode")
    Set<String> getImagesUrlByCountryCode(@Param("countryCode") String countryCode);

}
