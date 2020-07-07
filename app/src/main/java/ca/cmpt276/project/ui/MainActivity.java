package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.example.a276project.R;

import ca.cmpt276.project.model.RestaurantManager;
import ca.cmpt276.project.ui.RestListAdapter;
import ca.cmpt276.project.ui.RestaurantActivity;


public class MainActivity extends AppCompatActivity {
    private RestListAdapter.RestListClickListener listener;
    RestaurantManager manager;
    RecyclerView restList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = RestaurantManager.getInstance(getApplicationContext());
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, manager, listener);
        listener = new RestListAdapter.RestListClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurant", (Parcelable) manager.restaurants().get(position));
                startActivity(intent);
            }
        };
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));
    }
}