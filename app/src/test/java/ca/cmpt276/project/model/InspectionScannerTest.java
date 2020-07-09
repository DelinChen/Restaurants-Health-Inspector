package ca.cmpt276.project.model;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static ca.cmpt276.project.model.InspectionScanner.PATH_TO_INSPECTION_CSV_FROM_SRC;
import static org.junit.Assert.*;

public class InspectionScannerTest {
    @Test
    public void scanOneInspectionTest() throws IOException, ParseException {
        InspectionScanner scanner = new InspectionScanner(PATH_TO_INSPECTION_CSV_FROM_SRC);

        // Expected fields
        String trackingNumber = "SDFO-8HKP7E";
        LocalDate date = LocalDate.parse("20191002", DateTimeFormatter.BASIC_ISO_DATE);
        InspectionType type = InspectionType.fromString("Routine");
        int numCritViolations = 0;
        int numNonCritViolations = 0;
        HazardRating rating = HazardRating.fromString("Low");

        Inspection expected
                = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, rating, new ArrayList<>());
        Inspection scanned = scanner.nextInspection();
        assertEquals(expected, scanned);
    }

    @Test
    public void scanEntireFileTest() throws IOException, ParseException {
        InspectionScanner scanner = new InspectionScanner(PATH_TO_INSPECTION_CSV_FROM_SRC);
        while(scanner.hasNextLine()) {
            scanner.nextInspection();
        }
    }
}