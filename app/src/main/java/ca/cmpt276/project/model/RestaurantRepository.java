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

public class RestaurantRepository {
    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> allRestaruants;

    public RestaurantRepository(Application application){
        RestaurantDataBase database = RestaurantDataBase.getInstance(application);
        restaurantDao = database.restaurantDao();
        allRestaruants = restaurantDao.getAllRestaurant();
    }

    public void insert(Restaurant restaurant){
        new InsertAsyncTask(restaurantDao).execute(restaurant);
    }
//
    public void update(Restaurant restaurant){
        new UpdateAsyncTask(restaurantDao).execute(restaurant);
    }
//
    public void delete(Restaurant restaurant){
        new DeleteAsyncTask(restaurantDao).execute(restaurant);
    }

    public void deleteAllViolations(){
        new DeleteAllAsyncTask(restaurantDao).execute();
    }

    public LiveData<List<Restaurant>> getAllViolations(){
        return allRestaruants;
    }

    public Restaurant getRestaurantById(int trackingNumber) {
        return allRestaruants.getValue().get(trackingNumber);
    }



    private static class InsertAsyncTask extends AsyncTask< Restaurant, Void, Void>{

        private RestaurantDao restaurantDao;

        private InsertAsyncTask( RestaurantDao restaurantDao){
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.insert(restaurants[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Restaurant, Void, Void>{

        private RestaurantDao restaurantDao;

        private UpdateAsyncTask( RestaurantDao restaurantDao){
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.update(restaurants[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Restaurant, Void, Void>{

        private RestaurantDao restaurantDao;

        private DeleteAsyncTask( RestaurantDao restaurantDao){
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.delete(restaurants[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask <Void, Void, Void>{

        private RestaurantDao restaurantDao;

        private DeleteAllAsyncTask( RestaurantDao restaurantDao){
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            restaurantDao.deleteAllRestaurants();
            return null;
        }
    }

}