package ca.cmpt276.project.model.data;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import ca.cmpt276.project.model.database.InspectionViolationCrossref;

public class InspectionDetails {
    @Embedded
    @NonNull public final Inspection inspection;

    @Relation(
            parentColumn = "inspection_id",
            entityColumn = "code_number",
            associateBy = @Junction(InspectionViolationCrossref.class)
    )
    public List<Violation> violations;


    ////////////////////////////////////////////////////
    // Constructor

    public InspectionDetails(Inspection inspection, List<Violation> violations) {
        this.inspection = inspection;
        this.violations = violations;
    }


    ////////////////////////////////////////////////////
    // Object override methods

    @NonNull
    @Override
    public String toString() {
        return "InspectionDetails<"
                + inspection.toString()
                    .replace("Inspection<", "")
                    .replace(">", ", ... " + violations.size() + " violations>");
    }
}
