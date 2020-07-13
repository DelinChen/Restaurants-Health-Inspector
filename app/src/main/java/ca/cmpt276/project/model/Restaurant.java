package ca.cmpt276.project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity ( tableName = "restaurants" )
public class Restaurant implements Comparable<Restaurant> {
    @PrimaryKey
    @NonNull
    public final String trackingNumber;     // unique ID
    @ColumnInfo( name = "name")
    public final String name;
    @ColumnInfo( name = "address")
    public final String address;
    @ColumnInfo( name = "city")
    public final String city;
//     GPS coordinates
    @ColumnInfo( name = "latitude")
    public final double latitude;
    @ColumnInfo( name = "longitude")
    public final double longitude;
    @Ignore
    public final List<Inspection> inspections;

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
        this.inspections    = Collections.unmodifiableList(inspections);
    }

    public Restaurant(String trackingNumber, String name, String address, String city, double latitude, double longitude) {
        this(trackingNumber, name, address, city, latitude, longitude, Collections.emptyList());
    }


    @Override
    public int compareTo(Restaurant other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant)o;
        return allFieldsEqualWith(other);
    }
    private boolean allFieldsEqualWith(Restaurant other) {
        return trackingNumber.equals(other.trackingNumber)
                && name.equals(other.name)
                && address.equals(other.address)
                && city.equals(other.city)
                && (Double.compare(latitude, other.latitude) == 0)
                && (Double.compare(longitude, other.longitude) == 0)
                && inspections.equals(other.inspections);
    }


    /////////////////////////////////////////////////////////////////////////////////////
    // Helper methods

    private static void validateConstructorArgs(String trackingNumber, String name, String address, String city, double latitude, double longitude, List<Inspection> inspections) {
        String[] argNames = {"trackingNumber", "name", "address", "city", "latitude", "longitude", "inspections"};
        Object[] argValues = {trackingNumber, name, address, city, latitude, longitude, inspections};
        ConstructorArguments.requireArgsNonNull(argNames, argValues, Restaurant.class);

        String[] stringArgNames = {"trackingNumber", "name", "address", "city"};
        String[] stringArgValues = {trackingNumber, name, address, city};
        ConstructorArguments.requireStringArgsNonEmpty(stringArgNames, stringArgValues, Restaurant.class);

        requireGpsCoordsNotNaN(latitude, longitude);
        requireGpsCoordsInRange(latitude, longitude);
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
