package ca.cmpt276.project.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.*;

public class RestaurantManagerFactoryTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void onInstantiation_throwsExceptionTest() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Found isA(...) at https://stackoverflow.com/a/20759785
        thrown.expectCause(isA(InstantiationException.class));
        // Attempt instantiation through Reflection
        // Found this approach at https://stackoverflow.com/a/2599471
        Constructor<RestaurantManagerFactory> factoryConstructor = RestaurantManagerFactory.class.getDeclaredConstructor();
        factoryConstructor.setAccessible(true);
        RestaurantManagerFactory factory = factoryConstructor.newInstance();
    }

    @Test
    public void onCsvDataNotFound_throwsExceptionTest() throws IOException {
        thrown.expect(FileNotFoundException.class);
        RestaurantManagerFactory.populateFromCsv("doesntexist.csv");
    }

    @Test
    public void onCsvDataIsNotAFile_throwsExceptionTest() throws IOException {
        thrown.expect(IllegalArgumentException.class);
        RestaurantManagerFactory.populateFromCsv("src/");
    }

    @Test
    public void onCsvDataIsNotReadable_throwsExceptionTest() throws IOException {
        thrown.expect(IOException.class);
        RestaurantManagerFactory.populateFromCsv("src/test/fixtures/unreadableFile.txt");
    }
}