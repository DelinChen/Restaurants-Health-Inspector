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
        validateConstructorArgs(codeNumber, isCritical, category, description);
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
        return allFieldsEqualWith(other);
    }
    private boolean allFieldsEqualWith(Violation other) {
        return codeNumber == other.codeNumber
                && isCritical == other.isCritical
                && category.equals(other.category)
                && description.equals(other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeNumber, isCritical, category, description);
    }


    private static void validateConstructorArgs(int codeNumber, boolean isCritical, ViolationCategory category, String description) {
        String[] argNames = {"codeNumber", "isCritical", "category", "description"};
        Object[] argValues = {codeNumber, isCritical, category, description};
        ConstructorArguments.requireArgsNonNull(argNames, argValues, Violation.class);

        String[] stringArgNames = {"description"};
        String[] stringArgValues = {description};
        ConstructorArguments.requireStringArgsNonEmpty(stringArgNames, stringArgValues, Violation.class);

        requireCodeNumberNonNegative(codeNumber);
        requireCategoryNotNullCategory(category);
    }

    private static void requireCodeNumberNonNegative(int codeNumber) {
        if(codeNumber < 0) {
            throw new IllegalArgumentException("codeNumber must be non-negative");
        }
    }

    private static void requireCategoryNotNullCategory(ViolationCategory category) {
        if(category.equals(ViolationCategory.NULL_CATEGORY)) {
            throw new IllegalArgumentException("ViolationCategory category cannot be NULL_CATEGORY");
        }
    }
}
