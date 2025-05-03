package com.mjw.mjwservice.holidays.entity;

import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.holidays.model.ItineraryDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ITINERARY")
@Table(name = "ITINERARY", schema = "MJW_SERVICE")
public class ItineraryDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IDENTIFIER")
    private String identifier;


    @ManyToOne(targetEntity = LocationDb.class)
    @JoinColumn(
            name = "LOCATION_ID",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_itinerary_location"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LocationDb location;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "ITINERARY_DETAIL", columnDefinition = "JSONB")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = ItineraryDetailConverter.class)
    private ItineraryDetail itineraryDetail;


}
