package ca.cmpt276.project.model;

import java.io.IOException;

public class RestaurantScanner extends CsvScanner {
    public static final String PATH_TO_RESTAURANT_CSV = "src/main/assets/ProjectData/restaurants_itr1.csv";


    ///////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public RestaurantScanner(String pathToCsvData, boolean headerRow) throws IOException {
        super(pathToCsvData, headerRow);
    }

    public RestaurantScanner(String pathToCsvData) throws IOException {
        this(pathToCsvData, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Methods

    public Restaurant nextRestaurant() {
        String line = super.nextLine();
        return new Restaurant("", "", "" ,"", 0, 0);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Helper methods

}
