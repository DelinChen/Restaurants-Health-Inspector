package ca.cmpt276.project.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Collection;
import java.util.List;

import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.Violation;

@Dao
public abstract class InspectionDao implements BaseDao<Inspection> {
    @Transaction
    @Insert
    public abstract void insert(Inspection inspection, Collection<Violation> violations); /*{
        _insertInspection(arg);
        _insertViolations(arg.violations);
    }*/
/*
    @Insert
    abstract void _insertInspection(Inspection arg);
    @Insert
    abstract void _insertViolations(List<Violation> args);


    @Override
    @Transaction
    public void insertAll(List<Inspection> args) {
        args.forEach(this::insert);
    }
*/
    @Transaction
    @Insert
    public abstract void insertAll(Collection<Inspection> inspections, Collection<Violation> violations);

    @Transaction
    @Query("SELECT * FROM inspections ORDER BY date")
    public abstract LiveData<List<InspectionDetails>> getAllInspectionDetails();

    @Transaction
    @Query("SELECT * FROM inspections WHERE tracking_number = :trackingNumber ORDER BY date DESC")
    public abstract LiveData<List<InspectionDetails>> findAllInspectionDetailsForRestaurant(String trackingNumber);

}
