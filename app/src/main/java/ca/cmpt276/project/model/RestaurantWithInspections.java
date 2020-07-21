package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;


import java.util.Collections;
import java.util.List;

public class RestaurantWithInspections {
    @Embedded
    public final Restaurant restaurant;

    @NonNull
    public final List<Inspection> inspections;

    public RestaurantWithInspections(Restaurant restaurant, List<Inspection> inpsections) {
        validInputChecking(restaurant, inpsections);
        this.restaurant = restaurant;
        this.inspections = Collections.unmodifiableList(inpsections);
    }

    public void validInputChecking(Restaurant restaurant, List<Inspection> inspections){
        if(restaurant == null || inspections == null){
            throw new IllegalArgumentException("either restaurant or the inpsections cannot be null");
        }

        if(inspections.size() <= 0){
            throw new IllegalArgumentException("the Inspection List size cannot be empty or negative");
        }
    }
}
