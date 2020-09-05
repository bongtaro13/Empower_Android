package com.example.empower.ui.about;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.empower.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private Button feedbackButton;
    private WebView webView;
    private TextView contactTextview;
    private TextView firestoreTextview;

    private View root;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference sportsVenuesDF = db.collection("sportsVenues").document("sportsVenues35");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        root = inflater.inflate(R.layout.fragment_about, container, false);

        contactTextview = root.findViewById(R.id.about_contactdetail_textView);
        feedbackButton = root.findViewById(R.id.about_feedback_button);
        firestoreTextview = root.findViewById(R.id.about_firestore_test);


        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {

                webView = (WebView) root.findViewById(R.id.about_web_view);
                webView.getSettings().setJavaScriptEnabled(true);
                //load WebView
                webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdtNt9-3_nZlKA4ZjP0uaClP6-B1Ok9EoU-qlcYuS0oOxWWkw/viewform");
                feedbackButton.setVisibility(View.GONE);
                contactTextview.setVisibility(View.GONE);
                webView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN){
                            if (keyCode == KeyEvent.KEYCODE_BACK){
                                webView.goBack();
                                return true;

                            }

                        }
                        return false;
                    }
                });

            }
        });


        //fireStore text test
        sportsVenuesDF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String getString = documentSnapshot.getString("Address");
                    firestoreTextview.setText(getString);

                }
            }
        });


        return root;
    }



}