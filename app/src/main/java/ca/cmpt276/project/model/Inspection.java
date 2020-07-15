package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity(
    tableName = "inspections",
    primaryKeys = {"tracking_number", "date"},
    foreignKeys = {
        @ForeignKey(
            entity = Restaurant.class, parentColumns = "tracking_number", childColumns = "tracking_number",
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    },
    indices = @Index({"tracking_number", "date"})
)
public class Inspection implements Comparable<Inspection> {
    @ColumnInfo(name = "tracking_number")
    @NonNull public final String trackingNumber;

    @NonNull public final LocalDate date;
    @NonNull public final InspectionType type;
    
    @ColumnInfo(name = "num_crit_violations")
    public final int numCritViolations;

    @ColumnInfo(name = "num_noncrit_violations")
    public final int numNonCritViolations;
    
    @ColumnInfo(name = "hazard_rating")
    public final HazardRating hazardRating;

    @Ignore
    public final List<Violation> violations = Collections.emptyList();

    /////////////////////////////////////////////////////////////////////////////////////
    // Constructor

    public Inspection(String trackingNumber, LocalDate date, InspectionType type, int numCritViolations, int numNonCritViolations, HazardRating hazardRating) {
        validateConstructorArgs(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);
        this.trackingNumber         = trackingNumber;
        this.date                   = date;
        this.type                   = type;
        this.numCritViolations      = numCritViolations;
        this.numNonCritViolations   = numNonCritViolations;
        this.hazardRating           = hazardRating;
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
                && hazardRating.equals(other.hazardRating);
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
        return Objects.hash(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);
    }

    @NonNull
    @Override
    public String toString() {
        // Used mainly for debugging purposes
        return "Inspection<" + trackingNumber + ", " + date + ", " + type
                + ", " + numCritViolations + ", " + numNonCritViolations
                + ", " + hazardRating + ">";
    }


    ////////////////////////////////////////////////////////
    // Validate constructor args

    private static void validateConstructorArgs(String trackingNumber, LocalDate date, InspectionType type, int numCritViolations, int numNonCritViolations, HazardRating hazardRating) {
        String[] argNames = {"trackingNumber", "date", "type", "numCritViolations", "numNonCritViolations", "hazardRating"};
        Object[] argValues = {trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating};
        ConstructorArguments.requireArgsNonNull(argNames, argValues, Inspection.class);

        String[] stringArgNames = {"trackingNumber"};
        String[] stringArgValues = {trackingNumber};
        ConstructorArguments.requireStringArgsNonEmpty(stringArgNames, stringArgValues, Inspection.class);

        String[] intArgNames = {"numNonCritViolations", "numNonCritViolations"};
        int[] intArgValues = {numCritViolations, numNonCritViolations};
        ConstructorArguments.requireNonNegativeIntArgs(intArgNames, intArgValues, Inspection.class);

        requireInspectionTypeNotNullType(type);
        requireHazardRatingNotNullRating(hazardRating);
    }

    private static void requireInspectionTypeNotNullType(InspectionType type) {
        if(type.equals(InspectionType.NULL_TYPE)) {
            throw new IllegalArgumentException("Inspection class can not be instantiated with the field InspectionType type == NULL_TYPE");
        }
    }
    private static void requireHazardRatingNotNullRating(HazardRating hazardRating) {
        if(hazardRating.equals(HazardRating.NULL_RATING)) {
            throw new IllegalArgumentException("Inspection class can not be instantiated with the field HazardRating hazardRating == NULL_RATING");
        }
    }
}
