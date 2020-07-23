package ca.cmpt276.project.model.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;

import ca.cmpt276.project.model.data.CsvScanner;

import static ca.cmpt276.project.model.data.RestaurantScanner.PATH_TO_RESTAURANT_CSV_FROM_SRC;
import static org.junit.Assert.*;

public class CsvScannerTest {
    public static final String PATH_TO_SRC_DIR = "src/";
    public static final String PATH_TO_EMPTY_FILE = "src/test/fixtures/emptyFile.txt";
    public static final String PATH_TO_NONEXISTENT_FILE = "doesntexist.txt";
    public static final String PATH_TO_UNREADABLE_FILE = "src/test/fixtures/unreadableFile.txt";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void hasNextLine() throws IOException {
        CsvScanner scanner = new CsvScanner(PATH_TO_RESTAURANT_CSV_FROM_SRC){};
        assertTrue(scanner.hasNextLine());
    }

    @Test
    public void opensValidFileForReadingTest() throws IOException {
        CsvScanner scanner = new CsvScanner(PATH_TO_RESTAURANT_CSV_FROM_SRC){};
    }

    @Test
    public void hasNextLineIsFalseTest() throws IOException {
        CsvScanner scanner = new CsvScanner(PATH_TO_EMPTY_FILE){};
        assertFalse(scanner.hasNextLine());
    }

    @Test
    public void onCsvDataNotFound_throwsExceptionTest() throws IOException {
        thrown.expect(FileNotFoundException.class);
        CsvScanner scanner = new CsvScanner(PATH_TO_NONEXISTENT_FILE){};
    }

    @Test
    public void onCsvDataIsNotAFile_throwsExceptionTest() throws IOException {
        thrown.expect(IllegalArgumentException.class);
        CsvScanner scanner = new CsvScanner(PATH_TO_SRC_DIR, true){};
    }

    @Test
    public void onCsvDataIsNotReadable_throwsExceptionTest() throws IOException {
        thrown.expect(IOException.class);
        CsvScanner scanner = new CsvScanner(PATH_TO_UNREADABLE_FILE, true){};
    }
}
