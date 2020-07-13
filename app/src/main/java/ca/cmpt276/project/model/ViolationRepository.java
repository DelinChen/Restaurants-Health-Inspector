package ca.cmpt276.project.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

public class ViolationRepository {

    private ViolationDao violationDao;
    private LiveData<List<Violation>> allViolations;

    public ViolationRepository(Application application){
        ViolationDataBase database = ViolationDataBase.getInstance(application);
        violationDao = database.violationDao();
        allViolations = violationDao.getAllViolations();
    }

    public void insert(Violation violation){
        new InsertAsyncTask(violationDao).execute(violation);
    }

    public void update(Violation violation){
        new UpdateAsyncTask(violationDao).execute(violation);
    }

    public void delete(Violation violation){
        new DeleteAsyncTask(violationDao).execute(violation);
    }

    public void deleteAllViolations(){
        new DeleteAllAsyncTask(violationDao).execute();
    }

    public LiveData<List<Violation>> getAllViolations(){
        return allViolations;
    }

    public Violation getViolationById(int codeNum) {
        return allViolations.getValue().get(codeNum);
    }



    private static class InsertAsyncTask extends AsyncTask<Violation, Void, Void>{

        private ViolationDao violationDao;

        private InsertAsyncTask( ViolationDao violationDao){
            this.violationDao = violationDao;
        }

        @Override
        protected Void doInBackground(Violation... violations) {
            violationDao.insert(violations[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Violation, Void, Void>{

        private ViolationDao violationDao;

        private UpdateAsyncTask( ViolationDao violationDao){
            this.violationDao = violationDao;
        }

        @Override
        protected Void doInBackground(Violation... violations) {
            violationDao.update(violations[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Violation, Void, Void>{

        private ViolationDao violationDao;

        private DeleteAsyncTask( ViolationDao violationDao){
            this.violationDao = violationDao;
        }

        @Override
        protected Void doInBackground(Violation... violations) {
            violationDao.delete(violations[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask <Void, Void, Void>{

        private ViolationDao violationDao;

        private DeleteAllAsyncTask( ViolationDao violationDao){
            this.violationDao = violationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            violationDao.deleteAllViolations();
            return null;
        }
    }

}
