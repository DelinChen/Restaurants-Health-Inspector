package ca.cmpt276.project.model;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ViolationTest {
    private final static int codeNumber = 201;
    private final static boolean isCritical = true;
    private final static ViolationCategory category = ViolationCategory.FOOD;
    private final static String description = "Food contaminated or unfit for human consumption [s. 13]";


    public static class ViolationMethodTest {
        private Violation violation;
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

    public static class ViolationConstructorNullArgsTest {
        private Violation violation;

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
        public void categoryIsNullCategoryTest() {
            violation = new Violation(codeNumber, isCritical, ViolationCategory.NULL_CATEGORY, description);
        }
        @Test
        public void nullDescriptionTest() {
            violation = new Violation(codeNumber, isCritical, category, null);
        }

    }

    public static class ViolationConstructorEmptyStringArgsTest {
        private Violation violation;

        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        public void emptyDescriptionTest() {
            violation = new Violation(codeNumber, isCritical, category, "");
        }
    }


}