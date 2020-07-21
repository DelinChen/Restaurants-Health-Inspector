package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.time.LocalDate;

@Entity(
    tableName = "inspection_violations_crossref",
    primaryKeys = {"inspection_id", "code_number"},
    foreignKeys = {
        @ForeignKey(
            entity = Inspection.class, parentColumns = "inspection_id", childColumns = "inspection_id",
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Violation.class, parentColumns = "code_number", childColumns = "code_number",
            onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
        )
    }
)
public class InspectionViolationCrossref {
    @ColumnInfo(name = "inspection_id", index = true)
    public final int inspectionId;


    @ColumnInfo(name = "code_number", index = true)
    public final int codeNumber;

    public InspectionViolationCrossref(int inspectionId, int codeNumber) {
        this.inspectionId = inspectionId;
        this.codeNumber = codeNumber;
    }

    public InspectionViolationCrossref(Inspection inspection, Violation violation) {
        this.inspectionId = inspection.inspectionId;
        this.codeNumber = violation.codeNumber;
    }
}
