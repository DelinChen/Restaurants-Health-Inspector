package ca.cmpt276.project.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class InspectionWithHazardRating {
    @Embedded
    public Inspection inspection;

    //incomplete
//    @Relation(parentColumn = "trackingNumber", entityColumn = )
}
