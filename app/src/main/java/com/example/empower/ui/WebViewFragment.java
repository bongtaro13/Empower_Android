package com.example.empower.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.empower.R;

public class WebViewFragment extends Fragment {
    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub


        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        webview = (WebView) view.findViewById(R.id.web_fragment_form_report);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);


        //支持缩放
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//设定支持缩放

        // open website
        webview.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdtNt9-3_nZlKA4ZjP0uaClP6-B1Ok9EoU-qlcYuS0oOxWWkw/viewform");
    }
}
