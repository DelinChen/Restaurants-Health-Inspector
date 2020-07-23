package ca.cmpt276.project.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.Violation;

@Database(
        version = 1,
        entities = {Restaurant.class, Inspection.class, Violation.class, InspectionViolationCrossref.class})
@TypeConverters(Converters.class)
public abstract class HealthDatabase extends RoomDatabase {
    // Follows from guideline found at http://tutorials.jenkov.com/java-concurrency/volatile.html
    // and https://stackoverflow.com/a/50105730

    public static final String DB_NAME = "health.db";

    private static volatile HealthDatabase instance;

    public abstract RestaurantDao getRestaurantDao();
    public abstract InspectionDao getInspectionDao();
    public abstract ViolationDao getViolationDao();

    public static synchronized HealthDatabase getInstance(final Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), HealthDatabase.class, "health.db")
                    .createFromAsset("database/health.db")
                    .build();
        }
        return instance;
    }
}
