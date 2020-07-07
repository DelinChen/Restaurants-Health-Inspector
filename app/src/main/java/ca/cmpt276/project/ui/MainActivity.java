package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.RestaurantManager;


public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RestaurantManager manager;
    RecyclerView restList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = RestaurantManager.getInstance(getApplicationContext());
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, manager, this);
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("tracking number", manager.restaurants().get(position).trackingNumber);
        startActivity(intent);
        //Toast.makeText(this, "testing", Toast.LENGTH_LONG).show();
    }
}