package ca.cmpt276.project.model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RestaurantJSONParser {

    private static final String URL = "https://data.surrey.ca/dataset/3c8cb648-0e80-4659-9078-ef4917b90ffb/resource/0e5d04a2-be9b-40fe-8de2-e88362ea916b/download/restaurants.csv";

    public static JSONObject getDataFromWeb() throws IOException {
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();

            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
