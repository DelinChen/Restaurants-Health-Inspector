package ca.cmpt276.project.model;

import java.util.Objects;


public class Violation {
    public final int vioNum;
    public final boolean isCritical;
    public final String hazardType;
    public final String description;

    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors


    public Violation(int vioNum, boolean isCritical, String hazardType, String description) {
        validateConstructorArgs(vioNum, isCritical, hazardType, description);
        this.vioNum = vioNum;
        this.isCritical = isCritical;
        this.hazardType = hazardType;
        this.description = description;
    }

    private void validateConstructorArgs(int vioNum, boolean isCritical, String hazardType, String description) {
        String[] argNames = {"vioNum", "isCritical", "hazardType", "description"};
        Object[] argValues = {vioNum, isCritical, hazardType, description};
        requireArgsNonNull(argNames, argValues);

        requireIntArgsNonEmpty(vioNum);
        requirebooleanArgsNonEmpty(isCritical);

        String[] stringArgNames = {"hazardType", "description"};
        String[] stringArgValues = {hazardType, description};
        requireStringArgsNonEmpty(stringArgNames, stringArgValues);

    }



    private void requireArgsNonNull(String[] argNames, Object[] argValues) {
        if(argNames.length != argValues.length) {
            throw new IllegalArgumentException("argNames.length must be equal to argValues.length");
        }

        for(int i = 0; i < argNames.length; i++) {
            Objects.requireNonNull(argValues[i], "Restaurant field " + argNames[i] + " cannot be null");
        }
    }

    private void requireIntArgsNonEmpty(int vioNum) {
        Objects.requireNonNull(vioNum, "vioNum " + vioNum + " cannot be null");
    }

    private void requirebooleanArgsNonEmpty(boolean isCritical) {
        Objects.requireNonNull(isCritical, "isCritical " + isCritical + " cannot be null");
    }


    private void requireStringArgsNonEmpty(String[] stringArgNames, String[] stringArgValues) {
        if(stringArgNames.length != stringArgValues.length) {
            throw new IllegalArgumentException("stringArgNames.length must be equal to stringArgValues.length");
        }

        for(int i = 0; i < stringArgValues.length; i++) {
            if(stringArgValues[i].length() == 0) {
                throw new IllegalArgumentException("Restaurant string field " + stringArgNames[i] + " cannot be the empty string");
            }
        }
    }



}
