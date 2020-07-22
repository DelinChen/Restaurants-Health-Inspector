package ca.cmpt276.project.model.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ca.cmpt276.project.model.data.ViolationScanner.ViolationCsvColumns.*;


public class ViolationScanner {
    private ViolationScanner() throws InstantiationException {
        // should never be instantiated
        throw new InstantiationException(getClass().getName() + "is not instantiable");
    }


    public static Violation nextViolation(String csvField) {
        String[] buffer = csvField.split(",");

        int codeNumber              = Integer.parseInt(buffer[CODE_NUMBER]);
        boolean isCritical          = buffer[IS_CRITICAL].equals("Critical");
        ViolationCategory category  = ViolationCategory.fromCodeNumber(codeNumber);
        String description          = buffer[DESCRIPTION];

        Violation nextResult = new Violation(codeNumber, isCritical, category, description);
        return nextResult;
    }

    public static List<Violation> parseListFromLump(String csvViolationsLump) {
        if(csvViolationsLump.equals("")) {
            // no violations, return empty list
            return new ArrayList<>();
        }

        // else process the lump
        csvViolationsLump = csvViolationsLump.replace("\"", "");
        String[] violationsBuffer = csvViolationsLump.split("\\|");
        return Arrays.stream(violationsBuffer)
                .map(ViolationScanner::nextViolation)
                .collect(Collectors.toList());
    }

     static class ViolationCsvColumns {
        private ViolationCsvColumns() throws InstantiationException {
            // should never be instantiated
            throw new InstantiationException(getClass().getName() + " is not instantiable");
        }

        public static final int CODE_NUMBER = 0;
        public static final int IS_CRITICAL = 1;
        public static final int DESCRIPTION = 2;
     }
}
