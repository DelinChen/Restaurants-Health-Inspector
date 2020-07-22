package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.RestaurantManager;


public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RestaurantManager manager;
    RecyclerView restList;
    TextView textView;

    private static List<String[]> restaurantScannedCSV;
    private static List<String[]> inspectionsScannedCSV;

    public static String REST_DL_URL;
    public static String INSP_DL_URL;

    private static LocalDateTime REST_LAST_MODIFIED;
    private static LocalDateTime REST_LAST_UPDATED;
    private static LocalDateTime INSP_LAST_MODIFIED;
    private static LocalDateTime INSP_LAST_UPDATED;

    private static LocalDateTime LoginDate;
    private static Duration twentyHours = Duration.ofHours(20);

    /* sharedPreference*/
    private static final String REST_LAST_MODIFIED_DATE = "rest_last_modified_date";
    private static final String INSP_LAST_MODIFIED_DATE = "insp_last_modified_date";
    private static final String SHARED_PREFS = "sharedPrefs";
    /* sharedPreference*/
    private static String restaurantlastModifiedDate;
    private static String inspectionslastModifiedDate;

    /* URL */
    private static final String REST_API_URL = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";
    private static final String INSP_API_URL = "https://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";
    /* URL */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Restaurant Health Inspector");

        textView = findViewById(R.id.textView4);

        if(restaurantlastModifiedDate == null){
            restaurantlastModifiedDate = "2020-07-01T00:00";
        }
        if(inspectionslastModifiedDate == null){
            inspectionslastModifiedDate = "2020-07-01T00:00";
        }


        REST_LAST_UPDATED = LocalDateTime.parse(restaurantlastModifiedDate);
        INSP_LAST_UPDATED = LocalDateTime.parse(inspectionslastModifiedDate);
        LoginDate = LocalDateTime.now();
        twentyHours = Duration.ofHours(20);
        Duration restBetweenUpdates = Duration.between(REST_LAST_UPDATED,LoginDate);
        Duration inspBetweenUpdates = Duration.between(INSP_LAST_UPDATED,LoginDate);
//        textView.setText(inspBetweenUpdates.toString());


        updateUI();

        if(restBetweenUpdates.compareTo(twentyHours) >= 0) {
            new RESTJSONTask().execute(REST_API_URL);
//        textView.setText(REST_DL_URL);

            new RSCANTask().execute(REST_DL_URL);
//        textView.setText(scannedCSV.toString()); // <-used for testing anything wrong

            //Set the latest date as the Lastest modified date
//            REST_LAST_UPDATED = REST_LAST_MODIFIED; //<-the later one is null object
        }

        //Same method to extract data from API, only difference is the url
        if(inspBetweenUpdates.compareTo(twentyHours) >= 0) {
            new INSPJSONTask().execute(INSP_API_URL);
//            textView.setText(INSP_API_URL);

//
//            new ISCANTask().execute(INSP_DL_URL);
////            textView.setText(scannedCSV.toString()); // <-used for testing anything wrong
        }

    }

    private void updateUI(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        manager = RestaurantManager.getInstance(getApplicationContext());
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, manager, this);
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));

        String restaurantDate = sharedPreferences.getString(REST_LAST_MODIFIED_DATE,restaurantlastModifiedDate);
        String inspectionsDate = sharedPreferences.getString(INSP_LAST_MODIFIED_DATE, inspectionslastModifiedDate);

    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("tracking number", manager.restaurants().get(position).trackingNumber);
        startActivity(intent);
    }

    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // switch to map view
            case R.id.map:
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class RESTJSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            BufferedReader reader;
            URL url = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                String finalJson = stringBuffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject childObject = parentObject.getJSONObject("result");
                JSONArray parentArray = childObject.getJSONArray("resources");
                JSONObject targetObject = parentArray.getJSONObject(0);

                REST_DL_URL = targetObject.getString("url");
                REST_LAST_MODIFIED = LocalDateTime.parse(targetObject.getString("last_modified"));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (REST_LAST_UPDATED.isBefore(REST_LAST_MODIFIED)) {
                restDownloadFile(REST_DL_URL);

            }
        }
    }

        private void restDownloadFile(String url) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setTitle("restaurant");
            request.setDescription("Donwloading file....");

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis());

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }

    //The List<strin[]> is null after running this
    public class RSCANTask extends AsyncTask<String, String, String> {
        HttpURLConnection connection1;
        BufferedReader reader1;
        URL url1 = null;
        String[] content1 = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                url1 = new URL(params[0]);
                connection1 = (HttpURLConnection) url1.openConnection();
                connection1.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    content1 = line.split(",");
                    restaurantScannedCSV.add(content1);
                }
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            /*need to update the List<String[]> to the database*/
//            textView.setText(restaurantScannedCSV.toString()); //<--not sure why the restaurantScannerCSV is null
            //update the new update date to that day
            //

        }

    }

    private class INSPJSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            BufferedReader reader;
            URL url = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                String finalJson = stringBuffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject childObject = parentObject.getJSONObject("result");
                JSONArray parentArray = childObject.getJSONArray("resources");
                JSONObject targetObject = parentArray.getJSONObject(0);

                INSP_DL_URL = targetObject.getString("url");
                INSP_LAST_MODIFIED = LocalDateTime.parse(targetObject.getString("last_modified"));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (INSP_LAST_UPDATED.isBefore(INSP_LAST_MODIFIED)) {
                inspectDownloadFile(INSP_DL_URL);
//                textView.setText(INSP_DL_URL);
            }
        }
    }

    private void inspectDownloadFile(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("inspections");
        request.setDescription("Donwloading file....");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis());

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    private class ISCANTask extends AsyncTask<String, String, String> {
        HttpURLConnection connection;
        BufferedReader reader;
        URL url = null;
        String[] content = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    content = line.split(",");
                    inspectionsScannedCSV.add(content);
                }
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            /*need to update the List<String[]> to the database*/
//            textView.setText(restaurantScannedCSV.toString()); //<--not sure why the restaurantScannerCSV is null
            //update the new update date to that day
            //

        }

    }
}


