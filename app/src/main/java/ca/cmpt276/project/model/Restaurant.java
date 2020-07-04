package ca.cmpt276.project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Restaurant {
    public final String trackingNumber;     // unique ID
    public final String name;
    public final String address;
    public final String city;
    // GPS coordinates
    public final double latitude;
    public final double longitude;

    private final List<Inspection> inspections;

    public static final double MIN_LATITUDE = -90;
    public static final double MAX_LATITUDE = 90;
    public static final double MIN_LONGITUDE = -180;
    public static final double MAX_LONGITUDE = 180;


    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public Restaurant(String trackingNumber, String name, String address, String city, double latitude, double longitude, List<Inspection> inspections) {
        validateConstructorArgs(trackingNumber, name, address, city, latitude, longitude, inspections);
        this.trackingNumber = trackingNumber;
        this.name           = name;
        this.address        = address;
        this.city           = city;
        this.latitude       = latitude;
        this.longitude      = longitude;
        // dependency injection
        this.inspections    = inspections;
    }

    public Restaurant(String trackingNumber, String name, String address, String city, double latitude, double longitude) {
        this(trackingNumber, name, address, city, latitude, longitude, new ArrayList<Inspection>());
    }


    /**
     * Used to populate the list of inspections; outside world shouldn't be able to add anything to list
     * @param checkup the Inspection to add to this Restaurant object
     */
    protected void inspect(Inspection checkup) {
        inspections.add(checkup);
    }


    /////////////////////////////////////////////////////////////////////////////////////
    // Getter methods
    //     No mutator methods are allowed because there is currently no need to change a restaurant's data dynamically
    //     i.e. the class is essentially just a read-only interface to the csv data

    public List<Inspection> getInspections() {
        return Collections.unmodifiableList(inspections);
    }


    /////////////////////////////////////////////////////////////////////////////////////
    // Helper methods

    private static void validateConstructorArgs(String trackingNumber, String name, String address, String city, double latitude, double longitude, List<Inspection> inspections) {
        String[] argNames = {"trackingNumber", "name", "address", "city", "latitude", "longitude", "inspections"};
        Object[] argValues = {trackingNumber, name, address, city, latitude, longitude, inspections};
        requireArgsNonNull(argNames, argValues);

        String[] stringArgNames = {"trackingNumber", "name", "address", "city"};
        String[] stringArgValues = {trackingNumber, name, address, city};
        requireStringArgsNonEmpty(stringArgNames, stringArgValues);

        requireGpsCoordsNotNaN(latitude, longitude);
        requireGpsCoordsInRange(latitude, longitude);
    }

    private static void requireArgsNonNull(String[] argNames, Object[] argValues) {
        if(argNames.length != argValues.length) {
            throw new IllegalArgumentException("argNames.length must be equal to argValues.length");
        }

        for(int i = 0; i < argNames.length; i++) {
            Objects.requireNonNull(argValues[i], "Restaurant field " + argNames[i] + " cannot be null");
        }
    }

    private static void requireStringArgsNonEmpty(String[] stringArgNames, String[] stringArgValues) {
        if(stringArgNames.length != stringArgValues.length) {
            throw new IllegalArgumentException("stringArgNames.length must be equal to stringArgValues.length");
        }

        for(int i = 0; i < stringArgValues.length; i++) {
            if(stringArgValues[i].length() == 0) {
                throw new IllegalArgumentException("Restaurant string field " + stringArgNames[i] + " cannot be the empty string");
            }
        }
    }

    private static void requireGpsCoordsNotNaN(double latitude, double longitude) {
        if(Double.isNaN(latitude)) {
            throw new IllegalArgumentException(gpsNaNErrorMsg("Latitude"));
        }
        if(Double.isNaN(longitude)) {
            throw new IllegalArgumentException(gpsNaNErrorMsg("Longitude"));
        }
    }

    private static void requireGpsCoordsInRange(double latitude, double longitude) {
        final String latitudeClosedInterval = "[-90, 90]";
        final String longitudeClosedInterval = "[-180, 180]";

        if(Double.compare(latitude, MIN_LATITUDE) < 0) {
            throw new IllegalArgumentException(gpsRangeErrorMsg("Latitude", "less than -90", latitudeClosedInterval));
        }
        if(Double.compare(latitude, MAX_LATITUDE) > 0) {
            throw new IllegalArgumentException(gpsRangeErrorMsg("Latitude", "greater than 90", latitudeClosedInterval));
        }

        if(Double.compare(longitude, MIN_LONGITUDE) < 0) {
            throw new IllegalArgumentException(gpsRangeErrorMsg("Longitude", "less than -180", longitudeClosedInterval));
        }
        if(Double.compare(longitude, MAX_LONGITUDE) > 0) {
            throw new IllegalArgumentException(gpsRangeErrorMsg("Longitude", "greater than 180", longitudeClosedInterval));
        }
    }

    private static String gpsNaNErrorMsg(String axis) {
        return axis + " cannot be NaN";
    }
    private static String gpsRangeErrorMsg(String axis, String outOfRange, String closedInterval) {
        return axis + " is undefined for numbers " + outOfRange + ", must be in the closed interval " + closedInterval;
    }
}
