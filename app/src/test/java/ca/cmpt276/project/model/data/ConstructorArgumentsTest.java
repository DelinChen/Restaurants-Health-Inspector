package ca.cmpt276.project.model.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.cmpt276.project.model.data.ConstructorArguments;
import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.data.ViolationCategory;

public class ConstructorArgumentsTest {
    private static final int codeNumber = 201;
    private static final boolean isCritical = true;
    private static final ViolationCategory category = ViolationCategory.FOOD;
    private static final String description = "Food contaminated or unfit for human consumption [s. 13]";

    private static final String[] argNames = {"codeNumber", "isCritical", "category", "description"};
    private static final Object[] argValues = {codeNumber, isCritical, category, description};
    private static final Object[] argValuesWithNull         = {codeNumber, isCritical, category, null};

    private static final String[] stringArgNames = {"description"};
    private static final String[] stringArgValues = {description};
    private static final String[] stringArgValuesWithEmpty = {""};

    private Violation violation;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void requireArgsNonNull_getsNonNullArgTest() {
        ConstructorArguments.requireArgsNonNull(argNames, argValues, Violation.class);
    }

    @Test
    public void requireArgsNonNull_getsNullArgTest() {
        thrown.expect(NullPointerException.class);
        ConstructorArguments.requireArgsNonNull(argNames, argValuesWithNull, Violation.class);
    }

    @Test
    public void requireStringArgsNonEmptyTest_getsNonEmptyStringTest() {
        ConstructorArguments.requireStringArgsNonEmpty(stringArgNames, stringArgValues, Violation.class);
    }
    @Test
    public void requireStringArgsNonEmptyTest_getsEmptyStringTest() {
        thrown.expect(IllegalArgumentException.class);
        ConstructorArguments.requireStringArgsNonEmpty(stringArgNames, stringArgValuesWithEmpty, Violation.class);
    }
}