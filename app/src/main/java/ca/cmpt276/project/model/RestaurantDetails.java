package ca.cmpt276.project.model;

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
    @NonNull public final List<InspectionDetails> inspections;


    ////////////////////////////////////////////////////
    // Constructor

    public RestaurantDetails(Restaurant restaurant, List<InspectionDetails> inspections) {
        this.restaurant = restaurant;
        this.inspections = Collections.unmodifiableList(inspections);
    }


    ////////////////////////////////////////////////////
    // Object override methods

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetails<" + "\n\t" + restaurant + ",\n\t" + inspections + "\n>";
    }
}
