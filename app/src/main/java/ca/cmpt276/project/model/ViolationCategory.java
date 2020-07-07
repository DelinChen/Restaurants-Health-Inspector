package ca.cmpt276.project.model;

import androidx.annotation.NonNull;

public enum ViolationCategory {
    FOOD("FOOD"),
    PEST("PEST"),
    EQUIPMENT("EQUIPMENT"),
    NULL_CATEGORY("");

    public final String stringVal;

    ViolationCategory(String stringVal){
        this.stringVal = stringVal;
    }

    @NonNull
    @Override
    public String toString() {
        return this.stringVal;
    }

    public static ViolationCategory parse(String input){
        for( ViolationCategory category : ViolationCategory.values()){
            if(category.stringVal.equals(input)){
                return category;
            }
        }
        return NULL_CATEGORY;
    }

}
