package ca.cmpt276.project.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Dao
public abstract class InspectionDao implements BaseDao<Inspection> {
    @Transaction
    @Query("SELECT * FROM inspections")
    public abstract LiveData<List<InspectionDetails>> getAllInspectionDetails();

    @Transaction
    @Query("SELECT * FROM inspections WHERE tracking_number = :trackingNumber")
    public abstract LiveData<List<InspectionDetails>> findAllInspectionDetailsForRestaurant(String trackingNumber);

}
