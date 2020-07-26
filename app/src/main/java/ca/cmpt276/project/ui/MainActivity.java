package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.List;
import java.util.Locale;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.viewmodel.HealthViewModel;
import ca.cmpt276.project.model.viewmodel.HealthViewModelFactory;

import static ca.cmpt276.project.ApplicationClass.CHANNEL_1_ID;
import static ca.cmpt276.project.ApplicationClass.CHANNEL_2_ID;


public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RecyclerView restList;
    HealthViewModel model;
    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        ViewModelProvider.Factory factory = new HealthViewModelFactory(this);
        model = new ViewModelProvider(this, factory).get(HealthViewModel.class);

        model.restaurantDetailsData.observe(this, restaurantDetailsList -> {
            updateUI(restaurantDetailsList);
            //The method sends Notification to user, need to change to favourite later
            sendOnChannel1(restList);
        });
    }

    private void updateUI(List<RestaurantDetails> list) {
        restList = findViewById(R.id.rest_list);
        RestListAdapter adapter = new RestListAdapter(this, list, this);
        restList.setAdapter(adapter);
        restList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("tracking number",model.restaurantDetailsData.getValue().get(position).restaurant.trackingNumber);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                double longitude = data.getDoubleExtra("longitude", 0);
                Intent intent = new Intent(this, MapActivity.class);
                intent.putExtra("longitude",longitude);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                finish();
            }
        }
    }


    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // switch to map view
            case R.id.map:
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Send Notificaion Methods
    public void sendOnChannel1(View view){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_error_24)
                .setContentTitle("Notification works") //The content to added into
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
//
//    public void sendOnChannel2(View view){
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
//                .setSmallIcon(R.drawable.ic_baseline_error_24)
//                .setContentTitle("@string/Favourite")
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//
//        notificationManager.notify(2, notification);
//    }
}