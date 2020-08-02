package ca.cmpt276.project.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import ca.cmpt276.project.ApplicationClass;
import ca.cmpt276.project.R;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.viewmodel.HealthViewModel;
import ca.cmpt276.project.model.viewmodel.HealthViewModelFactory;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String SHARED_PREFS = "sharedPrefs";
    public static final long DEFAULT_DATE = 0;
    private static final long TWENTY_H_IN_MS = 20 * 60 * 60 * 1000;
    private static  final String LAST_UPDATE = "last_update_long";
    private boolean isUpdated = false;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap mMap;
    private Location currentLocation;
    private ClusterManager<Cluster> mClusterManager;
    MarkerClusterRenderer mRenderer;
    LocationManager locationManager;

    HealthViewModel model;
    int sum;
    UpdateTask updateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //manager = RestaurantManager.getInstance(getApplicationContext());
        ViewModelProvider.Factory factory = new HealthViewModelFactory(this);
        model = new ViewModelProvider(this, factory).get(HealthViewModel.class);

        locationManager = (LocationManager) this.getSystemService(MapActivity.LOCATION_SERVICE);

        getLocationPermission();

        // tap peg to pop up name, address and hazard level

        // tap again to goto restaurant's full info page
        model.restaurantDetailsData.observe(this, restaurantDetailsList -> {
            try {
                updateRestaurant();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
    }

//




    private void updateRestaurant() throws ParseException, IOException {
        if(ApplicationClass.dontUpdate) {
            return;
        }
        //function to check if there is new data

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        long lastUpdate = sharedPreferences.getLong(LAST_UPDATE, DEFAULT_DATE);
        long currDateLong = Calendar.getInstance().getTimeInMillis();

        if(currDateLong - lastUpdate < TWENTY_H_IN_MS) {
            return;
        }
        else {
            createAskDialog();
        }
    }

    private void createAskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle(R.string.update_available_text)
                .setMessage(R.string.update_now_text)
                .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateTask = new UpdateTask();
                        updateTask.execute();
                    }
                })
                .setNegativeButton(R.string.no_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApplicationClass.dontUpdate = true;
                    }
                })
                .setCancelable(false)
                .show();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLocation = location;

            moveCamera(new LatLng(location.getLatitude(),location.getLongitude()), DEFAULT_ZOOM);
        }
    };

    // display pegs showing the location of each restaurant with hazard icons
    private void geoLocate(List<RestaurantDetails> list) {
        setUpClusterer();
        Cluster offsetItem;

        if (list.size() > 0) {
            for (RestaurantDetails res : list) {
                if (!res.inspectionDetailsList.isEmpty()) {
                    offsetItem= new Cluster(res.restaurant.latitude, res.restaurant.longitude, res.restaurant.name, res.restaurant.address, res.inspectionDetailsList.get(0).inspection.hazardRating.toString());
                } else {
                    offsetItem = new Cluster(res.restaurant.latitude, res.restaurant.longitude, res.restaurant.name, res.restaurant.address, "null");
                }
                //mRenderer = new DefaultClusterRenderer(MapActivity.this, mMap, mClusterManager);
                //mClusterManager.setRenderer(mRenderer);
                mClusterManager.addItem(offsetItem);
                mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Cluster>() {
                    @Override
                    public void onClusterItemInfoWindowClick(Cluster item) {
                        for(RestaurantDetails res : list){
                            if (item.getPosition().longitude == res.restaurant.longitude) {
                                Intent intent = new Intent(MapActivity.this, RestaurantActivity.class);
                                intent.putExtra("tracking number", res.restaurant.trackingNumber);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                });
            }
        }
    }


    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<Cluster>(this, mMap);
        mRenderer = new MarkerClusterRenderer(this, mMap, mClusterManager);
        mClusterManager.setRenderer(mRenderer);
        mMap.setOnCameraIdleListener(mClusterManager);

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
                            currentLocation = (Location) task.getResult();
                            if(currentLocation!=null) {
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        DEFAULT_ZOOM);
                            }
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        Boolean isTrue = true;
                        Intent i = getIntent();
                        double longitude = i.getDoubleExtra("longitude",0);
                        for(Cluster m: mClusterManager.getAlgorithm().getItems()){
                            if(m.getPosition().longitude == longitude) {
                                isTrue = false;
                                // mRenderer.clusterMarkerMap.get(m).showInfoWindow();
                                moveCamera(m.getPosition(),DEFAULT_ZOOM);
                                break;
                            }
                        }
                        if (isTrue){
                            if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    200,
                                    10, mLocationListener);
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
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

            model.restaurantDetailsData.observe(this, restaurantDetailsList -> {
                geoLocate(restaurantDetailsList);
            });
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
            case R.id.search:
                startActivity(new Intent(this,SearchActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class UpdateTask extends AsyncTask<Void, Integer, Integer> {
        AlertDialog UpdateDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
            View view = inflater.inflate(R.layout.wait_dialog, null);
            UpdateDialog = builder.setView(view)
                    .setNegativeButton(R.string.cancel_txt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UpdateDialog.dismiss();
                            updateTask.cancel(true);
                        }
                    })
                    .setCancelable(false)
                    .create();
            UpdateDialog.show();
        }

        @Override
        protected void onPostExecute(Integer add) {
            super.onPostExecute(sum);
            sum = add;
            UpdateDialog.dismiss();
            //geoLocate();
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(LAST_UPDATE, Calendar.getInstance().getTimeInMillis());
            editor.apply();
            Toast.makeText(MapActivity.this, "Sum after ASYNCTASK = " + sum, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
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

