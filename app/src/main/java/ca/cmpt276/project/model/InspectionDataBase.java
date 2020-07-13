package ca.cmpt276.project.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Database( entities = Inspection.class, version = 1)
public abstract class InspectionDataBase extends RoomDatabase{

    public static InspectionDataBase instance;

    public abstract InspectionDao inspectionDao();

    public static synchronized  InspectionDataBase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), InspectionDataBase.class, "InspectionDataBase")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAysncTask(instance).execute();
        }
    };

    private static class PopulateDbAysncTask extends AsyncTask<Void, Void, Void> {
        private InspectionDao inspectionDao;

        private PopulateDbAysncTask(InspectionDataBase db){
            inspectionDao = db.inspectionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                File inspections = new File("src/main/assets/ProjectData/restaurants.csv");

                Scanner scanner = new Scanner(inspections);

                String nextLine = scanner.nextLine();

                String[] components = nextLine.split(",");

                String trackNum = components[0];
                String date = components[1];
                String type = components[2];
                String critVio = components[3];
                String critNonVio = components[4];
                String vioLump = components[5];
                String hazardRating = components[6];

                LocalDate localDate =LocalDate.parse(date);
                int critVioNum = Integer.parseInt(critVio);
                int critNonVioNum = Integer.parseInt(critNonVio);
                int vioNum = Integer.parseInt(vioLump);
                InspectionType inspectiontype = new InspectionType(type);
                HazardRating hazard = new HazardRating(hazardRating);
                //need to add values inside this List
                List<Violation> violations = new ArrayList<>();

                inspectionDao.insert(new Inspection(trackNum, localDate, inspectiontype, critVioNum,
                        critNonVioNum, hazard, violations ));

            } catch (FileNotFoundException e) {
                Logger.getLogger(RestaurantDataBase.class.getName()).log(Level.SEVERE, null, e);
            }

            return null;
        }
    }

}

