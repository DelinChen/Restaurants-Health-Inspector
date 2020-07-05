package ca.cmpt276.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public abstract class CsvScanner {
    private Scanner lineScanner;

    public CsvScanner(String pathToCsvData, boolean headerRow) throws IOException {
        File csvData = new File(pathToCsvData);
        validateDataFile(csvData);

        lineScanner = new Scanner(csvData);
        if(hasNextLine() && headerRow) {
            // discard the header row
            lineScanner.nextLine();
        }
    }

    public CsvScanner(String pathToCsvData) throws IOException {
        this(pathToCsvData, true);
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean hasNextLine() {
        return lineScanner.hasNextLine();
    }

    protected String nextLine() {
        return lineScanner.nextLine();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Helper methods

    protected static void validateDataFile(File csvData) throws IOException, IllegalArgumentException {
        if (!csvData.exists()) {
            throw new FileNotFoundException(csvData.getPath() + " doesn't exist");
        }
        if (!csvData.isFile()) {
            throw new IllegalArgumentException(csvData.getPath() + " is not a file");
        }
        if (!csvData.canRead()) {
            throw new IOException("Don't have permission to read file " + csvData.getPath());
        }
    }
}
