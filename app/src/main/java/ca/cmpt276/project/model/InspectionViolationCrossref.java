package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.time.LocalDate;

@Entity(
    tableName = "inspection_violations_crossref",
    primaryKeys = {"tracking_number", "date", "code_number"},
    foreignKeys = {
        @ForeignKey(
            entity = Inspection.class, parentColumns = {"tracking_number", "date"}, childColumns = {"tracking_number", "date"},
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Violation.class, parentColumns = "code_number", childColumns = "code_number",
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
        )
    }
)
public class InspectionViolationCrossref {
    @ColumnInfo(name = "tracking_number")
    @NonNull public final String trackingNumber;

    @NonNull public final LocalDate date;

    @ColumnInfo(name = "code_number")
    public final int codeNumber;

    public InspectionViolationCrossref(String trackingNumber, LocalDate date, int codeNumber) {
        this.trackingNumber = trackingNumber;
        this.date = date;
        this.codeNumber = codeNumber;
    }
}
