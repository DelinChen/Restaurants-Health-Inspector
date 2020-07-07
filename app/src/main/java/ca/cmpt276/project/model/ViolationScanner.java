package ca.cmpt276.project.model;

import java.util.Scanner;

public class ViolationScanner {
    private final Scanner lineScanner;

    public ViolationScanner(String lumpLine) {
        this.lineScanner = new Scanner(lumpLine);
    }


}
