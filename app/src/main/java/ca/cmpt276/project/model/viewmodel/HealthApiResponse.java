package ca.cmpt276.project.model.viewmodel;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class HealthApiResponse {
    public final String dataDownloadUrl;
    public final LocalDateTime lastModified;

    public HealthApiResponse(String dataDownloadUrl, LocalDateTime lastModified) {
        this.dataDownloadUrl = dataDownloadUrl;
        this.lastModified = lastModified;
    }
}
