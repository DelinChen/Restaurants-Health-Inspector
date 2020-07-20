package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.MalformedURLException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.time.Duration;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.Restaurant;
import ca.cmpt276.project.model.RestaurantManager;



public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RestaurantManager manager;
    RecyclerView restList;

    private Intent serviceIntent;
    private boolean mStopLoop;
    private static String restaurantDLUrl;
    private static String inspectionDLUrl;
    SimpleDateFormat inDaysFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static LocalDateTime RestaurantslastUpdateDate = LocalDateTime.of(2020,06,30,23,59); //the last date we did update from server, assume we updated it at that date
    private static LocalDateTime InspectionslastUpdateDate = LocalDateTime.of(2020,06,30,23,59);//the last date we did update from server, assume we updated it at that date
    private static LocalDateTime RestaurantslastModifiedDate = LocalDateTime.of(2020,06,30,23,59); //the last date we did update from server, assume we updated it at that date
    private static LocalDateTime InspectionslastModifiedDate = LocalDateTime.of(2020,06,30,23,59);//the last date we did update from server, assume we updated it at that date
    private static LocalDateTime LoginDate = LocalDateTime.now();

    TextView UrlText;
    TextView DateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Restaurant Health Inspector");

        UrlText = findViewById(R.id.txtUrl);
        DateText = findViewById(R.id.txtLatestUpdateDate);

        //for checking update timer

        manager = RestaurantManager.getInstance(getApplicationContext());
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, manager, this);
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));

        Duration betweenUpdates = Duration.between(RestaurantslastUpdateDate,LoginDate);
        //Update is define by more than Duration of 20 hours
        Duration twentyHours = Duration.ofHours(20);
        //check if the Duration bewteen two are more than or equal to 20h hours
//                    UrlText.setText(restaurantDLUrl);
//                    DateText.setText(RestaurantslastUpdateDate.toString());

        /*Request the server to check the latest update since last 20hours from server*/
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        if(betweenUpdates.compareTo(twentyHours) >= 0) {
            //the Restaurant JSON file request
            JsonObjectRequest RestaurantObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "http://data.surrey.ca/api/3/action/package_show?id=restaurants",
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Access the "result" field of JSON in the API request
                        JSONObject jsonObject = response.getJSONObject("result");
                        //Access the "resource " array in the "result field
                        JSONArray resource = jsonObject.getJSONArray("resources");
                        //Access the first JSonObject in the "resources" array
                        JSONObject restaurantResources = resource.getJSONObject(0);
                        //Get the Url to ready to download
                        restaurantDLUrl = restaurantResources.getString("url");
                        //Get the last modified date in String form
                        String updateDate = restaurantResources.getString("last_modified");
                        //THE LATEST MODIFIED DATE FROM API
                        LocalDateTime latestModeifiedDate = LocalDateTime.parse(updateDate);
                        //the Duration between the the

                        if (RestaurantslastUpdateDate.isBefore(latestModeifiedDate)) {
                            //replace the latest update date to the new one
                            RestaurantslastModifiedDate = latestModeifiedDate;
                            //Ask for Update and download
//                            Dialog creation

                            //setup downloadManager
                            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            //setup the Request
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(restaurantDLUrl));
                            //queue up for download
//                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                            request.setTitle("Download"); //set title in download notifcation;

                            downloadManager.enqueue(request);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            //add the request to the queue
            requestQueue.add(RestaurantObjectRequest);
        }


        Duration betweenUpdates2 = Duration.between(InspectionslastUpdateDate,LoginDate);
        if(betweenUpdates2.compareTo(twentyHours) >= 0) {
            /*Request the server to check the latest update since last 20hours from server*/
            //the Inpsection JSON file request
            JsonObjectRequest InspectionsObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "http://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports",
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Access the "result" field of JSON in the API request
                        JSONObject jsonObject = response.getJSONObject("result");
                        //Access the "resource " array in the "result field
                        JSONArray resource = jsonObject.getJSONArray("resources");
                        //Access the first JSonObject in the "resources" array
                        JSONObject restaurantResources = resource.getJSONObject(0);
                        //Get the Url to ready to download
                        inspectionDLUrl = restaurantResources.getString("url");
                        //Get the last modified date in String form
                        String updateDate = restaurantResources.getString("last_modified");
                        //THE LATEST MODIFIED DATE FROM API
                        LocalDateTime latestModeifiedDate = LocalDateTime.parse(updateDate);
                        //the Duration between the the
                        //replace the latest update date to the new one
                        if (InspectionslastUpdateDate.isBefore(latestModeifiedDate)) {
                            //replace the latest update date to the new one
                            InspectionslastUpdateDate = latestModeifiedDate;
                            //Ask for Update and download
                            // Dialog creation
                            //setup downloadManager
                            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            //setup the Request
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(restaurantDLUrl));
                            //queue up for download
//                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                            request.setTitle("Download"); //set title in download notifcation;

                            downloadManager.enqueue(request);
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
            });
            requestQueue.add(InspectionsObjectRequest);
        }

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
}