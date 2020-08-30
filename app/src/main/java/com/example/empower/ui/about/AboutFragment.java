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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.empower.R;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private Button feedbackButton;
    private WebView webView;

    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        root = inflater.inflate(R.layout.fragment_about, container, false);

        feedbackButton = root.findViewById(R.id.about_feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {

                webView = (WebView) root.findViewById(R.id.about_web_view);
                webView.getSettings().setJavaScriptEnabled(true);
                //load WebView
                webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdtNt9-3_nZlKA4ZjP0uaClP6-B1Ok9EoU-qlcYuS0oOxWWkw/viewform");
                feedbackButton.setVisibility(View.GONE);

            }
        });


        return root;
    }

}