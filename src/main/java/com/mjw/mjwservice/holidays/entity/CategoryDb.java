package com.mjw.mjwservice.holidays.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mjw.mjwservice.holidays.model.CategoryName;
import com.mjw.mjwservice.holidays.model.HasHoliday;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CATEGORY")
@Table(name = "CATEGORY", schema = "MJW_SERVICE")
public class CategoryDb implements HasHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryName name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOLIDAY_ID", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "fk_category_holiday"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "categories")
    private HolidayDb holiday;
}