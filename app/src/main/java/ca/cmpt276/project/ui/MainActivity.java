package ca.cmpt276.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.RestaurantManager;


public class MainActivity extends AppCompatActivity implements RestListAdapter.RestListClickListener {
    RestaurantManager manager;
    RecyclerView restList;
    TextView textView1;

    private static String REST_DL_URL;
    private static final String REST_API_URL = "https://data.surrey.ca/api/3/action/package_show?id=restaurants";
    private static LocalDateTime LAST_MODIFIED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Restaurant Health Inspector");

        textView1 = (TextView) findViewById(R.id.textView4);


        updateUI();
        new JSONTask().execute(REST_API_URL);
    }


        public class JSONTask extends AsyncTask< String, String, String>{

            @Override
            protected String doInBackground(String...params){
                HttpURLConnection connection;
                BufferedReader reader;
                URL url = null;
                try {
                    url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer stringBuffer = new StringBuffer();
                    String line = "";
                    while((line = reader.readLine()) != null){
                        stringBuffer.append(line);
                    }

                    String finalJson = stringBuffer.toString();
                    JSONObject parentObject = new JSONObject(finalJson);
                    JSONObject childObject =  parentObject.getJSONObject("result");
                    JSONArray parentArray = childObject.getJSONArray("resources");
                    JSONObject targetObject = parentArray.getJSONObject(0);

                    REST_DL_URL = targetObject.getString("url");
                    LAST_MODIFIED = LocalDateTime.parse(targetObject.getString("last_modified"));

//
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);

            }
        }






    private void updateUI() {
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
}