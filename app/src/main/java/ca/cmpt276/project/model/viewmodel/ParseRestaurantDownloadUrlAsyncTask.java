package ca.cmpt276.project.model.viewmodel;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantScanner;

public class ParseRestaurantDownloadUrlAsyncTask extends AsyncTask<String, String, Map<String, Restaurant>> {
    @Override
    protected Map<String, Restaurant> doInBackground(String ...params) {
        Map<String, Restaurant> responseBody = null;
        try {
            URL downloadUrl = new URL(params[0]);
            RestaurantScanner scanner = new RestaurantScanner(downloadUrl.openStream());
            responseBody = scanner.scanAllRestaurants();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

}
