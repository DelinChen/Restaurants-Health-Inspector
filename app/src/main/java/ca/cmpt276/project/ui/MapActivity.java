package ca.cmpt276.project.ui;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.RestaurantManager;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DEFAULT_DATE = "01/01/0001";
    static final long TWENTY_H_IN_MS = 20 * 60 * 60 * 1000;
    RestaurantManager manager = RestaurantManager.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        Date lastUpdate = dateFormat.parse(lastUpdateStr);
        if(currDate.getTime() - lastUpdate.getTime() < TWENTY_H_IN_MS) {
            return;
        }
        else {
            //function to check if there is new data

            //
            createAskDialog();
            
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("last_update", dateFormat.format(Calendar.getInstance().getTime()));
            return;
        }
    }

    private void createAskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle(R.string.update_available_text)
                .setMessage(R.string.update_now_text)
                .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createUpdateDialog();
                    }
                })
                .setNegativeButton(R.string.no_text, null)
                .setCancelable(false);
    }

    private void createUpdateDialog() {
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

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
}