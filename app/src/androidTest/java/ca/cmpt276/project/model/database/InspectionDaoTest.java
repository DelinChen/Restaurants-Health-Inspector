package ca.cmpt276.project.model.database;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.cmpt276.project.model.data.HazardRating;
import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.InspectionType;
import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.data.ViolationCategory;

@SmallTest
public class InspectionDaoTest {
    public static final String LOG_TAG = "InspectionDao";

    private static final String         trackingNumber  = "SHEN-B7BNSR";
    private static final LocalDate      date            = LocalDate.of(2019, 1, 30);
    private static final InspectionType type            = InspectionType.FOLLOWUP;
    private static final HazardRating   hazardRating    = HazardRating.LOW;
    private static final int numCritViolations    = 1;
    private static final int numNonCritViolations = 1;
    private static List<Violation> violations;

    private Context appContext;
    private HealthDatabase db;
    private InspectionDetails instance;
    private InspectionDao inspectionDao;


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void initialize() {
        Inspection inspection = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, hazardRating);

        final Violation firstViolation = new Violation(
                308, false, ViolationCategory.fromCodeNumber(308),
                "Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)]");
        final Violation secondViolation = new Violation(
                401, true, ViolationCategory.fromCodeNumber(401),
                "Adequate handwashing stations not available for employees [s. 21(4)]");
        final Violation[] violationsBuffer = {firstViolation, secondViolation};
        violations = new ArrayList<>(Arrays.asList(violationsBuffer));

        instance = new InspectionDetails(inspection, violations);
        appContext = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(appContext, HealthDatabase.class)
                .fallbackToDestructiveMigration()
                .build();
        inspectionDao = db.getInspectionDao();
    }


    @Test
    public void insertSingleInspection_successTest() {
        db.getViolationDao().insertAll(instance.violations);
        inspectionDao.getAllInspectionDetails().observeForever(printAll);
        inspectionDao.insert(instance.inspection, instance.violations);

    }

    @Test
    public void getAllInspectionDetails_successTest() {
        db = Room.databaseBuilder(appContext, HealthDatabase.class, "health_test.db")
                .createFromAsset("database/health.db")
                .build();

        db.getInspectionDao().getAllInspectionDetails().observeForever(printAll);
    }

    private static final Observer<List<InspectionDetails>> printAll = inspectionDetails -> {
        Log.i(LOG_TAG, "Inspections in database:");
        inspectionDetails.forEach(inspection -> Log.i(LOG_TAG, inspection.toString()));
    };
}
