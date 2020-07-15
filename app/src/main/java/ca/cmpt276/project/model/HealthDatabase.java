package ca.cmpt276.project.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        version = 1,
        entities = {Restaurant.class, Inspection.class, Violation.class, InspectionViolationCrossref.class})
@TypeConverters(Converters.class)
public abstract class HealthDatabase extends RoomDatabase {
    public abstract InspectionDao getInspectionDao();
    public abstract ViolationDao getViolationDao();
}
