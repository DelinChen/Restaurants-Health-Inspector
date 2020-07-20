package ca.cmpt276.project.model;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ca.cmpt276.project.model.InspectionScanner.InspectionCsvColumns.*;
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

    public Inspection nextInspection() {
        String line = super.nextLine();
        line = line.replace("\"", "");
        line = line.replace(",Not Repeat", "");
        String[] buffer = line.split(",", VIOLATIONS_LUMP+1);

        String trackingNumber           = buffer[TRACKING_NUMBER];
        LocalDate date                  = LocalDate.parse(buffer[DATE], BASIC_ISO_DATE);
        InspectionType type             = InspectionType.fromString(buffer[TYPE]);
        int numCritViolations           = Integer.parseInt(buffer[NUM_CRITICAL]);
        int numNonCritViolations        = Integer.parseInt(buffer[NUM_NONCRITICAL]);
        HazardRating rating             = HazardRating.fromString(buffer[HAZARD_RATING]);
        List<Violation> violations      = ViolationScanner.parseListFromLump(buffer[VIOLATIONS_LUMP]);

        Inspection nextResult = new Inspection(
                trackingNumber, date, type, numCritViolations, numNonCritViolations, rating);
        return nextResult;
    }

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


    ///////////////////////////////////////////////////////////////////////////////////
    // For clarity when parsing the csv data

    static final class InspectionCsvColumns {
        private InspectionCsvColumns() throws InstantiationException {
            // should never be instantiated
            throw new InstantiationException(getClass().getName() + "is not instantiable");
        }
        static final int TRACKING_NUMBER = 0;
        static final int DATE = 1;
        static final int TYPE = 2;
        static final int NUM_CRITICAL = 3;
        static final int NUM_NONCRITICAL = 4;
        static final int HAZARD_RATING = 5;
        static final int VIOLATIONS_LUMP = 6;
    }
}
