package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.Inspection;
import ca.cmpt276.project.model.Restaurant;
import ca.cmpt276.project.model.RestaurantManager;

public class InspectionActivity extends AppCompatActivity {
    RestaurantManager manager;
    Restaurant restaurant;
    String trackingNumber;
    Inspection inspection;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        Intent intent = getIntent();
        trackingNumber = intent.getStringExtra("tracking number");
        position = intent.getIntExtra("position", 0);
        manager = RestaurantManager.getInstance();
        inspection = manager.get(trackingNumber).getInspections().get(position);
    }
}