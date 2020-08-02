package ca.cmpt276.project.model.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (
        tableName = "favourites"
)
public class Favourites {

    @PrimaryKey
    @ColumnInfo(name = "tracking_number", index = true)
    @NonNull public final String trackingNumber;     // unique ID

    @ColumnInfo(name = "is_favourite")
    public final boolean isFavourite;

    /////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    public Favourites(String trackingNumber, boolean isFavourite) {
        this.trackingNumber = trackingNumber;
        this.isFavourite = isFavourite;
    }

    @NonNull
    @Override
    public String toString() {
        return "<" + trackingNumber + ", " + isFavourite + ">";
    }

}


