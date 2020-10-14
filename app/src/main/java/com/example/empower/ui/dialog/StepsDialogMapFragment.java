package com.example.empower.ui.dialog;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;
import com.example.empower.entity.Step;

import org.w3c.dom.Text;

import java.util.List;


/**
 * class name: StepsDialogMapFragment.java
 * function: Main aim of this dialog fragment is to display step information of a real-time router planner
 */
public class StepsDialogMapFragment extends DialogFragment {
    private static final String TAG = "StepsDialogMapFragment";


    private View root;
    private Button closeButton;

    private TextView venueNameTextView;

    private TextView startAddressTextView;
    private TextView endAddressTextView;
    private TextView totalDistanceTextView;
    private TextView durationTextView;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.dialog_steps, container, true);

        closeButton = root.findViewById(R.id.step_dialog_close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click");
                getDialog().dismiss();
            }
        });

        venueNameTextView = root.findViewById(R.id.step_venueName);

        startAddressTextView = root.findViewById(R.id.step_dialog_start_address);
        endAddressTextView = root.findViewById(R.id.step_dialog_end_address);
        totalDistanceTextView = root.findViewById(R.id.step_dialog_total_distance);
        durationTextView = root.findViewById(R.id.step_dialog_duration);


        String venueName = getArguments().getString("venue_name");

        String startAddress = getArguments().getString("start_address");
        String endAddress = getArguments().getString("end_address");
        String totalDistance = getArguments().getString("total_distance");
        String duration = getArguments().getString("duration");


        Typeface typeface_mon = getResources().getFont(R.font.montserrat_bold);
        venueNameTextView.setTypeface(typeface_mon);
        venueNameTextView.setText(venueName);

        startAddressTextView.setText("Start address: \n" + startAddress);
        endAddressTextView.setText("End address: \n" + endAddress);
        totalDistanceTextView.setText("Total distance: " + totalDistance);
        durationTextView.setText("Duration: " + duration);


        List<Step> stepList = getArguments().getParcelableArrayList("stepList");

        MyStepsAdapter myStepsAdapter = new MyStepsAdapter(getContext(), stepList);

        RecyclerView recyclerView = root.findViewById(R.id.step_recyclerView);

        // manage recycle view contents with map adapter
        recyclerView.setAdapter(myStepsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return root;
    }

}
