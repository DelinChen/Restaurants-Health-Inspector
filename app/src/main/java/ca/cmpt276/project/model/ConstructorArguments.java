package ca.cmpt276.project.model;

import java.util.Objects;

public final class ConstructorArguments {
    private ConstructorArguments() throws InstantiationException {
        // not instantiable
        throw new InstantiationException(getClass().getName() + " is not instantiable");
    }


    public static <T> void requireArgsNonNull(String[] argNames, Object[] argValues, Class<T> classRef) {
        if(argNames.length != argValues.length) {
            throw new IllegalArgumentException("argNames.length must be equal to argValues.length");
        }

        for(int i = 0; i < argValues.length; i++) {
            Objects.requireNonNull(argValues[i], classRef.getName() + "." + argNames[i] + " cannot be null");
        }
    }

    public static <T> void requireStringArgsNonEmpty(String[] stringArgNames, String[] stringArgValues, Class<T> classRef) {
        if(stringArgNames.length != stringArgValues.length) {
            throw new IllegalArgumentException("stringArgNames.length must be equal to stringArgValues.length");
        }

        for(int i = 0; i < stringArgValues.length; i++) {
            if(stringArgValues[i].length() == 0) {
                throw new IllegalArgumentException(classRef.getName() + "." + stringArgNames[i] + " cannot be the empty string");
            }
        }
    }

    public static <T> void requrieNonNegativeIntArgs(String[] intArgNames, int[] intArgValues, Class<T> classRef) {
        if(intArgNames.length != intArgValues.length) {
            throw new IllegalArgumentException("stringArgNames.length must be equal to stringArgValues.length");
        }

        for(int i = 0; i < intArgValues.length; i++) {
            if(intArgValues[i] < 0) {
                throw new IllegalArgumentException(classRef.getName() + "." + intArgNames[i] + "must be non-negative");
            }
        }
    }
}
