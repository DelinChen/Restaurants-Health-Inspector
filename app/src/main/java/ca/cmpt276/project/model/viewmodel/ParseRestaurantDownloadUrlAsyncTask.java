package ca.cmpt276.project.model.viewmodel;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantScanner;

public class ParseRestaurantDownloadUrlAsyncTask extends AsyncTask<String, String, List<Restaurant>> {
    @Override
    protected List<Restaurant> doInBackground(String ...params) {
        if(android.os.Debug.isDebuggerConnected()) {
            android.os.Debug.waitForDebugger();
        }

        List<Restaurant> responseBody = null;
        try {
            URL downloadUrl = new URL(params[0]);
            RestaurantScanner scanner = new RestaurantScanner(downloadUrl.openStream());
            responseBody = scanner.scanAllRestaurantsAsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
