package ca.cmpt276.project.ui;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import ca.cmpt276.project.R;
import ca.cmpt276.project.model.data.HazardRating;

public class Cluster implements ClusterItem {
    private LatLng mPosition = null;
    private String mTitle = null;
    private String mAddress;
    private HazardRating mHazard;
    private String snippet;

    public Cluster(double lat, double lng, String title, String address, HazardRating hazard) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mAddress = address;
        mHazard = hazard;
        snippet = address + ", Hazard: "+ hazard.toString();
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

    public HazardRating getHazard() {
        return mHazard;
    }

    public void setSnippet(String string) {
        snippet = string;
    }
}
