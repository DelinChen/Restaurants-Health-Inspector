package ca.cmpt276.project.model;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class RestaurantScanner extends CsvScanner {
    public static final String PATH_TO_RESTAURANT_CSV_FROM_SRC = "src/main/assets/ProjectData/restaurants_itr1.csv";
    public static final String PATH_TO_RESTAURANT_CSV_FROM_ASSETS = "ProjectData/restaurants_itr1.csv";
    public static final int UNUSED_COLUMN = 4;


    ///////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public RestaurantScanner(InputStream input, boolean hasHeaderRow) {
        super(input, hasHeaderRow);
    }
    public RestaurantScanner(InputStream input) {
        super(input);
    }

    public RestaurantScanner(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        super(pathToCsvData, hasHeaderRow);
    }

    public RestaurantScanner(String pathToCsvData) throws IOException {
        this(pathToCsvData, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Methods

    public Restaurant nextRestaurant() {
        String line = super.nextLine();
        String[] buffer = line.split(",");
        for(int i = 0; i < buffer.length; i++) {
            String field = buffer[i];
            buffer[i] = field.replace("\"", "");
        }
        String trackingNumber = buffer[0];
        String name         = buffer[1];
        String address      = buffer[2];
        String city         = buffer[3];
        double latitude     = Double.parseDouble(buffer[5]);
        double longitude    = Double.parseDouble(buffer[6]);
        return new Restaurant(trackingNumber, name, address, city, latitude, longitude);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Helper methods

}
