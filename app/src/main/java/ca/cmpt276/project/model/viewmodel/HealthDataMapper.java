package ca.cmpt276.project.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.Violation;

public class HealthDataMapper {
    private HealthDataMapper() throws InstantiationException {
        throw new InstantiationException(getClass().getName() + " is not instantiable");
    }

    public static LiveData<Map<String, RestaurantDetails>> getRestaurantDetailsMap(LiveData<List<RestaurantDetails>> restaurantDetailsData) {
        return Transformations.map(
                restaurantDetailsData,
                restaurantDetailsList -> restaurantDetailsList
                        .stream()
                        .collect(Collectors.collectingAndThen(mappingOfRestaurantDetails, Collections::unmodifiableMap))
        );
    }
    private static Collector<RestaurantDetails, ?, Map<String, RestaurantDetails>> mappingOfRestaurantDetails = Collectors.toMap(
            restaurantDetails -> restaurantDetails.restaurant.trackingNumber,
            restaurantDetails -> restaurantDetails
    );

    public static LiveData<Map<String, InspectionDetails>> getInspectionDetailsMap(LiveData<List<InspectionDetails>> inspectionDetailsData) {
        return Transformations.map(
                inspectionDetailsData,
                inspectionDetailsList -> inspectionDetailsList
                        .stream()
                        .collect(Collectors.collectingAndThen(mappingOfInspectionDetails, Collections::unmodifiableMap))
        );
    }
    private static Collector<InspectionDetails, ?, Map<String, InspectionDetails>> mappingOfInspectionDetails = Collectors.toMap(
            inspectionDetails -> inspectionDetails.inspection.trackingNumber,
            inspectionDetails -> inspectionDetails
    );

    public static LiveData<Map<Integer, Violation>> getViolationMap(LiveData<List<Violation>> violationsData) {
        return Transformations.map(
                violationsData,
                violationsList -> violationsList
                        .stream()
                        .collect(Collectors.collectingAndThen(mappingOfViolations, Collections::unmodifiableMap))
        );
    }
    private static Collector<Violation, ?, Map<Integer, Violation>> mappingOfViolations = Collectors.toMap(
            violation -> violation.codeNumber,
            violation -> violation
    );
}
