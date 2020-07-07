package ca.cmpt276.project.model;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ca.cmpt276.project.model.InspectionScanner.InspectionCsvColumns.*;
import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class InspectionScanner extends CsvScanner {
    public static final String PATH_TO_INSPECTION_CSV = "src/main/assets/ProjectData/inspectionreports_itr1.csv";


    ///////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public InspectionScanner(InputStream input, boolean hasHeaderRow) {
        super(input, hasHeaderRow);
    }

    public InspectionScanner(InputStream input) {
        super(input);
    }

    public InspectionScanner(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        super(pathToCsvData, hasHeaderRow);
    }

    public InspectionScanner(String pathToCsvData) throws IOException {
        this(pathToCsvData, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Methods

    public Inspection nextInspection() throws ParseException {
        String line = super.nextLine();
        line = line.replace("\"", "");
        line = line.replace("Not Repeat", "");
        String[] buffer = line.split(",", VIOLATIONS_LUMP);

        String trackingNumber           = buffer[TRACKING_NUMBER];
        LocalDate date                  = LocalDate.parse(buffer[DATE], BASIC_ISO_DATE);
        InspectionType type             = InspectionType.parse(buffer[TYPE]);
        int numCritViolations           = Integer.parseInt(buffer[NUM_CRITICAL]);
        int numNonCritViolations        = Integer.parseInt(buffer[NUM_NONCRITICAL]);
        HazardRating rating             = HazardRating.parse(buffer[HAZARD_RATING]);

        // ViolationScanner lumpScanner    = new ViolationScanner(buffer[VIOLATIONS_LUMP]);
        // List<Violation> violations      = lumpScanner.nextListOfViolations();

        Inspection nextResult = new Inspection(
                trackingNumber, date, type, numCritViolations, numNonCritViolations, rating, new ArrayList<>());
        return nextResult;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Helper methods

    ///////////////////////////////////////////////////////////////////////////////////
    // For clarity when parsing the csv data

    static final class InspectionCsvColumns {
        static final int TRACKING_NUMBER = 0;
        static final int DATE = 1;
        static final int TYPE = 2;
        static final int NUM_CRITICAL = 3;
        static final int NUM_NONCRITICAL = 4;
        static final int HAZARD_RATING = 5;
        static final int VIOLATIONS_LUMP = 6;
    }
}
