package ca.cmpt276.project.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.Collections;
import java.util.List;

public class InspectionWithViolations {
    @Embedded
    @NonNull public final Inspection inspection;

    @NonNull public final List<Violation> violations;

    public InspectionWithViolations(Inspection inspection, List<Violation> violations) {
        this.inspection = inspection;
        this.violations = Collections.unmodifiableList(violations);
    }
}
