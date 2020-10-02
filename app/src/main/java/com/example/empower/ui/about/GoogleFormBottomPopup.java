package com.example.empower.ui.about;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.example.empower.R;
import com.lxj.xpopup.core.BottomPopupView;

public class GoogleFormBottomPopup extends BottomPopupView {

    private WebView webView;

    public GoogleFormBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.google_form_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onShow() {
        super.onShow();
        webView = findViewById(R.id.web_fragment_form_popup);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdtNt9-3_nZlKA4ZjP0uaClP6-B1Ok9EoU-qlcYuS0oOxWWkw/viewform");

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

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "CustomEditTextBottomPopup  onDismiss");
    }



//    @Override
//    protected int getMaxHeight() {
//        return (int) (XPopupUtils.getWindowHeight(getContext())*0.75);
//    }
}