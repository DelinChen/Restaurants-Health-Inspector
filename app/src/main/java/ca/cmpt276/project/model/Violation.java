package ca.cmpt276.project.model;


public class Violation {
    public final int codeNumber;
    public final boolean isCritical;
    public final ViolationCategory category;
    public final String description;


    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public Violation(int codeNumber, boolean isCritical, ViolationCategory category, String description) {
        this.codeNumber = codeNumber;
        this.isCritical = isCritical;
        this.category = category;
        this.description = description;
    }

}
