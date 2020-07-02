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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.cmpt276.project.model.RestaurantManager;


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
        /*Restaurant currRest = manager.getRestaurant(position);
        Inspection currInspect = currRest.getRest_inspectionList().get(currRest.getNum_reports() - 1);
        int numIssues = currInspect.getInspect_crit_issue() + currInspect.getInspect_nonCrit_issue();
        Date currDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

        holder.name.setText(currRest.getRest_name());
        holder.numIssues.setText("" + numIssues);
        holder.date.setText();
        holder.hazardIcon.setImageDrawable();*/

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
