package ca.cmpt276.project.model.data;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Collections;
import java.util.List;

public class RestaurantDetails {
    @Embedded
    @NonNull public final Restaurant restaurant;

    @Relation(
            parentColumn = "tracking_number",
            entityColumn = "tracking_number",
            entity = Inspection.class)
    @NonNull public final List<InspectionDetails> inspectionDetailsList;


    ////////////////////////////////////////////////////
    // Constructor

    public RestaurantDetails(Restaurant restaurant, List<InspectionDetails> inspectionDetailsList) {
        this.restaurant = restaurant;
        this.inspectionDetailsList = Collections.unmodifiableList(inspectionDetailsList);
    }


    ////////////////////////////////////////////////////
    // Object override methods

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetails<" + "\n\t" + restaurant + ",\n\t" + inspectionDetailsList + "\n>";
    }
}
