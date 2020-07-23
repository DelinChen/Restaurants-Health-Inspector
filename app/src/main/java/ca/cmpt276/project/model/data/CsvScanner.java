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

    protected String[] splitCsvLine(String line) {
        // Adapted from https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split/15905916#15905916
        String[] buffer = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for(int i = 0; i < buffer.length; i++) {
            buffer[i] = buffer[i].replace("\"", "");
            buffer[i] = buffer[i].trim();
        }
        return buffer;
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
