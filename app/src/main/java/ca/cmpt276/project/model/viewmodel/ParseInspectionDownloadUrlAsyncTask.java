package ca.cmpt276.project.model.viewmodel;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ca.cmpt276.project.model.data.InspectionDetails;
import ca.cmpt276.project.model.data.InspectionScanner;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantScanner;

public class ParseInspectionDownloadUrlAsyncTask extends AsyncTask<String, String, List<InspectionDetails>> {
    @Override
    protected List<InspectionDetails> doInBackground(String ...params) {
        if(android.os.Debug.isDebuggerConnected()) {
            android.os.Debug.waitForDebugger();
        }

        List<InspectionDetails> responseBody = null;
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
