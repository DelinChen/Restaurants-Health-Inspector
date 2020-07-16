package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.Collections;
import java.util.List;

public class InspectionDetails {
    @Embedded
    @NonNull public final Inspection inspection;

    @Relation(
            parentColumn = "inspection_id",
            entityColumn = "code_number",
            associateBy = @Junction(InspectionViolationCrossref.class)
    )
    @NonNull public final List<Violation> violations;

    public InspectionDetails(Inspection inspection, List<Violation> violations) {
        this.inspection = inspection;
        this.violations = Collections.unmodifiableList(violations);
    }
}
