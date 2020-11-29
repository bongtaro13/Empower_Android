package com.example.empower.ui.about;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.empower.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPopupUtils;

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

        contactDetail.setText("Please contact us with email: \n empower.ma5@gmail.com");

        contactButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final GoogleFormBottomPopup textBottomPopup = new GoogleFormBottomPopup(getContext());
                new XPopup.Builder(getContext())
                        .autoOpenSoftInput(true)
                        .maxHeight((int) (XPopupUtils.getWindowHeight(getContext()) * 0.9))
//                        .hasShadowBg(false)
                        .setPopupCallback(new SimpleCallback() {
                            @Override
                            public void onShow(BasePopupView popupView) {
                            }

                            @Override
                            public void onDismiss(BasePopupView popupView) {

                            }
                        })
                        .asCustom(textBottomPopup)
                        .show();
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
