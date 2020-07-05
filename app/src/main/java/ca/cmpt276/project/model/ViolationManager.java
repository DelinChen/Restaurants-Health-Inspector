package ca.cmpt276.project.model;

import java.util.ArrayList;
import java.util.List;

public class ViolationManager {
    private List<Violation> violations;

    public ViolationManager() {
        violations = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Delegate methods

    public int size() {
        return violations.size();
    }

}
