//package com.example.empower.ui.dialog;
//
//import android.app.Dialog;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.DialogFragment;
//
//
//
//import com.google.android.gms.maps.StreetViewPanoramaOptions;
//import com.google.android.gms.maps.StreetViewPanoramaView;
//import com.google.android.gms.maps.model.LatLng;
//
//import java.util.Objects;
//
//
//public  class StreetviewDialogMapFragment extends DialogFragment {
//
//    LatLng latLng;
//
//    static StrretViewDialogFragment newInstance(LatLng latLng) {
//        StrretViewDialogFragment f = new StrretViewDialogFragment();
//
//        Bundle args = new Bundle();
//        args.putDouble("latitude", latLng.latitude);
//        args.putDouble("longitude", latLng.longitude);
//        f.setArguments(args);
//
//        return f;
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        latLng = new LatLng(getArguments().getDouble("latitude"),
//                getArguments().getDouble("longitude"));
//
//        LinearLayout layout = new LinearLayout(getActivity());
//        layout.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
//        options.position(latLng);
//        StreetViewPanoramaView streetViewPanoramaView =
//                new StreetViewPanoramaView(getActivity(), options);
//        streetViewPanoramaView.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT));
//        streetViewPanoramaView.setPadding(5, 5, 5, 5);
//        streetViewPanoramaView.onCreate(savedInstanceState);
//
//        layout.addView(streetViewPanoramaView);
//
//        return new AlertDialog.Builder(getActivity())
//                .setTitle(latLng.latitude + " : " + latLng.longitude)
//                .setPositiveButton("OK", null)
//                .setView(layout)
//                .create();
//    }
//}
//
