package ca.cmpt276.project.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RestaurantScannerTest {
    public static final int NUM_RESTAURANTS_IN_CSV = 8;
    public static final InspectionManager inspectionManager = InspectionManager.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readsOneRestaurant() throws IOException {
        RestaurantScanner scanner = new RestaurantScanner(RestaurantScanner.PATH_TO_RESTAURANT_CSV_FROM_SRC);
        InspectionManager inspectionManager = InspectionManager.getInstance();
        List<Inspection> inspectionsList = inspectionManager.getOrDefault("SDFO-8HKP7E", Collections.emptyList());

        Restaurant expected = new Restaurant("SDFO-8HKP7E", "Pattullo A&W", "12808 King George Blvd", "Surrey", 49.20610961, -122.8668064, inspectionsList);
        Restaurant scanned = scanner.nextRestaurant();

        assertEquals(expected, scanned);
    }

    @Test
    public void readsEntireRestaurantsFile_itr1Test() throws IOException {
        RestaurantScanner scanner = new RestaurantScanner(RestaurantScanner.PATH_TO_RESTAURANT_CSV_FROM_SRC);
        List<Object> allScanned = new ArrayList<>();
        while(scanner.hasNextLine()) {
            allScanned.add(scanner.nextRestaurant());
        }
        assertEquals(NUM_RESTAURANTS_IN_CSV, allScanned.size());
    }
}