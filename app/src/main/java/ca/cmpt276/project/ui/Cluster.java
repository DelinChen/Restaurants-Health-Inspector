package ca.cmpt276.project.ui;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Cluster implements ClusterItem {
    private LatLng mPosition = null;
    private String mTitle = null;
    private String mAddress;
    private String mHazard;
    private String snippet;

    public Cluster(double lat, double lng, String title, String address, String hazard) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mAddress = address;
        mHazard = hazard;
        snippet = address + ", Hazard: "+ hazard;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getHazard() {
        return mHazard;
    }
}
