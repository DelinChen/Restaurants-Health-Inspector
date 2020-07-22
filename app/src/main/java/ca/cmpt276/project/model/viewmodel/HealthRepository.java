package ca.cmpt276.project.model.viewmodel;

import android.content.Context;

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
import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.model.database.InspectionDao;
import ca.cmpt276.project.model.database.RestaurantDao;
import ca.cmpt276.project.model.database.ViolationDao;

public class HealthRepository {
    private final RestaurantDao restaurantDao;
    private final InspectionDao inspectionDao;
    private final ViolationDao violationDao;


    public HealthRepository(final Context anyContext) {
        HealthDatabase db = HealthDatabase.getInstance(anyContext);
        restaurantDao = db.getRestaurantDao();
        inspectionDao = db.getInspectionDao();
        violationDao = db.getViolationDao();
    }

    public LiveData<List<RestaurantDetails>> getAllRestaurantDetails() {
        return restaurantDao.getAllRestaurantsDetails();
    }

    public LiveData<List<InspectionDetails>> getAllInspectionDetails() {
        return inspectionDao.getAllInspectionDetails();
    }

    public LiveData<List<Violation>> getAllViolations() {
        return violationDao.getAllViolations();
    }

    public LiveData<Map<String, RestaurantDetails>> getRestaurantDetailsMap() {
        return Mapper.getRestaurantDetailsMap(getAllRestaurantDetails());
    }

    public LiveData<Map<String, InspectionDetails>> getInspectionDetailsMap() {
        return Mapper.getInspectionDetailsMap(getAllInspectionDetails());
    }

    public LiveData<Map<Integer, Violation>> getViolationMap() {
        return Mapper.getViolationMap(getAllViolations());
    }


    private static class Mapper {
        private static LiveData<Map<String, RestaurantDetails>> getRestaurantDetailsMap(LiveData<List<RestaurantDetails>> restaurantDetailsData) {
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

        private static LiveData<Map<String, InspectionDetails>> getInspectionDetailsMap(LiveData<List<InspectionDetails>> inspectionDetailsData) {
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

        private static LiveData<Map<Integer, Violation>> getViolationMap(LiveData<List<Violation>> violationsData) {
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
}
