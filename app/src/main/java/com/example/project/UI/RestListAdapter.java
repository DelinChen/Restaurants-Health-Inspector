package com.example.project.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.RestaurantManager;
import com.example.project.R;

public class RestListAdapter extends RecyclerView.Adapter<RestListAdapter.RestListViewHolder> {

    private Context context;
    private RestaurantManager manager;

    public RestListAdapter(Context context, RestaurantManager manager) {
        this.context = context;
        this.manager = manager;
    }

    @NonNull
    @Override
    public RestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_row, parent, false);
        return new RestListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestListViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RestListViewHolder extends RecyclerView.ViewHolder{
        TextView name, date, numIssues;
        ImageView restIcon, hazardIcon;
        public RestListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rest_name);
            date = itemView.findViewById(R.id.inspect_date);
            numIssues = itemView.findViewById(R.id.num_issues);
            restIcon = itemView.findViewById(R.id.rest_icon);
            hazardIcon = itemView.findViewById(R.id.hazard_icon);

        }
    }
}
