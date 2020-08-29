package com.example.empower.ui.about;

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
            @Override
            public void onClick(View v) {


                init();
                feedbackButton.setVisibility(root.GONE);
            }
        });


        return root;
    }


    private void init() {
        webView = (WebView) root.findViewById(R.id.about_web_view);
        //load WebView
        webView.loadUrl("https://stackoverflow.com/questions/27709238/edittext-change-border-color-with-shape-xml/27709426");
    }
}