package com.mjw.mjwservice.holidays.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.common.model.Currency;
import com.mjw.mjwservice.holidays.model.HasHoliday;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "HOLIDAY")
@Table(name = "HOLIDAY", schema = "MJW_SERVICE")
public class HolidayDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "fk_holiday_location"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LocationDb location;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_holiday_itinerary"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItineraryDb itinerary;

    @JsonManagedReference(value = "categories")
    @OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    //@OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CategoryDb> categories;

    @Column(name = "STANDARD_PRICE")
    private BigDecimal standardPrice;

    @Column(name = "SUPERIOR_PRICE")
    private BigDecimal superiorPrice;

    @Column(name = "LUXURY_PRICE")
    private BigDecimal luxuryPrice;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public HolidayDb addCategories(final Set<CategoryDb> categories) {
        this.categories = categories;
        return fixSet(this.categories);
    }

    private <T extends HasHoliday> HolidayDb fixSet(final Set<T> categories) {
        return Optional.of(categories).filter(CollectionUtils::isNotEmpty).map(category -> {
            category.forEach(c -> c.setHoliday(this));
            return this;
        }).orElse(this);
    }


}
