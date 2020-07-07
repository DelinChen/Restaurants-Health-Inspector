package ca.cmpt276.project.model;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public class Inspection {
    public final String trackingNumber;
    public final LocalDate date;
    public final InspectionType type;
    public final int numCritViolations;
    public final int numNonCritViolations;
    public final HazardRating rating;
    public final List<Violation> violations;

    public Inspection(String trackingNumber, LocalDate date, InspectionType type, int numCritViolations, int numNonCritViolations, HazardRating rating, List<Violation> violations) {
        this.trackingNumber = trackingNumber;
        this.date = date;
        this.type = type;
        this.numCritViolations = numCritViolations;
        this.numNonCritViolations = numNonCritViolations;
        this.rating = rating;
        this.violations = violations;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if(!(o instanceof Inspection)) {
            return false;
        }
        Inspection other = (Inspection)o;
        return allFieldsEqualWith(other);
    }
    private boolean allFieldsEqualWith(Inspection other) {
        return trackingNumber.equals(other.trackingNumber)
                && date.equals(other.date)
                && type.equals(other.type)
                && numCritViolations == other.numCritViolations
                && numNonCritViolations == other.numNonCritViolations
                && rating.equals(other.rating)
                && violations.equals(violations);
    }
}
