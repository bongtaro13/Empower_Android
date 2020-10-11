//package com.example.empower.ui.map;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.example.empower.R;
//import com.lxj.xpopup.core.BottomPopupView;
//import com.lxj.xpopup.util.XPopupUtils;
//
//import org.w3c.dom.Text;
//
//public class VenueDetailPopup extends BottomPopupView {
//
//
//    private TextView venueID;
//    private TextView venueName;
//    private TextView venueAddress;
//    private TextView venuePostcode;
//    private TextView venueSuburb;
//    private TextView venueBusinessCategory;
//
//
//    private String latitude;
//    private String longitude;
//
//
//
//    private Button streetViewButton;
//    private Button routerPlannerButton;
//
//    private Bundle venueDetail;
//
//
//    public VenueDetailPopup(@NonNull Context context, Bundle venueInformation) {
//        super(context);
//        venueDetail = venueInformation;
//    }
//
//    @Override
//    protected int getImplLayoutId() {
//        // TODO: replace this with new popup layout
//        return R.layout.contact_popup;
//    }
//
//    @Override
//    protected void onCreate() {
//        super.onCreate();
//
//
//
//        venueID = findViewById();
//        venueName = findViewById();
//        venueAddress = findViewById();
//        venuePostcode = findViewById();
//        venueSuburb = findViewById();
//        venueBusinessCategory = findViewById();
//
//
//        streetViewButton = findViewById();
//        routerPlannerButton = findViewById();
//
//        streetViewButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        routerPlannerButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//
//
//    }
//
//
//    //完全可见执行
//    @Override
//    protected void onShow() {
//        super.onShow();
//        Log.e("tag", "venue details onShow");
//
//    }
//
//    //完全消失执行
//    @Override
//    protected void onDismiss() {
//        Log.e("tag", "venue details onDismiss");
//
//    }
//
//    @Override
//    protected int getMaxHeight() {
////        return XPopupUtils.getWindowHeight(getContext());
//        return (int) (XPopupUtils.getWindowHeight(getContext()) * .9f);
//    }
//}
//
//
