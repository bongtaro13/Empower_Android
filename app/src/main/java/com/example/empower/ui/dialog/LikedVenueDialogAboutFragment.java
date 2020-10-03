package com.example.empower.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.empower.R;
import com.example.empower.entity.Venue;

public class LikedVenueDialogAboutFragment extends DialogFragment {
    private static final String TAG = "LikedVenueDialogAboutFragment";


    private View root;
    private Button closeButton;

    // text view for liked venue demonstration
    private TextView textView_venueID;
    private TextView textView_venueName;
    private TextView textView_venueType;
    private TextView textView_venueAddress;
    private TextView textView_venueSuburb;
    private TextView textView_venuePostcode;
    private TextView textView_venueBusinessCategory;
    private TextView textView_venueLGA;



    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.dialog_likedvenue, container, true);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        closeButton = root.findViewById(R.id.button_close_likedVenue);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click");
                getDialog().dismiss();
            }
        });

        textView_venueID = root.findViewById(R.id.tv_venueID_dialog_likedVenue);
        textView_venueName = root.findViewById(R.id.tv_venueName_dialog_likedVenue);
        textView_venueType = root.findViewById(R.id.tv_venueType_dialog_likedVenue);
        textView_venueAddress = root.findViewById(R.id.tv_venueAddress_dialog_likedVenue);
        textView_venueSuburb = root.findViewById(R.id.tv_venueSuburb_dialog_likedVenue);
        textView_venuePostcode = root.findViewById(R.id.tv_venuePostcode_dialog_likedVenue);
        textView_venueBusinessCategory = root.findViewById(R.id.tv_venueBusinessCategory_dialog_likedVenue);
        textView_venueLGA = root.findViewById(R.id.tv_venueLGAdialog_likedVenue);


        Venue selectLikedVenue = getArguments().getParcelable("selectLikedVenue");

        assert selectLikedVenue != null;
        textView_venueID.setText(selectLikedVenue.getVenueID());
        textView_venueName.setText(selectLikedVenue.getName());
        textView_venueType.setText(selectLikedVenue.getType());
        textView_venueAddress.setText(selectLikedVenue.getAddress());
        textView_venueSuburb.setText(selectLikedVenue.getSuburb());
        textView_venuePostcode.setText(selectLikedVenue.getPostcode());
        textView_venueBusinessCategory.setText(selectLikedVenue.getBusinessCategory().replace(","," "));
        textView_venueLGA.setText(selectLikedVenue.getLga());



        return root;

    }


}
