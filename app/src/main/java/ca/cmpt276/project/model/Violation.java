package ca.cmpt276.project.model;

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

    }


}
