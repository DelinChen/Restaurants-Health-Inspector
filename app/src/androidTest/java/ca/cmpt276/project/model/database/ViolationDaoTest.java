package ca.cmpt276.project.model.database;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;
import java.util.stream.Collectors;

import ca.cmpt276.project.model.database.HealthDatabase;
import ca.cmpt276.project.model.data.InspectionManager;
import ca.cmpt276.project.model.data.Violation;
import ca.cmpt276.project.model.data.ViolationCategory;
import ca.cmpt276.project.model.database.ViolationDao;

@SmallTest
public class ViolationDaoTest {
    public static final String LOG_TAG = "ViolationDao";

    private static final int codeNumber = 201;
    private static final boolean isCritical = true;
    private static final ViolationCategory category = ViolationCategory.FOOD;
    private static final String description = "Food contaminated or unfit for human consumption [s. 13]";

    private Context appContext;
    private HealthDatabase db;
    private Violation instance;
    private ViolationDao violationDao;


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void initialize() {
        instance = new Violation(codeNumber, isCritical, category, description);
        appContext = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(appContext, HealthDatabase.class)
                .fallbackToDestructiveMigration()
                .build();
        violationDao = db.getViolationDao();
    }

    @Test
    public void insertSingleViolation_successTest() {
        // Found testing procedure at https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9

        LiveData<List<Violation>> data = violationDao.getAllViolations();
        data.observeForever(printAll);

        violationDao.insert(instance);
    }

    @Test
    public void insertAllViolations_successTest() {
        LiveData<List<Violation>> data = violationDao.getAllViolations();
        data.observeForever(printAll);

        // Get all violations
        InspectionManager inspectionManager = InspectionManager.getInstance(appContext);
        List<Violation> violations = inspectionManager.inspections()
                .stream()
                .flatMap(List::stream)
                .flatMap(inspection -> inspection.violations.stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        violationDao.insertAll(violations);
    }

    @Test
    public void getAllViolations_successTest() {
        HealthDatabase testDb = Room.databaseBuilder(appContext, HealthDatabase.class, "health_test.db")
                .createFromAsset("database/health.db")
                .build();

        testDb.getViolationDao().getAllViolations().observeForever(printAll);
    }

    private static final Observer<List<Violation>> printAll = violations -> {
        Log.i(LOG_TAG, "Violations in database:");
        violations.forEach(v -> Log.i(LOG_TAG, v.toString()));
    };
}
