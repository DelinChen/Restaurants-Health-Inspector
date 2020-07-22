package ca.cmpt276.project.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.Violation;

public class HealthViewModel extends ViewModel {
    public final LiveData<List<RestaurantDetails>> restaurantDetailsData;
    public final LiveData<List<InspectionDetails>> inspectionDetailsData;
    public final LiveData<List<Violation>> violationData;

    public final LiveData<Map<String, RestaurantDetails>> restaurantDetailsMap;
    public final LiveData<Map<String, InspectionDetails>> inspectionDetailsMap;
    public final LiveData<Map<Integer, Violation>> violationsMap;


    /////////////////////////////////////////////////////////////
    // Constructor

    public HealthViewModel(HealthRepository repo) {
        restaurantDetailsData = repo.getAllRestaurantDetails();
        inspectionDetailsData = repo.getAllInspectionDetails();
        violationData = repo.getAllViolations();

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
