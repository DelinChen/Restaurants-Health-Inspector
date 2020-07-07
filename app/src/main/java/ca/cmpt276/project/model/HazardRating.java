package ca.cmpt276.project.model;

public enum HazardRating {
    LOW("Low"),
    MODERATE("Moderate"),
    HIGH("High"),
    NULL_RATING("");

    private final String stringVal;

    HazardRating(String stringVal) {
        this.stringVal = stringVal;
    }

    @Override
    public String toString() {
        return stringVal;
    }

    public static HazardRating parse(String input) {
        for(HazardRating rating : HazardRating.values()) {
            if(rating.stringVal.equals(input)) {
                return rating;
            }
        }
        return NULL_RATING;
    }
}
