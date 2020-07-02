package ca.cmpt276.project.model;

import java.util.List;

public class Inspection {
    private String inspect_date;
    private String inspect_type;
    private int inspect_crit_issue;
    private int inspect_nonCrit_issue;
    private int hazaradRating; //0 = low, 1 = moderate, 2= high
    private List<Violation> violationList;

    public Inspection(String inspect_date, String inspect_type, int inspect_crit_issue, int inspect_nonCrit_issue, int hazaradRating, List<Violation> violationList) {
        this.inspect_date = inspect_date;
        this.inspect_type = inspect_type;
        this.inspect_crit_issue = inspect_crit_issue;
        this.inspect_nonCrit_issue = inspect_nonCrit_issue;
        this.hazaradRating = hazaradRating;
        this.violationList = violationList;
    }

    public String getInspect_date() {
        return inspect_date;
    }

    public void setInspect_date(String inspect_date) {
        this.inspect_date = inspect_date;
    }

    public String getInspect_type() {
        return inspect_type;
    }

    public void setInspect_type(String inspect_type) {
        this.inspect_type = inspect_type;
    }

    public int getInspect_crit_issue() {
        return inspect_crit_issue;
    }

    public void setInspect_crit_issue(int inspect_crit_issue) {
        this.inspect_crit_issue = inspect_crit_issue;
    }

    public int getInspect_nonCrit_issue() {
        return inspect_nonCrit_issue;
    }

    public void setInspect_nonCrit_issue(int inspect_nonCrit_issue) {
        this.inspect_nonCrit_issue = inspect_nonCrit_issue;
    }

    public int getHazaradRating() {
        return hazaradRating;
    }

    public void setHazaradRating(int hazaradRating) {
        this.hazaradRating = hazaradRating;
    }

    public List<Violation> getViolationList() {
        return violationList;
    }

    public void setViolationList(List<Violation> violationList) {
        this.violationList = violationList;
    }
}
