package ca.cmpt276.project.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.*;

public class RestaurantManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getInstance() {
    }

    @Test
    public void containsTrackingNumber() {
    }

    @Test
    public void containsRestaurant() {
    }

    @Test
    public void get() {
    }

    @Test
    public void size() {
    }

    @Test
    public void trackingNumberSet() {
    }

    @Test
    public void restaurants() {
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

        RestaurantManager manager = RestaurantManager.getInstance();

        // Attempt reinstantiation through Reflection
        // Found this approach at https://stackoverflow.com/a/2599471
        Constructor<RestaurantManager> constructor = RestaurantManager.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        RestaurantManager instance = constructor.newInstance();
    }
}