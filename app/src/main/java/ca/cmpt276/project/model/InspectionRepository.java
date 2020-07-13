package ca.cmpt276.project.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InspectionRepository {
        private InspectionDao inspectionDao;
        private LiveData<List<Inspection>> allInspections;

        public InspectionRepository(Application application){
            InspectionDataBase database = InspectionDataBase.getInstance(application);
            inspectionDao = database.inspectionDao();
            allInspections = inspectionDao.getALLInspections();
        }

        public void insert(Inspection inspection){
            new InspectionRepository.InsertAsyncTask(inspectionDao).execute(inspection);
        }

        public void update(Inspection inspection){
            new InspectionRepository.UpdateAsyncTask(inspectionDao).execute(inspection);
        }

        public void delete(Inspection inspection){
            new InspectionRepository.DeleteAsyncTask(inspectionDao).execute(inspection);
        }

        public void deleteAllInspections(){
            new InspectionRepository.DeleteAllAsyncTask(inspectionDao).execute();
        }

        public LiveData<List<Inspection>> getAllViolations(){
            return allInspections;
        }

        public Inspection getInspectionById(int codeNum) {
            return allInspections.getValue().get(codeNum);
        }

        private static class InsertAsyncTask extends AsyncTask<Inspection, Void, Void> {

            private InspectionDao inspectionDao;

            private InsertAsyncTask( InspectionDao inspectionDao){
                this.inspectionDao = inspectionDao;
            }

            @Override
            protected Void doInBackground(Inspection... inspections) {
                inspectionDao.insert(inspections[0]);
                return null;
            }
        }

        private static class UpdateAsyncTask extends AsyncTask<Inspection, Void, Void>{

            private InspectionDao inspectionDao;

            private UpdateAsyncTask( InspectionDao inspectionDao){
                this.inspectionDao = inspectionDao;
            }

            @Override
            protected Void doInBackground(Inspection... inspections) {
                inspectionDao.update(inspections[0]);
                return null;
            }
        }

        private static class DeleteAsyncTask extends AsyncTask<Inspection, Void, Void>{

            private InspectionDao inspectionDao;

            private DeleteAsyncTask( InspectionDao inspectionDao){
                this.inspectionDao = inspectionDao;
            }

            @Override
            protected Void doInBackground(Inspection... inspections) {
                inspectionDao.delete(inspections[0]);
                return null;
            }
        }

        private static class DeleteAllAsyncTask extends AsyncTask <Void, Void, Void>{

            private InspectionDao inspectionDao;

            private DeleteAllAsyncTask( InspectionDao inspectionDao){
                this.inspectionDao = inspectionDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                inspectionDao.deleteAllInspections();
                return null;
            }
        }

}
