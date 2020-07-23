package ca.cmpt276.project.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ca.cmpt276.project.model.data.Violation;

@Dao
public abstract class ViolationDao implements BaseDao<Violation> {
    @Query("SELECT * FROM violations")
    public abstract LiveData<List<Violation>> getAllViolations();

    @Query("SELECT * FROM violations WHERE code_number = :codeNumber")
    public abstract Violation findViolationByCodeNumber(int codeNumber);

    @Query("SELECT violations.* FROM inspection_violations_crossref INNER JOIN violations "
            + "ON inspection_id = :inspectionId AND inspection_violations_crossref.code_number = violations.code_number")
    public abstract LiveData<List<Violation>> findViolationsForInspectionId(int inspectionId);
}
