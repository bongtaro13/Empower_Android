package com.example.empower.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.empower.R;
import com.example.empower.entity.Step;

import java.util.List;



/**
 * class name: MyStepsAdapter
 * function: manage the recycle with each item, integrated every single item to the step list
 * */


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
        int count = position + 1;


        if (step.getTravelMode().equals("WALKING")){
            holder.typeImage.setImageResource(R.drawable.ic_walk_24dp);
        }else {

            if (step.getVehicleName().equals("Train")) {
                holder.typeImage.setImageResource(R.drawable.ic_train_24dp);
            } else if (step.getVehicleName().equals("Bus")) {
                holder.typeImage.setImageResource(R.drawable.ic_bus_24dp);
            }else if (step.getVehicleName().equals("Tram")){
                holder.typeImage.setImageResource(R.drawable.ic_tram_24dp);
            }
        }


        holder.stepCount.setText("Step: " + count);
        holder.distance.setText("Distance: " +step.getDistance());
        holder.travelMode.setText("Travel mode: " + step.getTravelMode());
        if (!step.getTravelMode().equals("WALKING")) {
            holder.vehicleName.setText("Vehilce type: " +step.getVehicleName());
            holder.shortName.setText("Vehilce number: " + step.getShortName());
        }

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    // constructor of every single item in the adapter with managed by viewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout stepItem;      // get new recycle view item layout


        ImageView typeImage;
        TextView stepCount;
        TextView distance;
        TextView travelMode;
        TextView shortName;
        TextView vehicleName;

        public MyViewHolder(View itemView) {
            super(itemView);
            typeImage = itemView.findViewById(R.id.step_tranpost_icon);
            stepCount = itemView.findViewById(R.id.step_item_count);
            distance = itemView.findViewById(R.id.step_item_distance);
            travelMode = itemView.findViewById(R.id.step_item_travel_mode);
            shortName = itemView.findViewById(R.id.step_item_short_name);
            vehicleName = itemView.findViewById(R.id.step_item_vehicle_name);


        }
    }

}
