package ca.cmpt276.project.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
/**
 * Pattern found at https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1#026b
 * Tip #2
 */
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T arg);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<T> args);
}
