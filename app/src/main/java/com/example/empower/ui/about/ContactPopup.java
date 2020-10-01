package com.example.empower.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.empower.R;
import com.example.empower.ui.WebDetailActivity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.net.URL;
import java.util.Objects;

public class ContactPopup extends BottomPopupView {

    private Button contactButton;
    private TextView contactDetail;

    public ContactPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.contact_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        contactButton = findViewById(R.id.report_button);
        contactDetail = findViewById(R.id.contact_detail_textView);

        contactDetail.setText("Please contact us with email: \n empower@gmail.com");

        contactButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String url = "https://docs.google.com/forms/d/e/1FAIpQLSdtNt9-3_nZlKA4ZjP0uaClP6-B1Ok9EoU-qlcYuS0oOxWWkw/viewform";
                bundle.putString("URL",url);
                Intent intent = new Intent();
                intent.setClass(Objects.requireNonNull(v.getContext()),WebDetailActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent, bundle);
            }
        });

    }


    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "contact us onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "contact us onDismiss");

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .9f);
    }

}
