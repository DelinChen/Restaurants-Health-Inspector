package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.a276project.R;

import ca.cmpt276.project.model.RestaurantManager;
import ca.cmpt276.project.ui.RestListAdapter;


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
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));
    }
}