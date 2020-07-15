package ca.cmpt276.project.model;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Dao
public abstract class InspectionDao implements BaseDao<Inspection> {
    @Query("SELECT * FROM inspections")
    public abstract List<Inspection> getAllInspections();

    @Query("SELECT * FROM inspections WHERE tracking_number = :trackingNumber")
    public abstract List<Inspection> findAllInspectionsForRestaurant(String trackingNumber);

    @Query("SELECT * FROM inspections WHERE tracking_number = :trackingNumber AND date = :date")
    public abstract Inspection findInspectionForRestaurantOnDate(String trackingNumber, LocalDate date);

    @Query("SELECT violations.* FROM inspection_violations_crossref INNER JOIN violations "
            + "ON violations.code_number = inspection_violations_crossref.code_number AND tracking_number = :trackingNumber AND date = :date")
    public abstract List<Violation> findAllViolationsForInspection(String trackingNumber, LocalDate date);

    @Transaction
    public InspectionWithViolations findInspectionWithViolations(String trackingNumber, LocalDate date) {
        Inspection inspection = findInspectionForRestaurantOnDate(trackingNumber, date);
        List<Violation> violations = findAllViolationsForInspection(trackingNumber, date);
        return new InspectionWithViolations(inspection, violations);
    }

    @Transaction
    public List<InspectionWithViolations> findAllInspectionsWithViolations() {
        return getAllInspections().parallelStream()
                .map(inspection -> findInspectionWithViolations(inspection.trackingNumber, inspection.date))
                .collect(Collectors.toList());
    }

    @Transaction
    public List<InspectionWithViolations> findAllInspectionsWithViolationsForRestaurant(String trackingNumber) {
        return findAllInspectionsForRestaurant(trackingNumber).parallelStream()
                .map(inspection -> findInspectionWithViolations(inspection.trackingNumber, inspection.date))
                .collect(Collectors.toList());
    }
}
