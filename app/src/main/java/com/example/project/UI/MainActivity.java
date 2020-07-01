package com.example.project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.Model.RestaurantManager;
import com.example.project.R;

public class MainActivity extends AppCompatActivity {

    RestaurantManager manager;
    RecyclerView restList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager.getInstance();
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, manager);
    }
}