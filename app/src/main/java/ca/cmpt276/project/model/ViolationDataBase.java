//package ca.cmpt276.project.model;
//
//
//import android.app.AsyncNotedAppOp;
//import android.content.Context;
//import android.os.AsyncTask;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Database( entities = Violation.class, version = 1)
//public abstract class ViolationDataBase extends RoomDatabase {
//    private static ViolationDataBase instance;
//
//    public abstract ViolationDao violationDao();
//
//    public static synchronized ViolationDataBase getInstance(Context context){
//        if(instance == null){
//            instance = Room.databaseBuilder(context.getApplicationContext(), ViolationDataBase.class, "violationDataBase")
//                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
//        }
//        return instance;
//    }
//
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAysncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAysncTask extends AsyncTask<Void, Void, Void>{
//        private ViolationDao violationDao;
//
//        private PopulateDbAysncTask(ViolationDataBase db){
//            violationDao = db.violationDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
////            try {
//////                File violations = new File("app/src/main/assets/ProjectData/AllViolations.txt");
//////
//////                Scanner scanner = new Scanner(violations);
//////
//////                String nextLine = scanner.nextLine();
//////
//////                String[] components = nextLine.split(",");
//////
//////                String codeNum = components[0];
//////                String isCritical = components[1];
//////                String description = components[2];
//////
//////                int codeNums = Integer.parseInt(codeNum);
//////                boolean isCriticals = Boolean.parseBoolean(isCritical);
//////                violationDao.insert(new Violation(codeNums, isCriticals, category, description));
////
////
////            } catch (FileNotFoundException e) {
////                Logger.getLogger(ViolationDataBase.class.getName()).log(Level.SEVERE, null, e);
////            }
//
//
//
//            return null;
//        }
//    }
//
//
//}
