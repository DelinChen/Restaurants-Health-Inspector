package ca.cmpt276.project.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Map;

import ca.cmpt276.project.R;

public class MarkerClusterRenderer extends DefaultClusterRenderer<Cluster>{   // 1
    public Map <Cluster, Marker> clusterMarkerMap = new HashMap<>();
    private static final int MARKER_DIMENSION = 48;  // 2
    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;

    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<Cluster> clusterManager) {
        super(context, map, clusterManager);
        iconGenerator = new IconGenerator(context);  // 3
        markerImageView = new ImageView(context);
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);  // 4
    }

    @Override
    public void setOnClusterItemInfoWindowClickListener(ClusterManager.OnClusterItemInfoWindowClickListener<Cluster> listener) {
        super.setOnClusterItemInfoWindowClickListener(listener);
    }

    @Override
    protected void onBeforeClusterItemRendered(Cluster item, MarkerOptions markerOptions) { // 5
        if(!item.getHazard().isEmpty()){
            if(item.getHazard() == "Low") {
                markerImageView.setImageResource(R.drawable.hazard_low);  // 6
            } else if (item.getHazard() == "Moderate") {
                markerImageView.setImageResource(R.drawable.hazard_medium);
            } else if (item.getHazard() == "High") {
                markerImageView.setImageResource(R.drawable.hazard_high);
            } else if (item.getHazard() == "null") {
                markerImageView.setImageResource(R.drawable.restaurant);
            }
        } else {
        markerImageView.setImageResource(R.drawable.restaurant);
    }
        Bitmap icon = iconGenerator.makeIcon();  // 7
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));  // 8
        markerOptions.title(item.getTitle());
        markerOptions.snippet(item.getSnippet());
    }

    @Override
    protected void onClusterItemRendered(@NonNull Cluster clusterItem, @NonNull Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
        clusterMarkerMap.put(clusterItem, marker);
    }

    @Override
    public Marker getMarker(com.google.maps.android.clustering.Cluster<Cluster> cluster) {
        return super.getMarker(cluster);
    }

    @Override
    public void setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<Cluster> listener) {
        super.setOnClusterItemClickListener(listener);
    }

}