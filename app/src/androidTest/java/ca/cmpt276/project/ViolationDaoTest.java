package ca.cmpt276.project;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import ca.cmpt276.project.model.HealthDatabase;
import ca.cmpt276.project.model.Violation;
import ca.cmpt276.project.model.ViolationCategory;
import ca.cmpt276.project.model.ViolationDao;

@SmallTest
public class ViolationDaoTest {
    public static final String LOG_TAG = "ViolationDao";

    private static final int codeNumber = 201;
    private static final boolean isCritical = true;
    private static final ViolationCategory category = ViolationCategory.FOOD;
    private static final String description = "Food contaminated or unfit for human consumption [s. 13]";

    private HealthDatabase db;
    private Violation violation;
    private Context appContext;


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void initialize() {
        violation = new Violation(codeNumber, isCritical, category, description);
        appContext = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(appContext, HealthDatabase.class)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Test
    public void insertSingleViolation_successTest() {
        // Found testing procedure at https://proandroiddev.com/how-to-unit-test-livedata-and-lifecycle-components-8a0af41c90d9

        ViolationDao violationDao = db.getViolationDao();
        LiveData<List<Violation>> data = violationDao.getAllViolations();
        data.observeForever(violations -> {
            violations.forEach(v -> Log.i(LOG_TAG, v.toString()));
        });

        Log.i(LOG_TAG, "Violations in database:");
        violationDao.insert(violation);
    }
}
