package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.Inspection;

public class InspectionActivity extends AppCompatActivity {

    Inspection inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        Intent intent = getIntent();
        inspection = intent.getParcelableExtra("restaurant");
    }
}