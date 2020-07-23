package ca.cmpt276.project.model.data;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.data.ViolationCategory;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ViolationTest {
    private static final int codeNumber = 201;
    private static final boolean isCritical = true;
    private static final ViolationCategory category = ViolationCategory.FOOD;
    private static final String description = "Food contaminated or unfit for human consumption [s. 13]";

    private static Violation violation;

    public static class ValidConstructorArgsTest {
        @Before
        public void initialize() {
            violation = new Violation(codeNumber, isCritical, category, description);
        }

        @Test
        public void validCodeNumberTest(){
            assertEquals(codeNumber, violation.codeNumber);
        }

        @Test
        public void validIsCriticalTest(){
            assertEquals(isCritical, violation.isCritical);
        }

        @Test
        public void validCategoryTest(){
            assertEquals(category, violation.category);
        }

        @Test
        public void validDescriptionTest(){
            assertEquals(description, violation.description);
        }

    }

    public static class EqualsTest {
        @Before
        public void initialize() {
            violation = new Violation(codeNumber, isCritical, category, description);
        }

        @Test
        public void equalsReturnsTrueTest() {
            Violation equivalent = new Violation(codeNumber, isCritical, category, description);
            assertEquals(equivalent, violation);
        }

        @Test
        public void codeNumberNotEqualTest() {
            Violation different = new Violation(202, isCritical, category, description);
            assertNotEquals(different, violation);
        }

        @Test
        public void isCriticalNotEqualTest() {
            Violation different = new Violation(codeNumber, false, category, description);
            assertNotEquals(different, violation);
        }

        @Test
        public void categoryNotEqualTest() {
            Violation different = new Violation(codeNumber, isCritical, ViolationCategory.EQUIPMENT, description);
            assertNotEquals(different, violation);
        }

        @Test
        public void descriptionNotEqualTest() {
            Violation different = new Violation(codeNumber, isCritical, category, "Beep beep I'm a jeep");
            assertNotEquals(different, violation);
        }

        @Test
        public void allFieldsNotEqualTest() {
            Violation different = new Violation(202, false, ViolationCategory.EQUIPMENT, "Beep beep I'm a jeep");
            assertNotEquals(different, violation);
        }
    }

    public static class ViolationConstructorNullArgsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectNullPointerException() {
            thrown.expect(NullPointerException.class);
        }

        @Test
        public void nullCodeNumberTest() {
            //int cannot be null
            violation = new Violation((Integer)null, isCritical, category, description);
        }
        @Test
        public void nullNameTest() {
            //boolean is default to be false if not assigned any value
            violation = new Violation(codeNumber, (Boolean)null, category, description);
        }
        @Test
        public void nullCategoryTest() {
            violation = new Violation(codeNumber, isCritical, null, description);
        }
        @Test
        public void nullDescriptionTest() {
            violation = new Violation(codeNumber, isCritical, category, null);
        }

    }

    public static class InvalidViolationConstructor_ThrowsIllegalArgumentExceptionTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void emptyDescriptionTest() {
            violation = new Violation(codeNumber, isCritical, category, "");
        }

        @Test
        public void categoryIsNullCategoryTest() {
            violation = new Violation(codeNumber, isCritical, ViolationCategory.NULL_CATEGORY, description);
        }

        @Test
        public void negativeCodeNumberTest() {
            Violation violation = new Violation(-1, isCritical, category, description);
        }
    }

}