package ca.cmpt276.project.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.Collection;

@Dao
/**
 * Pattern found at https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1#026b
 * Tip #2
 */
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(T arg);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Collection<T> args);
}
