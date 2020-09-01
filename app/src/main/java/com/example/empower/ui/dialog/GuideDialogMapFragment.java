package com.example.empower.ui.dialog;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import androidx.fragment.app.DialogFragment;

import com.example.empower.R;
import com.example.empower.ui.map.MapFragment;


public class GuideDialogMapFragment extends DialogFragment {

    private static final String TAG = "GuideDialogFragment";

    private ImageView guideImage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_item, container, false);


        guideImage = view.findViewById(R.id.dialog_guidence_image);
        guideImage.setImageResource(R.drawable.map_guide);


        guideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click");
                getDialog().dismiss();
            }
        });




        return view;
    }




}
