package ca.cmpt276.project.model;

import java.util.Objects;


public class Violation {
    public final int vioNum;
    public final boolean isCritical;
    public final ViolationCategory category;
    public final String description;

    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors


    public Violation(int vioNum, boolean isCritical, ViolationCategory category, String description) {
        this.vioNum = vioNum;
        this.isCritical = isCritical;
        this.category = category;
        this.description = description;
    }

}
