package ca.cmpt276.project.model;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static ca.cmpt276.project.model.Restaurant.MAX_LATITUDE;
import static ca.cmpt276.project.model.Restaurant.MAX_LONGITUDE;
import static ca.cmpt276.project.model.Restaurant.MIN_LATITUDE;
import static ca.cmpt276.project.model.Restaurant.MIN_LONGITUDE;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ViolationTest {
    private final static int vioNum = 201;
    private final static boolean isCritical = true;
    private final static String hazardType = "Food contaminated or unfit for human consumption [s. 13]";
    private final static String description = "Not Repeat";


    public static class ViolationMethodTest {
        private Violation violation;
        @Before
        public void initialize() {
            violation = new Violation(vioNum, isCritical, hazardType, description);
        }

        public void validVioNumTest(){
            assertEquals(vioNum, violation.vioNum);
        }

        public void validIsCriticalTest(){
            assertEquals(isCritical, violation.isCritical);
        }

        public void validHazardTypeTest(){
            assertEquals(hazardType, violation.hazardType);
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
        public void nullVioNumTest() {
            //int cannot be null
            violation = new Violation(0, isCritical, hazardType, description);
        }
        @Test
        public void nullNameTest() {
            //boolean is default to be false if not assigned any value
            violation = new Violation(vioNum, false, hazardType, description);
        }
        @Test
        public void nullHazardTest() {
            violation = new Violation(vioNum, isCritical, null, description);
        }
        @Test
        public void nullDescriptionTest() {
            violation = new Violation(vioNum, isCritical, hazardType, null);
        }

    }

    public static class RestaurantConstructorEmptyStringArgsTest {
        private Violation violation;

        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void emptyVioNumTest() {
            //int cannot be null
            violation = new Violation(0, isCritical, hazardType, description);
        }
        @Test
        public void emptyNameTest() {
            //boolean is default to be false if not assigned any value
            violation = new Violation(vioNum, false, hazardType, description);
        }
        @Test
        public void emptyHazardTest() {
            violation = new Violation(vioNum, isCritical, null, description);
        }
        @Test
        public void emptyDescriptionTest() {
            violation = new Violation(vioNum, isCritical, hazardType, null);
        }
    }


}