package com.example.project.Model;

public class Violation {
    private int viol_num;
    private boolean isCritical;
    private String hazardType;
    private String description;

    public Violation(int viol_num, boolean isCritical, String hazardType, String description) {
        this.viol_num = viol_num;
        this.isCritical = isCritical;
        this.hazardType = hazardType;
        this.description = description;
    }

    public int getViol_num() {
        return viol_num;
    }

    public void setViol_num(int viol_num) {
        this.viol_num = viol_num;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public String getHazardType() {
        return hazardType;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
