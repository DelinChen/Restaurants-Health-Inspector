package ca.cmpt276.project.model.data;


import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class InspectionScanner extends CsvScanner {
    public static final String PATH_TO_INSPECTION_CSV_FROM_SRC = "src/main/assets/ProjectData/inspectionreports_itr1.csv";
    public static final String PATH_TO_INSPECTION_CSV_FROM_ASSETS = "ProjectData/inspectionreports_itr1.csv";


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

    // TODO: DEPRECATED!
    public Inspection nextInspection() {
        String line = super.nextLine();
        String[] buffer = splitCsvLine(line);

        String trackingNumber           = buffer[Iter1Columns.TRACKING_NUMBER];
        LocalDate date                  = LocalDate.parse(buffer[Iter1Columns.DATE], BASIC_ISO_DATE);
        InspectionType type             = InspectionType.fromString(buffer[Iter1Columns.TYPE]);
        int numCritViolations           = Integer.parseInt(buffer[Iter1Columns.NUM_CRITICAL]);
        int numNonCritViolations        = Integer.parseInt(buffer[Iter1Columns.NUM_NONCRITICAL]);
        HazardRating rating             = HazardRating.fromString(buffer[Iter1Columns.HAZARD_RATING]);
        List<Violation> violations      = ViolationScanner.parseListFromLump(buffer[Iter1Columns.VIOLATIONS_LUMP]);

        Inspection nextResult = new Inspection(
                trackingNumber, date, type, numCritViolations, numNonCritViolations, rating);
        return nextResult;
    }

    public InspectionDetails nextInspectionDetails() {
        String line = super.nextLine();
        if(line.equals(",,,,,,")) {
            return null;
        }
        String[] buffer = splitCsvLine(line);
        if(buffer.length != 7) {
            return null;
        }

        String trackingNumber           = buffer[Iter2Columns.TRACKING_NUMBER];
        LocalDate date                  = LocalDate.parse(buffer[Iter2Columns.DATE], BASIC_ISO_DATE);
        InspectionType type             = InspectionType.fromString(buffer[Iter2Columns.TYPE]);
        int numCritViolations           = Integer.parseInt(buffer[Iter2Columns.NUM_CRITICAL]);
        int numNonCritViolations        = Integer.parseInt(buffer[Iter2Columns.NUM_NONCRITICAL]);
        HazardRating rating             = HazardRating.fromString(buffer[Iter2Columns.HAZARD_RATING]);

        List<Violation> violations      = ViolationScanner.parseListFromLump(buffer[Iter2Columns.VIOLATIONS_LUMP]);
        Inspection inspection
                = new Inspection(trackingNumber, date, type, numCritViolations, numNonCritViolations, rating);

        InspectionDetails nextResult = new InspectionDetails(inspection, violations);
        return nextResult;
    }

    @Override
    protected String[] splitCsvLine(String line) {
        line = line.replace(",Not Repeat", "");
        String[] buffer = super.splitCsvLine(line);
        return buffer;
    }

    // TODO: DEPRECATED!
    public Map<String, List<Inspection>> scanAllInspections() {
        Map<String, List<Inspection>> inspectionsMap = new HashMap<>();
        while(hasNextLine()) {
            Inspection nextResult = nextInspection();
            if(inspectionsMap.containsKey(nextResult.trackingNumber)) {
                List<Inspection> inspectionsList = inspectionsMap.get(nextResult.trackingNumber);
                assert(inspectionsList != null);
                inspectionsList.add(nextResult);
            }
            else {
                List<Inspection> inspectionsList = new ArrayList<>();
                inspectionsList.add(nextResult);
                inspectionsMap.put(nextResult.trackingNumber, inspectionsList);
            }
        }
        close();
        return inspectionsMap;
    }

    public Map<String, List<InspectionDetails>> scanAllInspectionDetails() {
        Map<String, List<InspectionDetails>> inspectionDetailsMap = new HashMap<>();
        while(hasNextLine()) {
            InspectionDetails nextResult = nextInspectionDetails();
            if(nextResult == null) {
                continue;
            }

            if(inspectionDetailsMap.containsKey(nextResult.inspection.trackingNumber)) {
                List<InspectionDetails> inspectionDetailsList = inspectionDetailsMap.get(nextResult.inspection.trackingNumber);
                assert(inspectionDetailsList != null);
                inspectionDetailsList.add(nextResult);
            }
            else {
                List<InspectionDetails> inspectionDetailsList = new ArrayList<>();
                inspectionDetailsList.add(nextResult);
                inspectionDetailsMap.put(nextResult.inspection.trackingNumber, inspectionDetailsList);
            }
        }
        close();
        return inspectionDetailsMap;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // For clarity when parsing the csv data

    static final class Iter1Columns {
        private Iter1Columns() throws InstantiationException {
            // should never be instantiated
            throw new InstantiationException(getClass().getName() + " is not instantiable");
        }
        static final int TRACKING_NUMBER = 0;
        static final int DATE = 1;
        static final int TYPE = 2;
        static final int NUM_CRITICAL = 3;
        static final int NUM_NONCRITICAL = 4;
        static final int HAZARD_RATING = 5;
        static final int VIOLATIONS_LUMP = 6;
    }

    static final class Iter2Columns {
        private Iter2Columns() throws InstantiationException {
            throw new InstantiationException(getClass().getName() + " is not instantiable");
        }

        static final int TRACKING_NUMBER = 0;
        static final int DATE = 1;
        static final int TYPE = 2;
        static final int NUM_CRITICAL = 3;
        static final int NUM_NONCRITICAL = 4;
        static final int VIOLATIONS_LUMP = 5;
        static final int HAZARD_RATING = 6;
    }
}
