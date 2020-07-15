package ca.cmpt276.project.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


//Because we have an ViolationCategory as class attribute, still haven't figure out how to deal with it yet..
@Entity( tableName = "violations")
public class Violation {
    @PrimaryKey
    @ColumnInfo(name = "code_number")
    public final int codeNumber;

    @ColumnInfo(name = "is_critical")
    public final boolean isCritical;

    @NonNull public final ViolationCategory category;
    @NonNull public final String description;


    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public Violation(int codeNumber, boolean isCritical, ViolationCategory category, String description) {
        validateConstructorArgs(codeNumber, isCritical, category, description);
        this.codeNumber = codeNumber;
        this.isCritical = isCritical;
        this.category = category;
        this.description = description;
    }


    @NonNull
    @Override
    public String toString() {
        return "Violation<" + codeNumber + ", " + isCritical + ", " + category + ", " + description + ">";
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
