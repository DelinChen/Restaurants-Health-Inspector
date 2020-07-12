package ca.cmpt276.project.model;


import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InspectionJSONParser {

    private static String URL = "https://data.surrey.ca/dataset/948e994d-74f5-41a2-b3cb-33fa6a98aa96/resource/30b38b66-649f-4507-a632-d5f6f5fe87f1/download/fraserhealthrestaurantinspectionreports.csv";
    private static final String USERID = "userID";

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
