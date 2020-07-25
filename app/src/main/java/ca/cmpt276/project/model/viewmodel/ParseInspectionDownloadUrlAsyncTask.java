package ca.cmpt276.project.model.viewmodel;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.InspectionScanner;

public class ParseInspectionDownloadUrlAsyncTask extends AsyncTask<String, String, Map<String, List<InspectionDetails>>> {
    @Override
    protected Map<String, List<InspectionDetails>> doInBackground(String ...params) {
        Map<String, List<InspectionDetails>> responseBody = null;
        try {
            URL downloadUrl = new URL(params[0]);
            InspectionScanner scanner = new InspectionScanner(downloadUrl.openStream());
            responseBody = scanner.scanAllInspectionDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
