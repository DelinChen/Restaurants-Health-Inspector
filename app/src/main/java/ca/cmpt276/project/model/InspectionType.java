package ca.cmpt276.project.model;

import androidx.annotation.NonNull;

public enum InspectionType {
    ROUTINE("Routine"),
    FOLLOWUP("Follow-Up"),
    NULL_TYPE("");


    ///////////////////////////////////////////////////////////
    // Fields

    public final String stringVal;


    ///////////////////////////////////////////////////////////
    // Constructor

    InspectionType(String stringVal) {
        this.stringVal = stringVal;
    }

    @NonNull
    @Override
    public String toString() {
        return this.stringVal;
    }


    ///////////////////////////////////////////////////////////
    // Factory method

    public static InspectionType fromString(String input) {
        for(InspectionType type : InspectionType.values()) {
            if(type.stringVal.equals(input)) {
                return type;
            }
        }
        return NULL_TYPE;
    }
}
