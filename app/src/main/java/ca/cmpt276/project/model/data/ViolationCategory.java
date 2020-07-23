package ca.cmpt276.project.model.data;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum ViolationCategory {
    LOGISTICS("Logistics",
            101, 102, 103, 104, 311, 312, 313, 314),
    FOOD("Food",
            201, 202, 203, 204, 205, 206, 208, 209, 210, 211),
    EQUIPMENT("Equipment",
            307, 308, 309, 310, 315),
    PEST("Pest",
            304, 305),
    HYGIENE("Hygiene",
            301, 302, 303, 306, 401, 402, 403, 404),
    TRAINING("Training",
            212, 501, 502),
    NULL_CATEGORY("");


    private static final Map<Integer, ViolationCategory> codeNumberToCategoryMap = populateNumberCodeToCategoryMap();


    ///////////////////////////////////////////////////////////
    // Fields

    public final String         stringVal;
    public final Set<Integer>   codeNumberSet;


    ///////////////////////////////////////////////////////////
    // Constructor

    ViolationCategory(String stringVal, int ...codeNumbers) {
        this.stringVal = stringVal;
        Set<Integer> modifiableSet = Arrays.stream(codeNumbers)
                                            .boxed()
                                            .collect(Collectors.toSet());
        codeNumberSet = Collections.unmodifiableSet(modifiableSet);
    }

    @NonNull
    @Override
    public String toString() {
        return this.stringVal;
    }


    ///////////////////////////////////////////////////////////
    // Static methods

    private static Map<Integer, ViolationCategory> populateNumberCodeToCategoryMap() {
        Map<Integer, ViolationCategory> categoryMap = new HashMap<>();
        for(ViolationCategory category : values()) {
            for(Integer codeNumber : category.codeNumberSet) {
                categoryMap.put(codeNumber, category);
            }
        }
        return Collections.unmodifiableMap(categoryMap);
    }



    ///////////////////////////////////////////////////////////
    // Factory methods

    public static ViolationCategory fromString(String input){
        for( ViolationCategory category : ViolationCategory.values()){
            if(category.stringVal.equals(input)){
                return category;
            }
        }
        return NULL_CATEGORY;
    }

    public static ViolationCategory fromCodeNumber(int codeNumber) {
        return codeNumberToCategoryMap.getOrDefault(codeNumber, NULL_CATEGORY);
    }
}
