package ca.cmpt276.project.model;

import androidx.annotation.NonNull;

public enum HazardRating {
    LOW("Low"),
    MODERATE("Moderate"),
    HIGH("High"),
    NULL_RATING("");


    ///////////////////////////////////////////////////////////
    // Fields

    public final String stringVal;


    ///////////////////////////////////////////////////////////
    // Constructor

    HazardRating(String stringVal) {
        this.stringVal = stringVal;
    }

    @NonNull
    @Override
    public String toString() {
        return stringVal;
    }


    ///////////////////////////////////////////////////////////
    // Factory method

    public static HazardRating fromString(String input) {
        for(HazardRating rating : HazardRating.values()) {
            if(rating.stringVal.equals(input)) {
                return rating;
            }
        }
        return NULL_RATING;
    }
}
