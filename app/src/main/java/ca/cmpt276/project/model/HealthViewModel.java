package ca.cmpt276.project.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HealthViewModel extends ViewModel {
    public final LiveData<Map<String, RestaurantDetails>> restaurantDetailsMap;
    public final LiveData<Map<String, InspectionDetails>> inspectionDetailsMap;
    public final LiveData<Map<Integer, Violation>> violationsMap;


    /////////////////////////////////////////////////////////////
    // Constructor

    public HealthViewModel(HealthRepository repo) {
        restaurantDetailsMap = repo.getRestaurantDetailsMap();
        inspectionDetailsMap = repo.getInspectionDetailsMap();
        violationsMap = repo.getViolationMap();
    }




/*
    public void updateData() {
        repo.updateData();
    }
*/
}
