package ca.cmpt276.project.model;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converters {
    private Converters() throws InstantiationException {
        throw new InstantiationException(getClass().getName() + " cannot be instantiated");
    }

    @TypeConverter
    public static LocalDate localDateFromString(String date) {
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
    }
    @TypeConverter
    public static String localDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }


    @TypeConverter
    public static HazardRating hazardRatingFromString(String hazardRating) {
        return HazardRating.fromString(hazardRating);
    }
    @TypeConverter
    public static String hazardRatingToString(HazardRating hazardRating) {
        return hazardRating.stringVal;
    }


    @TypeConverter
    public static InspectionType inspectionTypeFromString(String inspection) {
        return InspectionType.fromString(inspection);
    }
    @TypeConverter
    public static String inspectionTypeToString(InspectionType type) {
        return type.stringVal;
    }


    @TypeConverter
    public static ViolationCategory violationCategoryFromCodeNumber(int codeNumber) {
        return ViolationCategory.fromCodeNumber(codeNumber);
    }
    @TypeConverter
    public static ViolationCategory violationCategoryFromString(String categoryStringVal) {
        return ViolationCategory.fromString(categoryStringVal);
    }
    @TypeConverter
    public static String violationCategoryToString(ViolationCategory category) {
        return category.toString();
    }

}
