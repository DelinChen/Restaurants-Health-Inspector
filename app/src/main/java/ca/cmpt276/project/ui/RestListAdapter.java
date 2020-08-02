package ca.cmpt276.project.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.data.HazardRating;
import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantDetails;
import ca.cmpt276.project.model.data.RestaurantManager;
import ca.cmpt276.project.model.viewmodel.HealthViewModel;


public class RestListAdapter extends RecyclerView.Adapter<RestListAdapter.RestListViewHolder> {

    private Context context;
    //private RestaurantManager manager;
    //private HealthViewModel model;
    List<RestaurantDetails> list;
    private RestListClickListener listener;

    public RestListAdapter(Context context, List<RestaurantDetails> list, RestListClickListener listener) {
        this.context = context;
        //this.manager = manager;
        //this.model = model;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_row, parent, false);
        return new RestListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestListViewHolder holder, int position) {
        //Restaurant currRest = manager.restaurants().get(position);
        Restaurant currRest = list.get(position).restaurant;
        holder.name.setText(currRest.name);
        holder.address.setText(currRest.address);

        if(currRest.name.contains("Save On Foods")) {
            holder.restIcon.setImageResource(R.drawable.saveonfood);
        }else if(currRest.name.contains("Boston Pizza")) {
            holder.restIcon.setImageResource(R.drawable.bostonpizza);
        }else if(currRest.name.contains("A&W")) {
            holder.restIcon.setImageResource(R.drawable.anw);
        }else if(currRest.name.contains("Subway")) {
            holder.restIcon.setImageResource(R.drawable.subway);
        }else if(currRest.name.contains("McDonald's")) {
            holder.restIcon.setImageResource(R.drawable.mcdonalds);
        }else if(currRest.name.contains("7-Eleven")) {
            holder.restIcon.setImageResource(R.drawable.seveneleven);
        }else if(currRest.name.contains("Blenz Coffee")) {
            holder.restIcon.setImageResource(R.drawable.blenz);
        }else if(currRest.name.contains("Safeway")) {
            holder.restIcon.setImageResource(R.drawable.safeway);
        }else if(currRest.name.contains("White Spot")) {
            holder.restIcon.setImageResource(R.drawable.whitespot);
        }else if(currRest.name.contains("Burger King")) {
            holder.restIcon.setImageResource(R.drawable.burgerking);
        }else {
            holder.restIcon.setImageResource(R.drawable.restaurant);
        }

        Inspection currInspect;
        int numIssues;
        HazardRating hazardLevel;
        Date currDate;
        Date inspectDate;
        long days;
        if(!list.get(position).inspectionDetailsList.isEmpty()) {
            //currInspect = currRest.inspections.get(0);
            currInspect = list.get(position).inspectionDetailsList.get(0).inspection;
            numIssues = currInspect.numCritViolations + currInspect.numNonCritViolations;
            hazardLevel = currInspect.hazardRating;
            currDate = Calendar.getInstance().getTime();
            inspectDate = Date.from(currInspect.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            long diff = currDate.getTime() - inspectDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            days = Math.abs(hours / 24);

            SimpleDateFormat withinOneYearFormat = new SimpleDateFormat("MMMM dd");
            SimpleDateFormat oneYearBeforeFormat = new SimpleDateFormat("MMMM yyyy");
            holder.numIssues.setText("" + numIssues);

            if(days < 31) {
                holder.date.setText("" + days + " days ago");
            }
            else if (days<365){
                holder.date.setText(withinOneYearFormat.format(inspectDate));
            }
            else {
                holder.date.setText(oneYearBeforeFormat.format(inspectDate));
            }

            if(hazardLevel == HazardRating.LOW) {
                holder.hazardIcon.setImageResource(R.drawable.hazard_low);
                holder.level.setText("Low");
                holder.level.setTextColor(Color.rgb(204,204,0));
            }
            else if(hazardLevel == HazardRating.MODERATE) {
                holder.hazardIcon.setImageResource(R.drawable.hazard_medium);
                holder.level.setText("Moderate");
                holder.level.setTextColor(Color.rgb(255,165,0));
            }
            else if (hazardLevel == HazardRating.HIGH){
                holder.hazardIcon.setImageResource(R.drawable.hazard_high);
                holder.level.setText("High");
                holder.level.setTextColor(Color.RED);
            }
            else{
                holder.level.setText(" ");
            }
        }
        else {
            numIssues = 0;
            holder.date.setText("No inspections");
            holder.numIssues.setText("" + numIssues);
            holder.hazardIcon.setImageResource(0);
        }

    }

    @Override
    public int getItemCount() {
        //return manager.restaurants().size();
        return list.size();
    }

    public class RestListViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView name, date, numIssues, address, level;
        ImageView restIcon, hazardIcon;
        RestListClickListener itemListener;
        public RestListViewHolder(@NonNull View itemView, RestListClickListener itemListener) {
            super(itemView);
            name = itemView.findViewById(R.id.rest_name);
            date = itemView.findViewById(R.id.inspect_date);
            numIssues = itemView.findViewById(R.id.num_issues);
            restIcon = itemView.findViewById(R.id.rest_icon);
            hazardIcon = itemView.findViewById(R.id.hazard_icon);
            address = itemView.findViewById(R.id.rest_address);
            level = itemView.findViewById(R.id.txtLevel);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.onClick(view, getAdapterPosition());
        }
    }

    public interface RestListClickListener {
        void onClick(View v, int position);
    }
}
