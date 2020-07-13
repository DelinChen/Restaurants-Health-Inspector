package ca.cmpt276.project.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ViolationWithViolationCategory {
    @Embedded
    public Violation violation;

    //incomplete
//    @Relation(parentColumn = )
}
