package ca.cmpt276.project.model.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class CsvScanner {
    private Scanner lineScanner;

    public CsvScanner(InputStream input, boolean hasHeaderRow) {
        lineScanner = new Scanner(input);
        discardHeaderRow(hasHeaderRow);
    }
    public CsvScanner(InputStream input) {
        this(input, true);
    }

    public CsvScanner(String pathToCsvData, boolean hasHeaderRow) throws IOException {
        File csvData = new File(pathToCsvData);
        validateDataFile(csvData);

        lineScanner = new Scanner(csvData);
        discardHeaderRow(hasHeaderRow);
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

    public void close() {
        lineScanner.close();
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

    private void discardHeaderRow(boolean hasHeaderRow) {
        if(hasNextLine() && hasHeaderRow) {
            nextLine();
        }
    }
}
