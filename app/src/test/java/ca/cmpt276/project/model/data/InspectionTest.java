package ca.cmpt276.project.model.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.cmpt276.project.model.data.HazardRating;
import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.InspectionType;
import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.data.ViolationCategory;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class InspectionTest {
    private static final String         trackingNumber  = "SHEN-B7BNSR";
    private static final LocalDate      date            = LocalDate.of(2019, 1, 30);
    private static final InspectionType type            = InspectionType.FOLLOWUP;
    private static final HazardRating   hazardRating    = HazardRating.LOW;
    private static final int            numCritViolations    = 1;
    private static final int            numNonCritViolations = 1;

    private static final Violation firstViolation = new Violation(
            308, false, ViolationCategory.fromCodeNumber(308),
            "Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)]");
    private static final Violation secondViolation = new Violation(
            401, true, ViolationCategory.fromCodeNumber(401),
            "Adequate handwashing stations not available for employees [s. 21(4)]");
    private static final Violation[] violationsBuffer = {firstViolation, secondViolation};
    private static final List<Violation> violations = new ArrayList<>(Arrays.asList(violationsBuffer));

    private static Inspection inspection;

    public static class ValidConstructorArgsTest {
        @Before
        public void initialize() {
            inspection = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);
        }

        @Test
        public void validTrackingNumberTest() {
            assertEquals(trackingNumber, inspection.trackingNumber);
        }
        @Test
        public void validDateTest() {
            assertEquals(date, inspection.date);
        }
        @Test
        public void validTypeTest() {
            assertEquals(type, inspection.type);
        }
        @Test
        public void validNumCritViolationsTest() {
            assertEquals(numCritViolations, inspection.numCritViolations);
        }
        @Test
        public void validNumNonCritViolationsTest() {
            assertEquals(numNonCritViolations, inspection.numNonCritViolations);
        }
        @Test
        public void validHazardRatingTest() {
            assertEquals(hazardRating, inspection.hazardRating);
        }
        @Test
        public void validViolationsTest() {
            assertEquals(violations, inspection.violations);
        }
    }

    public static class InspectionEqualsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Before
        public void initialize() {
            inspection = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);
        }

        @Test
        public void equalsReturnsTrueTest() {
            Inspection equivalent = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);
            assertEquals(equivalent, inspection);
        }

        @Test
        public void differentTrackingNumberTest() {
            Inspection different = new Inspection("SDFO-8HKP7E", date, type, numCritViolations, numNonCritViolations, hazardRating);
            assertNotEquals(different, inspection);
        }
        @Test
        public void differentDateTest() {
            Inspection different = new Inspection(trackingNumber, LocalDate.of(2019, 1, 29), type, numCritViolations, numNonCritViolations, hazardRating);
            assertNotEquals(different, inspection);
        }
        @Test
        public void differentTypeTest() {
            Inspection different = new Inspection(trackingNumber, date, InspectionType.ROUTINE, numCritViolations, numNonCritViolations, hazardRating);
            assertNotEquals(different, inspection);
        }
        @Test
        public void differentNumCritViolationsTest() {
            Inspection different = new Inspection(trackingNumber, date, type, 0, numNonCritViolations, hazardRating);
            assertNotEquals(different, inspection);
        }
        @Test
        public void differentNumNonCritViolationsTest() {
            Inspection different = new Inspection(trackingNumber, date, type, numCritViolations, 0, hazardRating);
            assertNotEquals(different, inspection);
        }
        @Test
        public void differentHazardRatingTest() {
            Inspection different = new Inspection("SDFO-8HKP7E", date, type, numCritViolations, numNonCritViolations, HazardRating.HIGH);
            assertNotEquals(different, inspection);
        }
        @Test
        public void differentViolationsTest() {
            Violation[] differentBuffer = {violationsBuffer[0]};
            List<Violation> diffViolations = new ArrayList<>(Arrays.asList(differentBuffer));
            Inspection different = new Inspection("SDFO-8HKP7E", date, type, numCritViolations, numNonCritViolations, hazardRating);
            assertNotEquals(different, inspection);
        }
    }

    public static class NullConstructorArgsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectNullPointerException() {
            thrown.expect(NullPointerException.class);
        }

        @Test
        public void nullTrackingNumberTest() {
            inspection = new Inspection(null, date, type, numCritViolations, numNonCritViolations, hazardRating);
        }
        @Test
        public void nullDateTest() {
            inspection = new Inspection(trackingNumber, null, type, numCritViolations, numNonCritViolations, hazardRating);
        }
        @Test
        public void nullTypeTest() {
            inspection = new Inspection(trackingNumber, date, null, numCritViolations, numNonCritViolations, hazardRating);
        }
        @Test
        public void nullNumCritViolationsTest() {
            inspection = new Inspection(trackingNumber, date, type, (Integer)null, numNonCritViolations, hazardRating);
        }
        @Test
        public void nullNumNonCritViolationsTest() {
            inspection = new Inspection(trackingNumber, date, type, numCritViolations, (Integer)null, hazardRating);
        }
        @Test
        public void nullHazardRatingTest() {
            inspection = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, null);
        }
        @Test
        public void nullViolationsTest() {
            inspection = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);
        }

        @Test
        public void allFieldsNullTest() {
            inspection = new Inspection(null, null, null, (Integer)null, (Integer)null, null);
        }
    }

    public static class EmptyStringConstructorArgsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void emptyStringTrackingNumberTest() {
            inspection = new Inspection("", date, type, numCritViolations, numNonCritViolations, hazardRating);
        }
    }

    public static class NegativeIntConstructorArgsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void negativeNumCritViolationsTest() {
            inspection = new Inspection(trackingNumber, date, type, -1, numNonCritViolations, hazardRating);
        }
        @Test
        public void negativeNumNonCritViolationsTest() {
            inspection = new Inspection(trackingNumber, date, type, numCritViolations, -1, hazardRating);
        }
        @Test
        public void allIntsNegativeTest() {
            inspection = new Inspection(trackingNumber, date, type, -1, -1, hazardRating);
        }
    }

    public static class CompareToTest {
        private static final LocalDate janFirst = LocalDate.of(2020, 1, 1);
        private static final LocalDate janSecond = LocalDate.of(2020, 1, 2);
        private static final LocalDate janThird = LocalDate.of(2020, 1, 3);

        private static Inspection firstInspection;
        private static Inspection secondInspection;

        @Before
        public void initialize() {
            firstInspection = new Inspection(trackingNumber, janFirst, type, numCritViolations, numNonCritViolations, hazardRating);
            secondInspection = new Inspection(trackingNumber, janSecond, type, numCritViolations, numNonCritViolations, hazardRating);
        }

        @Test
        public void firstInspectionBeforeSecondInspection() {
            assertEquals(firstInspection.compareTo(secondInspection), -1);
        }

        @Test
        public void firstInspectionSameFirstInspection() {
            assertEquals(firstInspection.compareTo(firstInspection), 0);
        }

        @Test
        public void secondInspectionAfterFirstInspection() {
            assertEquals(secondInspection.compareTo(firstInspection), 1);
        }
    }

    public static class EnumsConstructorArgsCannotBeNullValueTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void inspectionTypeIsNullTypeTest() {
            inspection = new Inspection(trackingNumber, date, InspectionType.NULL_TYPE, numCritViolations, numNonCritViolations, hazardRating);
        }
        @Test
        public void hazardRatingIsNullRatingTest() {
            inspection = new Inspection(trackingNumber, date, InspectionType.NULL_TYPE, numCritViolations, numNonCritViolations, HazardRating.NULL_RATING);
        }
    }
}
