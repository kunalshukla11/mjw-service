package com.mjw.mjwservice.holidays.repository;

import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.holidays.entity.HolidayThemeDb;
import com.mjw.mjwservice.holidays.entity.HolidayDb;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class HolidaySpecification {

    public static Specification<HolidayDb> findByCriteria(final HolidaySearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            // Join with LocationDb if any location criteria is present
            if (StringUtils.hasText(request.cityCode()) || StringUtils.hasText(request.stateCode())
                    || StringUtils.hasText(request.countryCode())) {
                final Join<HolidayDb, LocationDb> locationJoin = root.join("location");

                if (StringUtils.hasText(request.cityCode())) {
                    predicates.add(criteriaBuilder.equal(locationJoin.get("cityCode"), request.cityCode()));
                }
                if (StringUtils.hasText(request.stateCode())) {
                    predicates.add(criteriaBuilder.equal(locationJoin.get("stateCode"), request.stateCode()));
                }
                if (StringUtils.hasText(request.countryCode())) {
                    predicates.add(criteriaBuilder.equal(locationJoin.get("countryCode"), request.countryCode()));
                }
            }

            // Join with HolidayThemeDb if any theme criteria is present
            if (request.theme() != null) {
                final Join<HolidayDb, HolidayThemeDb> holidayThemeDbJoin = root.join("holidayThemes");
                predicates.add(criteriaBuilder.equal(holidayThemeDbJoin.get("theme"), request.theme()));
            }

            // Combine predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
