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

import java.util.List;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.Inspection;
import ca.cmpt276.project.model.RestaurantManager;
import ca.cmpt276.project.model.Violation;

public class InspectionActivity extends AppCompatActivity {
    RestaurantManager manager;
    String trackingNumber;
    Inspection inspection;
    int position;
    private List<Violation> violations;

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
        inspection = manager.get(trackingNumber).getInspections().get(position);

        // get violations
        violations = inspection.getViolationList();

        populateInspection();
        populateViolations();

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
            if (currentViolation.getHazardType().equals("food")){
                imageView.setImageResource(R.drawable.violation_food);
            }
            else if (currentViolation.getHazardType().equals("pest")){
                imageView.setImageResource(R.drawable.violation_pest);
            }

            else if (currentViolation.getHazardType().equals("equipment")){
                imageView.setImageResource(R.drawable.violation_equipment);
            }

            String description = currentViolation.getDescription();
            TextView txtDescription = findViewById(R.id.txtDescription);
            txtDescription.setText(description);

            Boolean critical = currentViolation.isCritical();
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
        int hazardLevel = inspection.getHazaradRating();
        String inspectionType = inspection.getInspect_type();
        String date = inspection.getInspect_date();
        int critical = inspection.getInspect_crit_issue();
        int nonCritical = inspection.getInspect_nonCrit_issue();

        //
        ImageView image = findViewById(R.id.imgHazard);
        if (hazardLevel == 0){
            image.setImageResource(R.drawable.hazard_low);
        }
        else if (hazardLevel == 1){
            image.setImageResource(R.drawable.hazard_medium);
        }
        else {
            image.setImageResource(R.drawable.hazard_high);
        }

        TextView txtType = findViewById(R.id.txtName);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtCritical = findViewById(R.id.txtCritical);
        TextView txtNonCritical = findViewById(R.id.txtNonCritical);

        txtType.setText(inspectionType);
        txtDate.setText(date);
        txtCritical.setText("Critical: " + critical);
        txtNonCritical.setText("Non-critical: " + nonCritical);
    }
}