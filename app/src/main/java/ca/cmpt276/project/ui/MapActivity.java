package ca.cmpt276.project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.Restaurant;
import ca.cmpt276.project.model.RestaurantManager;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String SHARED_PREFS = "sharedPrefs";
    public static final String DEFAULT_DATE = "01/01/2020";
    private static final long TWENTY_H_IN_MS = 20 * 60 * 60 * 1000;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap mMap;


    RestaurantManager manager;
    int sum;
    UpdateTask updateTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        manager = RestaurantManager.getInstance(getApplicationContext());

        getLocationPermission();

        // tap peg to pop up name, address and hazard level

        // tap again to goto restaurant's full info page
        try {
            updateRestaurant();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void updateRestaurant() throws ParseException {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String lastUpdateStr = sharedPreferences.getString("last_update", DEFAULT_DATE);
        Date currDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date lastUpdate = dateFormat.parse(lastUpdateStr);
        if(currDate.getTime() - lastUpdate.getTime() < TWENTY_H_IN_MS) {
            return;
        }
        else {
            //function to check if there is new data

            //
            createAskDialog();

            /*SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("last_update", dateFormat.format(Calendar.getInstance().getTime()));
            return;*/
        }
    }

    private void createAskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle(R.string.update_available_text)
                .setMessage(R.string.update_now_text)
                .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MapActivity.this, "onCLick" , Toast.LENGTH_SHORT).show();
                        updateTask = new UpdateTask();
                        updateTask.execute();
                    }
                })
                .setNegativeButton(R.string.no_text, null)
                .setCancelable(false)
                .show();

    }

    private void createUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
        View view = inflater.inflate(R.layout.wait_dialog, null);
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
    };

    // display pegs showing the location of each restaurant with hazard icons
    private void geoLocate() {
        Bitmap hazardIcon = BitmapFactory.decodeResource(getResources(), R.drawable.restaurant);
        BitmapDescriptor hazardIconBit;

        if (manager.size() > 0) {
            for (Restaurant res : manager.restaurants()) {
                if(!res.inspections.isEmpty()){
                    if(res.inspections.get(0).hazardRating.toString() == "Low") {
                        hazardIcon = BitmapFactory.decodeResource(getResources(), R.drawable.hazard_low);
                    }
                    else if (res.inspections.get(0).hazardRating.toString() ==  "Moderate") {
                        hazardIcon = BitmapFactory.decodeResource(getResources(), R.drawable.hazard_medium);
                    }
                    else if (res.inspections.get(0).hazardRating.toString() ==  "High") {
                        hazardIcon = BitmapFactory.decodeResource(getResources(), R.drawable.hazard_high);
                    }
                }
                else{
                    hazardIcon = BitmapFactory.decodeResource(getResources(), R.drawable.restaurant);
                }
                hazardIconBit = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(hazardIcon, 100, 100, false));
                MarkerOptions options = new MarkerOptions()
                        .position(new LatLng(res.latitude, res.longitude))
                        .title(res.name)
                        .icon(hazardIconBit);
                mMap.addMarker(options);
            }

        }

    }

    // get user location and center on current location
    private void getDeviceLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {

                Task location = mFusedLocationClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving camera to lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapActivity.this);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "map is ready!", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            geoLocate();

        }
    }

    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // switch to a list view
            case R.id.list:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class UpdateTask extends AsyncTask<RestaurantManager, Integer, Integer> {
        AlertDialog UpdateDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
            View view = inflater.inflate(R.layout.wait_dialog, null);
            UpdateDialog = builder.setView(view)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UpdateDialog.dismiss();
                            updateTask.cancel(true);
                        }
                    })
                    .create();
            UpdateDialog.show();
        }

        @Override
        protected void onPostExecute(Integer add) {
            super.onPostExecute(sum);
            sum = add;
            UpdateDialog.dismiss();
            //geoLocate();
            Toast.makeText(MapActivity.this, "Sum after execution is: " + sum, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(RestaurantManager... restaurantManagers) {
            int add = 0;
            for(int i = 0; i < 1000; i++) {
                add++;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < 1000; i++) {
                add++;
            }
            return add;
        }
    }
}

