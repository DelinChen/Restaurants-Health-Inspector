package ca.cmpt276.project.model.data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (
        tableName = "restaurants"
)
public class Restaurant implements Comparable<Restaurant> {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "tracking_number", index = true)
    public final String trackingNumber;     // unique ID

    @NonNull public final String name;
    @NonNull public final String address;
    @NonNull public final String city;
    //unable to add these attribute into restaurant class, crashed the app
//    public LocalDate lastInspection; //to check the last inspection date of this restaurant

    // GPS coordinates
    public final double latitude;
    public final double longitude;

    public static final double MIN_LATITUDE = -90;
    public static final double MAX_LATITUDE = 90;
    public static final double MIN_LONGITUDE = -180;
    public static final double MAX_LONGITUDE = 180;


    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public Restaurant(String trackingNumber, String name, String address, String city, double latitude, double longitude) {
        validateConstructorArgs(trackingNumber, name, address, city, latitude, longitude);
        this.trackingNumber = trackingNumber;
        this.name           = name;
        this.address        = address;
        this.city           = city;
        this.latitude       = latitude;
        this.longitude      = longitude;
//        this.lastInspection = null;
    }


    /////////////////////////////////////////////////////////////////////////////////////
    // Comparable override methods

    @Override
    public int compareTo(Restaurant other) {
        if(other == null) {
            throw new NullPointerException();
        }

        return name.compareTo(other.name);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Setter methods

//    //Setter method for user to set the restaurant to Favourite restaurant
//    public void setIsFavourite(String stringVal){ //String stringVal will be either "0" or "1"
//        if(isFavourite != stringVal){
//            isFavourite = stringVal;
//        }
//    }
//
//    //Setter method for user to set the restaurant's latest inspection date (in LocalDate)
//    public void setLastInspection(LocalDate date){
//        if(lastInspection != date || lastInspection == null){
//            lastInspection = date;
//        }
//    }


    /////////////////////////////////////////////////////////////////////////////////////
    // Object override methods

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
                && (Double.compare(longitude, other.longitude) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, name, city, latitude, longitude);
    }

    @NonNull
    @Override
    public String toString() {
        return "Restaurant<" + trackingNumber + ", " + name
                + ", " + address + ", " + city + ", " + latitude + ", " + longitude + ">";
    }


    /////////////////////////////////////////////////////////////////////////////////////
    // Helper methods

    private static void validateConstructorArgs(String trackingNumber, String name, String address, String city, double latitude, double longitude) {
        String[] argNames = {"trackingNumber", "name", "address", "city", "latitude", "longitude"};
        Object[] argValues = {trackingNumber, name, address, city, latitude, longitude};
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
