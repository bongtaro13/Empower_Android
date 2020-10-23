package com.example.empower.ui.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.empower.R;

public class VenueDetailGuideFragment extends Fragment {


    private View root;

    private ImageView gif_guide;
    private TextView description;


    // initialize the news fragment with certain functions
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_venue_search, container, false);

        gif_guide = root.findViewById(R.id.gif_guide);
        description = root.findViewById(R.id.description_guide);

        Glide.with(this).asGif().load(R.raw.venue_detail_router).into(gif_guide);
        description.setText("Get operation by select venue");


        return root;
    }
}
