package ca.cmpt276.project.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface InspectionDao {
    //i will keep all daos' function number same, so it will be easier for us to call them.
    @Insert
    void insert(Inspection inpsection);

    @Update
    void update(Inspection inspection);

    @Delete
    void delete(Inspection inpsection);

    @Query("DELETE FROM inspections")
    void deleteAllInspections();

    @Query("SELECT * FROM inspections WHERE trackingNumber = :trackingNumber AND InspectionDate=:InspectionDate ")
    LiveData<Inspection> getInspectionById(String trackingNumber, LocalDate InspectionDate);

    @Query("SELECT* FROM inspections")
    LiveData<List<Inspection>> getALLInspections();


}

