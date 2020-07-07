package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Inspection implements Comparable<Inspection> {
    public final String trackingNumber;
    public final LocalDate date;
    public final InspectionType type;
    public final int numCritViolations;
    public final int numNonCritViolations;
    public final HazardRating hazardRating;
    public final List<Violation> violations;


    /////////////////////////////////////////////////////////////////////////////////////
    // Constructor

    public Inspection(String trackingNumber, LocalDate date, InspectionType type, int numCritViolations, int numNonCritViolations, HazardRating hazardRating, List<Violation> violations) {
        this.trackingNumber         = trackingNumber;
        this.date                   = date;
        this.type                   = type;
        this.numCritViolations      = numCritViolations;
        this.numNonCritViolations   = numNonCritViolations;
        this.hazardRating           = hazardRating;
        this.violations             = Collections.unmodifiableList(violations);
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
                && hazardRating.equals(other.hazardRating)
                && violations.equals(other.violations);
    }

    @Override
    public int compareTo(Inspection other) {
        if(date.isBefore(other.date)) {
            return -1;
        }
        else if(date.isEqual(other.date)) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating, violations);
    }

    @NonNull
    @Override
    public String toString() {
        // Used mainly for debugging purposes
        return "Inspection<" + trackingNumber + ", " + date + ", " + type
                + ", " + numCritViolations + ", " + numNonCritViolations
                + ", " + hazardRating + ", ... " + violations.size() + " violations>";
    }
}
