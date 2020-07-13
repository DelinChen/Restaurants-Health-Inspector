package ca.cmpt276.project.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ViolationDao {

    @Insert
    void insert(Violation violation);

    //should be no use in this itr
    @Update
    void update(Violation violation);

    //No use on this itr too
    @Delete
    void delete(Violation violation);

    @Query("DELETE FROM violations")
    void deleteAllViolations();

    @Query("SELECT * FROM violations WHERE codeNumber = :codeNumber")
    LiveData<Restaurant> getRestaurantById(int codeNumber);

    @Query("SELECT * FROM violations")
    LiveData<List<Violation>> getAllViolations();
}
