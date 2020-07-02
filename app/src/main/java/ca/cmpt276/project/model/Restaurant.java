package ca.cmpt276.project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    public  final String trackingNumber;     // unique ID
    public  final String name;
    public  final String address;
    public  final String city;
    // GPS coordinates
    public  final double latitude;
    public  final double longitude;

    private final List<Inspection> inspections;


    //////////////////////////////////////////////////////////////////////
    // Constructor

    public Restaurant(String trackingNumber, String name, String address, String city, double latitude, double longitude, List<Inspection> inspections) {
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


    //////////////////////////////////////////////////////////////////////
    // Delegate methods

    public List<Inspection> getInspections() {
        return Collections.unmodifiableList(inspections);
    }


    // Used to populate the list of inspections; outside world shouldn't be able to add anything to list
    protected void inspect(Inspection checkup) {
        inspections.add(checkup);
    }
}
