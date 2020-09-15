package com.example.empower.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;
import com.example.empower.entity.Step;

import java.util.List;

public class MyStepsAdapter  extends RecyclerView.Adapter<MyStepsAdapter.MyViewHolder> {


    private LayoutInflater mInflater;
    Context context;
    List<Step> stepList;

    public MyStepsAdapter (Context context, List<Step> stepList){
        this.context = context;
        this.stepList = stepList;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.steps_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Step step = stepList.get(position);

        holder.stepCount.setText("Step: " + position);
        holder.distance.setText(step.getDistance());
        holder.travelMode.setText(step.getTravelMode());
        holder.shortName.setText(step.getShortName());
        holder.vehicleName.setText(step.getVehicleName());


    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    // constructor of every single item in the adapter with managed by viewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout stepItem;      // get new recycle view item layout

        TextView stepCount;
        TextView distance;
        TextView travelMode;
        TextView shortName;
        TextView vehicleName;


        public MyViewHolder(View itemView) {
            super(itemView);
            stepCount = itemView.findViewById(R.id.step_item_count);
            distance = itemView.findViewById(R.id.step_item_distance);
            travelMode = itemView.findViewById(R.id.step_item_travel_mode);
            shortName = itemView.findViewById(R.id.step_item_short_name);
            vehicleName = itemView.findViewById(R.id.step_item_vehicle_name);


        }
    }

}
