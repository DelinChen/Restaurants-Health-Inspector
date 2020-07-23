package ca.cmpt276.project.model.data;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ca.cmpt276.project.model.data.InspectionScanner.PATH_TO_INSPECTION_CSV_FROM_ASSETS;
import static ca.cmpt276.project.model.data.InspectionScanner.PATH_TO_INSPECTION_CSV_FROM_SRC;

// TODO: DEPRECATED
public final class InspectionManager {
    private static InspectionManager instance = null;
    private final Map<String, List<Inspection>> inspectionsMap;      // maps Tracking Number -> Inspection


    ////////////////////////////////////////////////////////////////////////////
    // Singleton pattern

    private InspectionManager(Map<String, List<Inspection>> inspectionsMap) {
        if(instance != null) {
            throw new IllegalStateException(getClass().getName() + " is a singleton with an existing instance and cannot be reinstantiated");
        }
        instance = this;

        // Make both the Map and the List values in the Map immutable
        inspectionsMap.forEach((trackingNumber, inspectionsList) -> {
            Collections.sort(inspectionsList, Collections.reverseOrder());
            inspectionsMap.put(trackingNumber, Collections.unmodifiableList(inspectionsList));
        });
        this.inspectionsMap = Collections.unmodifiableMap(inspectionsMap);
    }


    public static InspectionManager getInstance(Context anyContext) {
        if(instance == null) {
            try {
                instance = fromCsv(anyContext, PATH_TO_INSPECTION_CSV_FROM_ASSETS, true);
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }
    public static InspectionManager getInstance() {
        if(instance == null) {
            try {
                instance = fromCsv(PATH_TO_INSPECTION_CSV_FROM_SRC);
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Delegate methods

    public boolean containsTrackingNumber(String trackingNumber) {
        return inspectionsMap.containsKey(trackingNumber);
    }
    public boolean containsInspection(Inspection inspection) {
        return inspectionsMap.containsValue(inspection);
    }

    public List<Inspection> get(String trackingNumber) {
        return inspectionsMap.get(trackingNumber);
    }
    public List<Inspection> getOrDefault(String trackingNumber, List<Inspection> defaultValue) {
        return inspectionsMap.getOrDefault(trackingNumber, defaultValue);
    }

    public int size() {
        return inspectionsMap.size();
    }

    public List<List<Inspection>> inspections() {
        List<List<Inspection>> values = new ArrayList<>(inspectionsMap.values());
        return Collections.unmodifiableList(values);
    }


    ////////////////////////////////////////////////////////////////////////
    // Factory methods

    /**
     * Uses .csv data to populate the InspectionManager
     * @param pathToCsvData a String path to the data
     * @param hasHeaderRow
     * @return the populated manager
     */
    private static InspectionManager fromCsv(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        InspectionScanner scanner = new InspectionScanner(pathToCsvData, hasHeaderRow);
        Map<String, List<Inspection>> inspections = scanner.scanAllInspections();

        InspectionManager manager = new InspectionManager(inspections);
        return manager;
    }
    private static InspectionManager fromCsv(String pathToCsvData) throws IOException {
        return fromCsv(pathToCsvData, true);
    }

    private static InspectionManager fromCsv(Context anyContext, String pathToCsvData, boolean hasHeaderRow) throws IOException {
        InputStream dataStream = anyContext.getApplicationContext().getAssets().open(pathToCsvData);
        InspectionScanner inspectionScanner = new InspectionScanner(dataStream, hasHeaderRow);
        Map<String, List<Inspection>> inspections = inspectionScanner.scanAllInspections();

        InspectionManager manager = new InspectionManager(inspections);
        return manager;
    }


}
