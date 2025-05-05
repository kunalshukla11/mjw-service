package com.mjw.mjwservice.holidays.repository;

import com.mjw.mjwservice.holidays.entity.HolidayDb;
import com.mjw.mjwservice.holidays.entity.LocationPriceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayDb, Long>, JpaSpecificationExecutor<HolidayDb> {

    @Query(value = """
             SELECT 'CITY' as type, MIN(h.standard_price) as standard_price ,
                       COALESCE(l.city, '') as display_name,
                       COALESCE(l.city_code , '') as city_code,
                       COALESCE(l.state_code , '') as state_code,
                       COALESCE(l.country_code, '') as country_code
                FROM mjw_service.holiday h
                JOIN mjw_service.location l ON h.location_id = l.id
                WHERE CONCAT('CITY', '-', l.city_code, '-', l.state_code, '-', l.country_code) IN (:cityKeys)
                GROUP BY l.city, l.city_code ,l.state_code , l.country_code
            """, nativeQuery = true)
    List<LocationPriceProjection> findLowestPriceByCity(@Param("cityKeys") List<String> cityKeys);


    @Query(value = """
            SELECT 'STATE' as type, MIN(h.standard_price) as standard_price ,
                        COALESCE(l.state, '') as display_name,
                       COALESCE(l.state_code, '') as state_code,
                       COALESCE(l.country_code, '') as country_code
                FROM mjw_service.holiday h
                JOIN mjw_service.location l ON h.location_id = l.id
                WHERE CONCAT('STATE', '-', l.state_code, '-', l.country_code) IN (:stateKeys)
                GROUP BY l.state, l.state_code, l.country_code
                """, nativeQuery = true)
    List<LocationPriceProjection> findLowestPriceByState(@Param("stateKeys") List<String> stateKeys);


    @Query(value = """
             SELECT 'COUNTRY' as type, MIN(h.standard_price) as standard_price,
                        COALESCE(l.country, '') as display_name,
                        COALESCE(l.country_code, '') as country_code
                  FROM mjw_service.holiday h
                  JOIN mjw_service.location l ON h.location_id = l.id
                  WHERE CONCAT('COUNTRY', '-', l.country_code) IN (:countryKeys)
                  GROUP BY  l.country , l.country_code
            """, nativeQuery = true)
    List<LocationPriceProjection> findLowestPriceByCountry(@Param("countryKeys") List<String> countryKeys);

    List<HolidayDb> findAllByIdIn(Set<Long> holidayIds);

}
