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

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantManager;

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
        if(inspections.isEmpty()){
            TextView empty = findViewById(R.id.txtEmpty);
            empty.setVisibility(View.VISIBLE);
        }
        else {
            populateListView();
        }
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

            Date currDate = Calendar.getInstance().getTime();
            Date inspectDate = Date.from(currentInspection.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            long diff = currDate.getTime() - inspectDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = Math.abs(hours / 24);

            SimpleDateFormat withinOneYearFormat = new SimpleDateFormat("MMMM dd");
            SimpleDateFormat oneYearBeforeFormat = new SimpleDateFormat("MMMM yyyy");

            TextView txtDate = itemView.findViewById(R.id.txtDate);
            if(days < 31) {
                txtDate.setText("" + days + " days ago");
            }
            else if (days<365){
                txtDate.setText(withinOneYearFormat.format(inspectDate));
            }
            else {
                txtDate.setText(oneYearBeforeFormat.format(inspectDate));
            }

            return itemView;

        }
    }

}