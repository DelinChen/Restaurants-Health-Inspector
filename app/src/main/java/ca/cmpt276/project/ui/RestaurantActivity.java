package ca.cmpt276.project.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
        //inspections = restaurant.getInspections();

        // set the inspections listview
        //populateListView();
    }

    private void populateListView() {
        ArrayAdapter <Inspection> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.listInspections);
        list.setAdapter(adapter);

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
            if (currentInspection.getHazaradRating() == 0){
                imageView.setImageResource(R.drawable.hazard_low);
            }
            else if (currentInspection.getHazaradRating() == 1){
                imageView.setImageResource(R.drawable.hazard_medium);
            }
            else {
                imageView.setImageResource(R.drawable.hazard_high);
            }
            TextView txtCritical = itemView.findViewById(R.id.txtCritical);
            txtCritical.setText("Critical: " + currentInspection.getInspect_crit_issue());

            TextView txtNonCritical = itemView.findViewById(R.id.txtNonCritical);
            txtNonCritical.setText("Critical: " + currentInspection.getInspect_nonCrit_issue());

            TextView txtDate = itemView.findViewById(R.id.txtDate);
            txtDate.setText("Critical: " + currentInspection.getInspect_date());
            return itemView;

        }
    }

}