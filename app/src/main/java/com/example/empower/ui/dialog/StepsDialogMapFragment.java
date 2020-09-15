package com.example.empower.ui.dialog;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;
import com.example.empower.entity.Step;
import java.util.List;
import java.util.Objects;

public class StepsDialogMapFragment extends DialogFragment {
    private static final String TAG = "StepsDialogMapFragment";


    private View root;
    private Button closeButton;
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

        startAddressTextView = root.findViewById(R.id.step_dialog_start_address);
        endAddressTextView = root.findViewById(R.id.step_dialog_end_address);
        totalDistanceTextView = root.findViewById(R.id.step_dialog_total_distance);
        durationTextView = root.findViewById(R.id.step_dialog_duration);


        String startAddress = getArguments().getString("start_address");
        String endAddress = getArguments().getString("end_address");
        String totalDistance = getArguments().getString("total_distance");
        String duration = getArguments().getString("duration");

        startAddressTextView.setText("Start address: " + startAddress);
        endAddressTextView.setText("End address: " + endAddress);
        totalDistanceTextView.setText("Total distance: " + totalDistance);
        durationTextView.setText("Duration: " + duration);


        List<Step> stepList = getArguments().getParcelableArrayList("stepList");


        MyStepsAdapter myStepsAdapter = new MyStepsAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), stepList);

        RecyclerView recyclerView = root.findViewById(R.id.step_recyclerView);

        // manage recycle view contents with map adapter
        recyclerView.setAdapter(myStepsAdapter);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return root;
    }

}
