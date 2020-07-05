package ca.cmpt276.project.model;

import java.io.IOException;

public class InspectionScanner extends CsvScanner {
    public static final String PATH_TO_INSPECTION_CSV = "src/main/assets/ProjectData/inspectionreports_itr1.csv";


    ///////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public InspectionScanner(String pathToCsvData, boolean headerRow) throws IOException {
        super(pathToCsvData, headerRow);
    }

    public InspectionScanner(String pathToCsvData) throws IOException {
        this(pathToCsvData, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Methods

    public Inspection nextInspection() {
        String line = super.nextLine();
        return new Inspection("", "", 0, 0, 0, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // Helper methods


}
