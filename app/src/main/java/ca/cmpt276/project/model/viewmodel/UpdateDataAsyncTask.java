package ca.cmpt276.project.model.viewmodel;

import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.database.RestaurantDao;

public class UpdateDataAsyncTask extends AsyncTask<Object, Boolean, List<RestaurantDetails>> {
    @Override
    protected List<RestaurantDetails> doInBackground(Object ...params) {
        if(android.os.Debug.isDebuggerConnected()) {
            android.os.Debug.waitForDebugger();
        }
        if(!(params[0] instanceof Map) || !(params[1] instanceof Map) || !(params[2] instanceof RestaurantDao)) {
            throw new AssertionError("Params of incorrect type");
        }

        Map<String, Restaurant> restaurantMap = (Map<String, Restaurant>) params[0];
        Map<String, List<InspectionDetails>> inspectionDetailsMap = (Map<String, List<InspectionDetails>>) params[1];

        List<RestaurantDetails> restaurantDetailsList = inspectionDetailsMap.entrySet()
               .parallelStream()
               .filter(entry -> restaurantMap.get(entry.getKey()) != null)
               .map(entry -> new RestaurantDetails(restaurantMap.get(entry.getKey()), entry.getValue()))
               .collect(Collectors.toList());

        RestaurantDao restaurantDao = (RestaurantDao) params[2];

        assert restaurantDetailsList != null;
        restaurantDao.insertAllRestaurantDetails(restaurantDetailsList);
        return restaurantDetailsList;
    }


    AsyncTask<String, String, Map<String, Restaurant>> parseRestaurantDownloadUrl(String downloadUrl) {
        return new ParseRestaurantDownloadUrlAsyncTask().execute(downloadUrl);
    }

     AsyncTask<String, String, Map<String, List<InspectionDetails>>> parseInspectionDownloadUrl(String downloadUrl) {
        return new ParseInspectionDownloadUrlAsyncTask().execute(downloadUrl);
    }
}
