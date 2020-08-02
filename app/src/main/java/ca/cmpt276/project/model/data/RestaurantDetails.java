package ca.cmpt276.project.model.data;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantDetails implements Comparable<RestaurantDetails> {
    @Embedded
    @NonNull public final Restaurant restaurant;

    @Relation(
            parentColumn = "tracking_number",
            entityColumn = "tracking_number",
            entity = Inspection.class)
    @NonNull public final List<InspectionDetails> inspectionDetailsList;

    @Relation(
            parentColumn = "tracking_number",
            entityColumn = "tracking_number",
            entity = Favourites.class,
            projection = "is_favourite")
    public boolean isFavourite;

    ////////////////////////////////////////////////////
    // Constructor

    public RestaurantDetails(Restaurant restaurant, List<InspectionDetails> inspectionDetailsList, boolean isFavourite) {
        this.restaurant = restaurant;
        this.inspectionDetailsList = inspectionDetailsList.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        this.isFavourite = isFavourite;
    }


    ////////////////////////////////////////////////////
    // Object override methods

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetails<" + "\n\t" + restaurant + ",\n\t" + inspectionDetailsList + "\n>";
    }


    ////////////////////////////////////////////////////
    // Comparable method

    @Override
    public int compareTo(RestaurantDetails other) {
        return this.restaurant.compareTo(other.restaurant);
    }
}
