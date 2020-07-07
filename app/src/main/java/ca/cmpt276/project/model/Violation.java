package ca.cmpt276.project.model;


import androidx.annotation.Nullable;

import java.util.Objects;

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


    @Override
    public boolean equals(@Nullable Object o) {
        if(!(o instanceof Violation)) {
            return false;
        }
        Violation other = (Violation) o;
        return this.codeNumber == other.codeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeNumber, isCritical, category, description);
    }
}
