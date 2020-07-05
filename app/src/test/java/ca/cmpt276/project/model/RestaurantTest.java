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
public class RestaurantTest {
    private static final String trackingNumber = "SDFO-8HKP7E";
    private static final String name = "Pattullo A&W";
    private static final String address = "12808 King George Blvd";
    private static final String city = "Surrey";
    private static final double latitude = 40.20610961;
    private static final double longitude = -122.8668064;
    private static final List<Inspection> inspections = new ArrayList<>();

    private static final double MAX_DELTA = 1e-12;

    public static class RestaurantMethodTest {
        public Restaurant instance = null;

        @Before
        public void initialize() {
            instance = new Restaurant(trackingNumber, name, address, city, latitude, longitude, inspections);
        }

        @Test
        public void getInspections () {

        }

        @Test
        public void inspect () {

        }
    }

    public static class ValidRestaurantConstructorTest {
        private Restaurant instance = null;

        @Before
        public void initialize() {
            instance = new Restaurant(trackingNumber, name, address, city, latitude, longitude, inspections);
        }

        @Test
        public void validTrackingNumberTest() {
            assertEquals(trackingNumber, instance.trackingNumber);
        }
        @Test
        public void validNameTest() {
            assertEquals(name, instance.name);
        }
        @Test
        public void validAddressTest() {
            assertEquals(address, instance.address);
        }
        @Test
        public void validCityTest() {
            assertEquals(city, instance.city);
        }
        @Test
        public void validLatitudeTest() {
            assertEquals(latitude, instance.latitude, MAX_DELTA);
        }
        @Test
        public void validLongitudeTest() {
            assertEquals(longitude, instance.longitude, MAX_DELTA);
        }
        @Test
        public void validInspectionsTest() {
            assertEquals(inspections, instance.getInspections());
        }
        @Test
        public void latitudeOnLowerBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, MIN_LATITUDE, longitude, inspections);
        }
        @Test
        public void latitudeOnUpperBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, MAX_LATITUDE, longitude, inspections);
        }
        @Test
        public void longitudeOnLowerBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, MIN_LONGITUDE, inspections);
        }
        @Test
        public void longitudeOnUpperBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, MAX_LONGITUDE, inspections);
        }
    }

    public static class RestaurantConstructorNullArgsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectNullPointerException() {
            thrown.expect(NullPointerException.class);
        }

        @Test
        public void nullTrackingNumberTest() {
            Restaurant instance = new Restaurant(null, name, address, city, latitude, longitude, inspections);
        }
        @Test
        public void nullNameTest() {
            Restaurant instance = new Restaurant(trackingNumber, null, address, city, latitude, longitude, inspections);
        }
        @Test
        public void nullAddressTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, null, city, latitude, longitude, inspections);
        }
        @Test
        public void nullCityTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, null, latitude, longitude, inspections);
        }
        @Test
        public void nullLatitudeTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, (Double)null, longitude, inspections);
        }
        @Test
        public void nullLongitudeTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, (Double)null, inspections);
        }
        @Test
        public void nullInspectionsTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, longitude, null);
        }

        @Test
        public void allArgsNullTest() {
            Restaurant instance = new Restaurant(null, null, null, null, (Double)null, (Double)null, null);
        }
    }

    public static class RestaurantConstructorEmptyStringArgsTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void emptyTrackingNumberTest() {
            Restaurant instance = new Restaurant("", name, address, city, latitude, longitude, inspections);
        }
        @Test
        public void emptyNameTest() {
            Restaurant instance = new Restaurant(trackingNumber, "", address, city, latitude, longitude, inspections);
        }
        @Test
        public void emptyAddressTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, "", city, latitude, longitude, inspections);
        }
        @Test
        public void emptyCityTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, "", latitude, longitude, inspections);
        }

        @Test
        public void allArgsEmptyTest() {
            Restaurant instance = new Restaurant("", "", "", "", latitude, longitude, inspections);
        }
    }

    public static class RestaurantConstructorGpsArgsNaNTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void latitudeNaNTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, Double.NaN, longitude, inspections);
        }
        @Test
        public void longitudeNaNTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, Double.NaN, inspections);
        }
    }

    public static class RestaurantConstructorGpsArgsOutOfRangeTest {
        @Rule
        public ExpectedException thrown = ExpectedException.none();
        @Before
        public void expectIllegalArgumentException() {
            thrown.expect(IllegalArgumentException.class);
        }

        @Test
        public void latitudeUnderLowerBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, MIN_LATITUDE - MAX_DELTA, longitude, inspections);
        }
        @Test
        public void latitudeAboveUpperBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, MAX_LATITUDE + MAX_DELTA, longitude, inspections);
        }
        @Test
        public void longitudeUnderLowerBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, MIN_LONGITUDE - MAX_DELTA, inspections);
        }
        @Test
        public void longitudeAboveLowerBoundTest() {
            Restaurant instance = new Restaurant(trackingNumber, name, address, city, latitude, MAX_LONGITUDE + MAX_DELTA, inspections);
        }
    }
}