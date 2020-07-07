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

        public void validCodeNumberTest(){
            assertEquals(codeNumber, violation.codeNumber);
        }

        public void validIsCriticalTest(){
            assertEquals(isCritical, violation.isCritical);
        }

        public void validCategoryTest(){
            assertEquals(category, violation.category);
        }

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
            violation = new Violation(0, isCritical, category, description);
        }
        @Test
        public void nullNameTest() {
            //boolean is default to be false if not assigned any value
            violation = new Violation(codeNumber, false, category, description);
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

    public static class ViolationConstructorEmptyStringArgsTest {
        private Violation violation;

        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void emptyCodeNumberTest() {
            //int cannot be null
            violation = new Violation(0, isCritical, category, description);
        }
        @Test
        public void emptyNameTest() {
            //boolean is default to be false if not assigned any value
            violation = new Violation(codeNumber, false, category, description);
        }
        @Test
        public void emptyCategoryTest() {
            violation = new Violation(codeNumber, isCritical, null, description);
        }
        @Test
        public void emptyDescriptionTest() {
            violation = new Violation(codeNumber, isCritical, category, null);
        }
    }


}