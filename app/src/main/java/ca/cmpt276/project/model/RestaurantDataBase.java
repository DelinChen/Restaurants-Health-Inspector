package ca.cmpt276.project.model;

import android.app.AsyncNotedAppOp;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


@Database( entities = Restaurant.class, version = 1)
public abstract class RestaurantDataBase extends RoomDatabase  {
    private static RestaurantDataBase instance;

    public abstract RestaurantDao restaurantDao();

    public static synchronized RestaurantDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), RestaurantDataBase.class, "restaurantDataBase")
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

    private static class PopulateDbAysncTask extends AsyncTask<Void, Void, Void>{
        private RestaurantDao restaurantDao;

        private PopulateDbAysncTask(RestaurantDataBase db){
            restaurantDao = db.restaurantDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                File restaurants = new File("src/main/assets/ProjectData/restaurants.csv");

                Scanner scanner = new Scanner(restaurants);

                String nextLine = scanner.nextLine();

                String[] components = nextLine.split(",");

                String trackNum = components[0];
                String name = components[1];
                String address = components[2];
                String city = components[3];
                String latitude = components[4];
                String longitude = components[5];

                double latitudes = Double.parseDouble(latitude);
                double longitudes = Double.parseDouble(longitude);

                restaurantDao.insert(new Restaurant(trackNum, name, address, city, latitudes, longitudes));


            } catch (FileNotFoundException e) {
                Logger.getLogger(RestaurantDataBase.class.getName()).log(Level.SEVERE, null, e);
            }

            return null;
        }
    }


}

