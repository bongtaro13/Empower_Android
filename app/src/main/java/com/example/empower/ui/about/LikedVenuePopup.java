package com.example.empower.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.empower.R;
import com.example.empower.entity.Venue;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import static android.content.ContentValues.TAG;
import static com.example.empower.ui.dialog.LikedVenueDialogAboutFragment.sendMail;
import static com.example.empower.ui.dialog.LikedVenueDialogAboutFragment.sendSms;


public class LikedVenuePopup extends BottomPopupView {


    private Bundle venueInformation;


    // components ont the liked venue detail info window
    private ImageView venueImage;
    private TextView venueID;
    private TextView venueName;
    private TextView venueType;
    private TextView venueAddress;
    private TextView venueSuburb;
    private TextView venueBusinessCategory;
    private TextView venueLga;


    private ImageView shareByMessage;
    private ImageView shareByEmail;

    private Button closeVenueDetail;





    public LikedVenuePopup(@NonNull Context context, Bundle venueInfoBundle) {

        super(context);
        venueInformation = venueInfoBundle;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.likedvenue_popup;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        venueImage = findViewById(R.id.venuePicture);

        venueID = findViewById(R.id.venueID_detail_textView);
        venueName = findViewById(R.id.venueName_detail_textView);
        venueType = findViewById(R.id.venueType_detail_textView);
        venueAddress = findViewById(R.id.venueAddress_detail_textView);
        venueSuburb = findViewById(R.id.suburb_detail_textView);
        venueBusinessCategory = findViewById(R.id.businessCategory_detail_textView);
        venueLga = findViewById(R.id.LGA_detail_textView);


        shareByMessage = findViewById(R.id.share_message_detail_textView);
        shareByEmail = findViewById(R.id.share_email_detail_textView);

        closeVenueDetail = findViewById(R.id.close_likedVenueDetail_button);

        closeVenueDetail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        Venue selectLikedVenue = venueInformation.getParcelable("selectLikedVenue");
        if (selectLikedVenue != null) {
            venueID.setText(selectLikedVenue.getVenueID());
            venueName.setText(selectLikedVenue.getName());
            venueType.setText(selectLikedVenue.getType());
            venueAddress.setText(selectLikedVenue.getAddress());
            venueSuburb.setText(selectLikedVenue.getSuburb() + " " + selectLikedVenue.getPostcode());
            venueBusinessCategory.setText(selectLikedVenue.getBusinessCategory().replace(","," "));
            venueLga.setText(selectLikedVenue.getLga());
        }


        shareByMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "message share: click");
                sendSms(getContext(), "Hi, I found a sport venue in " + selectLikedVenue.getAddress());
            }
        });


        shareByEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "email share: click");
                sendMail(getContext(), "Venue share", "Hi, I found a sport venue in " + selectLikedVenue.getAddress());
            }
        });

    }


    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "liked venue detail onShow");

    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "liked venue detail onDismiss");



    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .9f);
    }


}
