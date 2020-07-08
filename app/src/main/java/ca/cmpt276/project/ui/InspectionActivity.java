package ca.cmpt276.project.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.HazardRating;
import ca.cmpt276.project.model.Inspection;
import ca.cmpt276.project.model.InspectionType;
import ca.cmpt276.project.model.RestaurantManager;
import ca.cmpt276.project.model.Violation;
import ca.cmpt276.project.model.ViolationCategory;

public class InspectionActivity extends AppCompatActivity {
    RestaurantManager manager;
    String trackingNumber;
    Inspection inspection;
    int position;
    List<Violation> violations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        // set manager
        manager = RestaurantManager.getInstance();

        // get intent and get necessary extras
        Intent intent = getIntent();
        trackingNumber = intent.getStringExtra("tracking number");
        position = intent.getIntExtra("position", 0);
        inspection = manager.get(trackingNumber).inspections.get(position);

        // get violations
        violations = inspection.violations;

        //populateInspection();
        //populateViolations();

    }

    private void populateViolations() {
        ArrayAdapter <Violation> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.listViolations);
        list.setAdapter(adapter);
        /*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show exact description
            }
        });
         */
    }

    private class MyListAdapter extends ArrayAdapter<Violation>{
        public MyListAdapter(){
            super(InspectionActivity.this, R.layout.listview_violations, violations);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listview_violations, parent, false);
            }

            // find the inspection to work with
            Violation currentViolation = violations.get(position);

            // set the image
            ImageView imageView = itemView.findViewById(R.id.imgCategory);
            if (currentViolation.category == ViolationCategory.FOOD){
                imageView.setImageResource(R.drawable.violation_food);
            }
            else if (currentViolation.category == ViolationCategory.PEST){
                imageView.setImageResource(R.drawable.violation_pest);
            }

            else if (currentViolation.category == ViolationCategory.EQUIPMENT){
                imageView.setImageResource(R.drawable.violation_equipment);
            }

            String description = currentViolation.description;
            TextView txtDescription = findViewById(R.id.txtDescription);
            txtDescription.setText(description);

            Boolean critical = currentViolation.isCritical;
            ImageView imgCategory = findViewById(R.id.imageView3);
            TextView txtCritical = findViewById(R.id.txtCriticalExtent);
            if(critical == true){
                imgCategory.setImageResource(R.drawable.icon_critical);
                txtCritical.setText("Critical");
                txtCritical.setTextColor(Color.RED);
            }
            else{
                imgCategory.setImageResource(R.drawable.icon_noncritical);
                txtCritical.setText("Non-ritical");
                txtCritical.setTextColor(Color.rgb(255,165,0));
            }

            return itemView;

        }
    }

    private void populateInspection() {
        // find single inspection data
        HazardRating hazardLevel = inspection.hazardRating;
        InspectionType inspectionType = inspection.type;
        LocalDate date = inspection.date;
        int critical = inspection.numCritViolations;
        int nonCritical = inspection.numNonCritViolations;

        //
        ImageView image = findViewById(R.id.imgHazard);
        if (hazardLevel == HazardRating.LOW){
            image.setImageResource(R.drawable.hazard_low);
        }
        else if (hazardLevel == HazardRating.MODERATE){
            image.setImageResource(R.drawable.hazard_medium);
        }
        else {
            image.setImageResource(R.drawable.hazard_high);
        }

        TextView txtType = findViewById(R.id.txtName);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtCritical = findViewById(R.id.txtCritical);
        TextView txtNonCritical = findViewById(R.id.txtNonCritical);

        txtType.setText(inspectionType.toString());
        txtDate.setText(date.toString());
        txtCritical.setText("Critical: " + critical);
        txtNonCritical.setText("Non-critical: " + nonCritical);
    }
}