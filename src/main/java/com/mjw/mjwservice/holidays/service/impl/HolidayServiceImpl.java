package com.mjw.mjwservice.holidays.service.impl;

import com.mjw.mjwservice.common.model.dashboard.HolidayDashboard;
import com.mjw.mjwservice.common.model.dashboard.Section;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardConfig;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardData;
import com.mjw.mjwservice.common.repository.LocationRepository;
import com.mjw.mjwservice.common.service.DashboardConfigService;
import com.mjw.mjwservice.common.service.ReviewService;
import com.mjw.mjwservice.common.utility.Utils;
import com.mjw.mjwservice.holidays.entity.HolidayDb;
import com.mjw.mjwservice.holidays.entity.LocationPriceProjection;
import com.mjw.mjwservice.holidays.mapper.HolidayMapper;
import com.mjw.mjwservice.holidays.model.Holiday;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;
import com.mjw.mjwservice.holidays.model.Itinerary;
import com.mjw.mjwservice.holidays.repository.CategoryRepository;
import com.mjw.mjwservice.holidays.repository.HolidayRepository;
import com.mjw.mjwservice.holidays.repository.HolidaySpecification;
import com.mjw.mjwservice.holidays.service.HolidayService;
import com.mjw.mjwservice.holidays.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mjw.mjwservice.common.model.dashboard.Section.HERO_SECTION;

@Service
@RequiredArgsConstructor
@Log4j2
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final ItineraryService itineraryService;
    private final HolidayMapper holidayMapper;
    private final ReviewService reviewService;
    private final DashboardConfigService dashboardConfigService;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Holiday save(final Holiday holiday) {
        log.info("save holiday: {}", holiday);
        final HolidayDb savedHolidayDb = Optional.ofNullable(holiday.itinerary())
                .map(Itinerary::id)
                .map(itineraryService::getItineraryById)
                .or(() -> Optional.of(itineraryService.save(holiday.itinerary())))
                .map(itinerary -> holiday.withItinerary(itinerary)
                        .withLocation(itinerary.location()))
                .map(holidayMapper::toDatabase)
                .map(holidayDb -> holidayDb.addCategories(holidayDb.getCategories()))
                .map(holidayRepository::save)
                .orElseThrow(() -> new IllegalStateException("Failed to save holiday"));

        return holidayMapper.toModel(savedHolidayDb);
    }

    @Override
    @Transactional
    public Holiday update(final Holiday holiday) {
        log.info("update holiday: {}", holiday);

        // Get existing holiday
        final HolidayDb existingHoliday = holidayRepository.findById(holiday.id())
                .orElseThrow(() -> new IllegalStateException("Holiday not found with id: " + holiday.id()));


        // Merge
        final Holiday updatedHoliday = holidayMapper.merge(holiday, holidayMapper.toModel(existingHoliday).toBuilder());

        final HolidayDb existingHolidayDb = Optional.of(updatedHoliday)
                .map(holidayMapper::toDatabase)
                .map(this::updateItineraryIdentifier)
                .map(holidayDb -> holidayDb.addCategories(holidayDb.getCategories()))
                .orElseThrow();

        // Save
        final HolidayDb saved = holidayRepository.save(existingHolidayDb);

        return holidayMapper.toModel(saved);
    }

    @Override
    @Transactional
    public List<Holiday> saveAll(final List<Holiday> holidays) {
        log.info("save holidays: {}", holidays);
        return holidays.stream().map(this::save).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HolidayDashboard holidayDashboard() {


        final Map<Section, DashboardConfig> dashboardConfigMap = dashboardConfigService
                .getByType(DashboardConfig.Type.HOLIDAYS)
                .stream()
                .collect(Collectors.toMap(DashboardConfig::section, Function.identity()));




        return HolidayDashboard.builder()
                .heroImageUrl(Optional.ofNullable(dashboardConfigMap.get(HERO_SECTION))
                        .map(DashboardConfig::dashboardData)
                        .filter(dashboardData -> !dashboardData.isEmpty())
                        .map(dashboardData -> dashboardData.get(0).imageUrl())
                        .orElse("https://ik.imagekit.io/r4qffffod/Locations/image2.jpg?updatedAt=1741208339318"))
                .topDestinations(populatePriceLocation(dashboardConfigMap.get(Section.TOP_DESTINATIONS)))
                .topPackages(populateTopPackages(dashboardConfigMap.get(Section.TOP_PACKAGES)))
                .internationalDestinations(
                        populatePriceLocation(dashboardConfigMap.get(Section.INTERNATIONAL_DESTINATIONS)))
                .unexploredDestinations(
                        populatePriceLocation(dashboardConfigMap.get(Section.UNEXPLORED_DESTINATIONS)))
                .holidayThemes(dashboardConfigMap.get(Section.TOP_ATTRACTIONS).dashboardData())
                .reviews(reviewService.getReviews())
                .build();

    }

    // Replace the old getHolidays method with this one
    @Override
    @Transactional(readOnly = true) // Add transactional annotation
    public List<Holiday> searchHolidays(final HolidaySearchRequest searchRequest) {
        log.info("Searching holidays with criteria: {}", searchRequest);

        Specification<HolidayDb> spec = HolidaySpecification.findByCriteria(searchRequest);

        return holidayRepository.findAll(spec)
                .stream()
                .map(holidayMapper::toModel)
                .toList();
    }

    @Override
    public Holiday getHolidayById(final Long id) {
        return holidayRepository.findById(id)
                .map(holidayMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));
    }

    private List<DashboardData> populateTopPackages(final DashboardConfig dashboardConfig) {
        if (Objects.isNull(dashboardConfig.dashboardData()) || dashboardConfig.dashboardData().isEmpty()) {
            return List.of();
        }

        final Map<Long, DashboardData> dashboardDataMap = dashboardConfig.dashboardData()
                .stream()
                .collect(Collectors.toMap(DashboardData::holidayId, Function.identity()));

        return holidayRepository.findAllByIdIn(dashboardDataMap.keySet()).stream()
                .map(holidayMapper::toModel)
                .map(holiday -> {
                    final DashboardData dashboardData = dashboardDataMap.get(holiday.id());
                    return DashboardData.builder()
                            .imageUrl(dashboardData.imageUrl())
                            .displayName(holiday.name())
                            .build();
                })
                .toList();


    }

    private HolidayDb updateItineraryIdentifier(final HolidayDb holidayDb) {
        holidayDb.getItinerary().setIdentifier(
                Utils.itineraryIdentifier(
                        holidayDb.getLocation().getCityCode(),
                        holidayDb.getLocation().getCountryCode(),
                        holidayDb.getItinerary().getDuration(),
                        holidayDb.getItinerary().getName()
                ));
        return holidayDb;
    }

    @SneakyThrows
    private List<DashboardData> populatePriceLocation(final DashboardConfig dashboardConfig) {

        final Map<String, DashboardData> dashboardDataMap = Optional.ofNullable(dashboardConfig)
                .map(DashboardConfig::dashboardData)
                .map(dashboardData -> dashboardData.stream()
                        .collect(Collectors.toMap(this::generateKey, Function.identity())))
                .orElseThrow(() -> new RuntimeException("No dashboard data found"));

        // Launch three asynchronous calls to the repository.
        final CompletableFuture<List<LocationPriceProjection>> cityFuture = CompletableFuture.supplyAsync(
                () -> holidayRepository.findLowestPriceByCity(dashboardDataMap.keySet()
                        .stream()
                        .filter(string -> string.startsWith("CITY-"))
                        .toList()));


        final CompletableFuture<List<LocationPriceProjection>> stateFuture = CompletableFuture.supplyAsync(
                () -> holidayRepository.findLowestPriceByState(dashboardDataMap.keySet()
                        .stream()
                        .filter(string -> string.startsWith("STATE-"))
                        .toList()));

        final CompletableFuture<List<LocationPriceProjection>> countryFuture = CompletableFuture.supplyAsync(
                () -> holidayRepository.findLowestPriceByCountry(dashboardDataMap.keySet()
                        .stream()
                        .filter(string -> string.startsWith("COUNTRY-"))
                        .toList()));

        // Wait for all futures and collect results into a single list
        final List<LocationPriceProjection> priceProjections = Stream.of(cityFuture, stateFuture, countryFuture)
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        return priceProjections.stream()
                .map(projection -> {
                    final String key = switch (projection.getType()) {
                        case "CITY" -> String.join("-", "CITY",
                                projection.getCityCode(), projection.getStateCode(), projection.getCountryCode());
                        case "STATE" -> String.join("-", "STATE",
                                projection.getStateCode(), projection.getCountryCode());
                        case "COUNTRY" -> String.join("-", "COUNTRY",
                                projection.getCountryCode());
                        default -> throw new IllegalStateException("Unexpected value: " + projection.getType());
                    };
                    final DashboardData data = dashboardDataMap.get(key);
                    return DashboardData.builder()
                            .displayName(projection.getDisplayName())
                            .price(projection.getStandardPrice())
                            .imageUrl(data.imageUrl())
                            .order(data.order())
                            .build();

                }).sorted(Comparator.comparing(DashboardData::order))
                .toList();
    }


    private String generateKey(final DashboardData data) {
        return switch (data.displayTarget()) {
            case CITY -> String.join("-", data.displayTarget().name(), data.cityCode(), data.stateCode(),
                    data.countryCode());
            case STATE -> String.join("-", data.displayTarget().name(), data.stateCode(),
                    data.countryCode());
            case COUNTRY -> String.join("-", data.displayTarget().name(), data.countryCode());
        };

    }

}
