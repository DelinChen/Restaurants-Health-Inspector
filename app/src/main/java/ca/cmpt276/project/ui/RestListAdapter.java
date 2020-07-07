package ca.cmpt276.project.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a276project.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ca.cmpt276.project.model.Inspection;
import ca.cmpt276.project.model.Restaurant;
import ca.cmpt276.project.model.RestaurantManager;




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
        /*Inspection currInspect = currRest.getInspections().get(currRest.getInspections().size() - 1);
        int numIssues = currInspect.getInspect_crit_issue() + currInspect.getInspect_nonCrit_issue();
        int hazardLevel = currInspect.getHazaradRating();
        Date currDate = Calendar.getInstance().getTime();

        Date currInspectDate = null;
        try {
            currInspectDate = new SimpleDateFormat("yyyyMMdd").parse(currInspect.getInspect_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diffInMillies = Math.abs(currDate.getTime() - currInspectDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");*/

        holder.name.setText(currRest.name);
        holder.address.setText(currRest.address);
        holder.restIcon.setImageResource(R.drawable.restaurant);
        /*holder.numIssues.setText("" + numIssues);

        if(diffInDays < 31) {
            holder.date.setText("" + diffInDays + " days ago");
        }
        else {
            holder.date.setText(dateFormat.format(currInspectDate));
        }

        if(hazardLevel == 1) {
            holder.hazardIcon.setImageResource(R.drawable.hazard_low);
        }
        else if(hazardLevel == 2) {
            holder.hazardIcon.setImageResource(R.drawable.hazard_medium);
        }
        else {
            holder.hazardIcon.setImageResource(R.drawable.hazard_high);
        }

        holder.restIcon.setImageResource(R.drawable.restaurant);*/

    }

    @Override
    public int getItemCount() {
        return manager.restaurants().size();
    }

    public class RestListViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView name, date, numIssues, address;
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
