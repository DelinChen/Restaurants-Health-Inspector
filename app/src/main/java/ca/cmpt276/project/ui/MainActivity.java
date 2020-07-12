package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.io.IOException;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.RestaurantJSONParser;
import ca.cmpt276.project.model.RestaurantManager;


public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RestaurantManager manager;
    RecyclerView restList;

    private Intent serviceIntent;
    private boolean mStopLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Restaurant Health Inspector");

        //for checking update timer
//        serviceIntent = new Intent(getApplicationContext(), MyService.class);


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
    }

    //UPDATE DIALOG
    /*      protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction


            x=list.size();

                    if(x==0)
            jIndex=0;
                    else
            jIndex=x;

            dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Update to the latest info);
                dialog.setMessage("I am updating");
                dialog.show();
    */
            protected void doInBackground(Void...params) throws IOException {
            JSONObject jsonObject = RestaurantJSONParser.getDataFromWeb();



     }

}