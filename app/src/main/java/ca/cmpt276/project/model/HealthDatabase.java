package ca.cmpt276.project.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        version = 1,
        entities = {Restaurant.class, Inspection.class, Violation.class, InspectionViolationCrossref.class})
@TypeConverters(Converters.class)
public abstract class HealthDatabase extends RoomDatabase {
    private static HealthDatabase instance;

    public abstract RestaurantDao getRestaurantDao();
    public abstract InspectionDao getInspectionDao();
    public abstract ViolationDao getViolationDao();

    public static HealthDatabase getInstance(final Context context) {
        synchronized(HealthDatabase.class) {
            if(instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(), HealthDatabase.class, "health_db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }
}
