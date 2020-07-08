package ca.cmpt276.project.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ca.cmpt276.project.R;

import java.util.List;

import ca.cmpt276.project.model.Inspection;
import ca.cmpt276.project.model.Restaurant;
import ca.cmpt276.project.model.RestaurantManager;

// Display details of single restaurant
public class RestaurantActivity extends AppCompatActivity {
    RestaurantManager manager;
    Restaurant restaurant;
    String trackingNumber;
    private List<Inspection> inspections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getSupportActionBar().setTitle("Restaurant Health Inspector");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the intent and set the restaurant information
        Intent intent = getIntent();
        trackingNumber = intent.getStringExtra("tracking number");
        manager = RestaurantManager.getInstance();
        restaurant = manager.get(trackingNumber);

        TextView name = findViewById(R.id.txtName);
        TextView address = findViewById(R.id.txtAddress);
        TextView coords = findViewById(R.id.txtCoords);
        name.setText(restaurant.name);
        address.setText(restaurant.address);
        coords.setText("(" + restaurant.latitude + ", " + restaurant.longitude + ")");

        // get the inspections
        inspections = restaurant.inspections;

        // set the inspections listview
        populateListView();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void populateListView() {
        ArrayAdapter <Inspection> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.listInspections);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RestaurantActivity.this, InspectionActivity.class);
                intent.putExtra("tracking number", restaurant.trackingNumber);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Inspection>{
        public MyListAdapter(){
            super(RestaurantActivity.this, R.layout.listview_inspections,inspections);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listview_inspections, parent, false);
            }

            // find the inspection to work with
            Inspection currentInspection = inspections.get(position);

            // set the image
            ImageView imageView = itemView.findViewById(R.id.imgHazard);
            if (currentInspection.hazardRating.toString().equals("Low")){
                imageView.setImageResource(R.drawable.hazard_low);
            }
            else if (currentInspection.hazardRating.toString().equals("Moderate")){
                imageView.setImageResource(R.drawable.hazard_medium);
            }
            else if (currentInspection.hazardRating.toString().equals("High")){
                imageView.setImageResource(R.drawable.hazard_high);
            }
            else{

            }
            TextView txtCritical = itemView.findViewById(R.id.txtCritical);
            txtCritical.setText("Critical: " + currentInspection.numCritViolations);

            TextView txtNonCritical = itemView.findViewById(R.id.txtNonCritical);
            txtNonCritical.setText("Non critical: " + currentInspection.numNonCritViolations);

            TextView txtDate = itemView.findViewById(R.id.txtDate);
            txtDate.setText("Date: " + currentInspection.date.toString());
            return itemView;

        }
    }

}