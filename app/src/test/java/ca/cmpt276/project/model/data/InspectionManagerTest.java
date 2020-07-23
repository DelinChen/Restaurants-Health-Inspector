package ca.cmpt276.project.model.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.InspectionManager;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertTrue;

public class InspectionManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getInstanceTest() {
        InspectionManager manager = InspectionManager.getInstance();
        for(List<Inspection> inspections : manager.inspections()){
            System.out.println(inspections);
        }

        assertTrue(manager.size() != 0);
    }

    @Test
    public void containsTrackingNumber_returnsTrueTest() {

    }
    @Test
    public void containsTrackingNumber_returnsFalseTest() {

    }

    @Test
    public void containsInspection_returnsTrueTest() {

    }
    @Test
    public void containsInspection_returnsFalseTest() {
    }

    @Test
    public void get_getSuccessTest() {

    }
    @Test
    public void get_getDefaultTest() {

    }

    @Test
    public void getOrDefault_getSuccessTest() {

    }
    @Test
    public void getOrDefault_getDefaultTest() {

    }

    @Test
    public void size_scannedAllInspectionsTest() {

    }

    @Test
    public void trackingNumberSet() {

    }

    @Test
    public void inspections_unmodifiableListTest() {

    }

    @Test
    public void entrySet() {
    }

    @Test
    public void put() {
    }

    @Test
    public void onReinstantiation_throwsExceptionTest() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Found isA(...) at https://stackoverflow.com/a/20759785
        thrown.expectCause(isA(IllegalStateException.class));

        Map<String, Inspection> inspectionMap = new HashMap<>();
        InspectionManager manager = InspectionManager.getInstance();

        // Attempt reinstantiation through Reflection
        // Found this approach at https://stackoverflow.com/a/2599471
        Constructor<InspectionManager> constructor = InspectionManager.class.getDeclaredConstructor(Map.class);
        constructor.setAccessible(true);
        InspectionManager instance = constructor.newInstance(inspectionMap);
    }

}
