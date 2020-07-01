package com.example.a276project.Model;

public class Restaurant {
    private String rest_name;
    private String rest_addr;
    private String GPS_coord;
    private String rest_tracking_num;
    private int num_reports;



    //decide to add image in the UI, not in the class
    public Restaurant(String rest_name, String rest_addr, String GPS_coord, String rest_tracking_num, int num_reports) {
        this.rest_name = rest_name;
        this.rest_addr = rest_addr;
        this.GPS_coord = GPS_coord;
        this.rest_tracking_num = rest_tracking_num;
        this.num_reports = num_reports;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getRest_addr() {
        return rest_addr;
    }

    public void setRest_addr(String rest_addr) {
        this.rest_addr = rest_addr;
    }

    public String getGPS_coord() {
        return GPS_coord;
    }

    public void setGPS_coord(String GPS_coord) {
        this.GPS_coord = GPS_coord;
    }

    public String getRest_tracking_num() {
        return rest_tracking_num;
    }

    public void setRest_tracking_num(String rest_tracking_num) {
        this.rest_tracking_num = rest_tracking_num;
    }

    public int getNum_reports() {
        return num_reports;
    }

    public void setNum_reports(int num_reports) {
        this.num_reports = num_reports;
    }
}
