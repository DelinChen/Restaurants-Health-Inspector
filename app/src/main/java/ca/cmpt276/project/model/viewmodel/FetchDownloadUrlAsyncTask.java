package ca.cmpt276.project.model.viewmodel;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class FetchDownloadUrlAsyncTask extends AsyncTask<String, String, HealthApiResponse> {
    @Override
    protected HealthApiResponse doInBackground(String ...params) {
        String dataDownloadUrl = "";
        LocalDateTime dataLastModified = null;
        try {
            URL restUrl = new URL(params[0]);
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(restUrl.openStream()));
            String responseBody = responseReader.lines().parallel().collect(Collectors.joining());

            JSONObject parentObject = new JSONObject(responseBody);
            JSONObject childObject =  parentObject.getJSONObject("result");
            JSONArray parentArray = childObject.getJSONArray("resources");
            JSONObject targetObject = parentArray.getJSONObject(0);

            dataDownloadUrl = targetObject.getString("url");
            dataLastModified = LocalDateTime.parse(targetObject.getString("last_modified"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return new HealthApiResponse(dataDownloadUrl, dataLastModified);
    }
}
