package ca.cmpt276.project.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import ca.cmpt276.project.R;

import ca.cmpt276.project.model.data.HazardRating;
import ca.cmpt276.project.model.data.Inspection;
import ca.cmpt276.project.model.data.Restaurant;
import ca.cmpt276.project.model.data.RestaurantManager;




public class RestListAdapter extends RecyclerView.Adapter<RestListAdapter.RestListViewHolder> {

    private Context context;
    private RestaurantManager manager;
    private RestListClickListener listener;

    public RestListAdapter(Context context, RestaurantManager manager, RestListClickListener listener) {
        this.context = context;
        this.manager = manager;
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
        Restaurant currRest = manager.restaurants().get(position);
        holder.name.setText(currRest.name);
        holder.address.setText(currRest.address);
        holder.restIcon.setImageResource(R.drawable.restaurant);

        Inspection currInspect;
        int numIssues;
        HazardRating hazardLevel;
        Date currDate;
        Date inspectDate;
        long days;
        if(!currRest.inspections.isEmpty()) {
            currInspect = currRest.inspections.get(0);
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
        return manager.restaurants().size();
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
