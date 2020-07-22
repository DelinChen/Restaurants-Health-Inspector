package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.RestaurantManager;
import ca.cmpt276.project.model.viewmodel.HealthViewModel;
import ca.cmpt276.project.model.viewmodel.HealthViewModelFactory;


public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RecyclerView restList;
    HealthViewModel model;

    private static String REST_DL_URL;
    private static final String REST_API_URL = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";
    private static LocalDateTime LAST_MODIFIED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelProvider.Factory factory = new HealthViewModelFactory(this);
        model = new ViewModelProvider(this, factory).get(HealthViewModel.class);
        
        model.restaurantDetailsData.observe(this, restaurantDetailsList -> {
            updateUI(restaurantDetailsList);
        });
    }

    private void updateUI(List<RestaurantDetails> list) {
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, list, this);
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("tracking number",model.restaurantDetailsData.getValue().get(position).restaurant.trackingNumber);
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