package ca.cmpt276.project.model;

import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class Inspection {
    public final String inspectDate;
    public final String inspectType;
    public final int inspectCritIssue;
    public final int inspectNonCritIssue;
    public final int hazaradRating; //0 = low, 1 = moderate, 2= high
    private final List<Violation> violations;

    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public Inspection(String inspectDate, String inspectType, int inspectCritIssue, int inspectNonCritIssue, int hazaradRating, List<Violation> violations) {
        this.inspectDate = inspectDate;
        this.inspectType = inspectType;
        this.inspectCritIssue = inspectCritIssue;
        this.inspectNonCritIssue = inspectNonCritIssue;
        this.hazaradRating = hazaradRating;
        // dependency injection
        this.violations = violations;
    }

    /**
     * Used to populate the list of inspections; outside world shouldn't be able to add anything to list
     * @param checkup the Inspection to add to this Restaurant object
     */
    protected void violation(Violation checkup) {
        violations.add(checkup);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Getter methods
    //     No mutator methods are allowed because there is currently no need to change a restaurant's data dynamically
    //     i.e. the class is essentially just a read-only interface to the csv data

    public List<Violation> getViolations() { return violations; }

    /////////////////////////////////////////////////////////////////////////////////////
    // Helper methods


}
