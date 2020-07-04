package ca.cmpt276.project.model;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

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

    private static final double MAX_DELTA = 1e-8;

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
    }

    public static class RestaurantConstructorNullArgsTest {
        @Rule
        public ExpectedException thrownNullException = ExpectedException.none();
        @Before
        public void expectNullPointerException() {
            thrownNullException.expect(NullPointerException.class);
        }

        @Test
        public void nullTrackingNumberTest() {
            Restaurant diner = new Restaurant(null, name, address, city, latitude, longitude, inspections);
        }
        @Test
        public void nullNameTest() {
            Restaurant diner = new Restaurant(trackingNumber, null, address, city, latitude, longitude, inspections);
        }
        @Test
        public void nullAddressTest() {
            Restaurant diner = new Restaurant(trackingNumber, name, null, city, latitude, longitude, inspections);
        }
        @Test
        public void nullCityTest() {
            Restaurant diner = new Restaurant(trackingNumber, name, address, null, latitude, longitude, inspections);
        }
        @Test
        public void nullLatitudeTest() {
            Restaurant diner = new Restaurant(trackingNumber, name, address, city, (Double)null, longitude, inspections);
        }
        @Test
        public void nullLongitudeTest() {
            Restaurant diner = new Restaurant(trackingNumber, name, address, city, latitude, (Double)null, inspections);
        }
        @Test
        public void nullInspectionsTest() {
            Restaurant diner = new Restaurant(trackingNumber, name, address, city, latitude, longitude, null);
        }

        @Test
        public void allArgsNullTest() {
            Restaurant diner = new Restaurant(null, null, null, null, (Double)null, (Double)null, null);
        }
    }

    public static class RestaurantConstructorEmptyStringArgsTest {

    }

    @Test
    public void getInspections() {

    }

    @Test
    public void inspect() {

    }
}