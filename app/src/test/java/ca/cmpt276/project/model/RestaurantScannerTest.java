package ca.cmpt276.project.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class RestaurantScannerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readsOneRestaurant() throws IOException {
        RestaurantScanner restaurantScanner = new RestaurantScanner(RestaurantScanner.PATH_TO_RESTAURANT_CSV);

        Restaurant expected = new Restaurant("SDFO-8HKP7E", "Pattullo A&W", "12808 King George Blvd", "Surrey", 40.20610961, -122.8668064);
        Restaurant scanned = restaurantScanner.nextRestaurant();

        assertEquals(expected, scanned);
    }

}