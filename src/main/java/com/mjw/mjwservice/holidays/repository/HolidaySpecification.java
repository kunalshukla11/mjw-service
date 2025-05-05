package com.mjw.mjwservice.holidays.repository;

import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.holidays.entity.CategoryDb;
import com.mjw.mjwservice.holidays.entity.HolidayDb;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HolidaySpecification {
    public static Specification<HolidayDb> findByCriteria(final HolidaySearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            // Join with LocationDb if any location criteria is present
            if (StringUtils.hasText(request.cityCode()) || StringUtils.hasText(request.stateCode()) || StringUtils.hasText(request.countryCode())) {
                Join<HolidayDb, LocationDb> locationJoin = root.join("location");

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

            // Join with CategoryDb if categoryName is present
            if (request.categoryName() != null) {
                Join<HolidayDb, CategoryDb> categoryJoin = root.join("categories");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("name"), request.categoryName()));
            }

            // Combine predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
