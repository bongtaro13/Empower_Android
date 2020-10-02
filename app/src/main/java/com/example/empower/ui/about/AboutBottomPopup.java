package com.example.empower.ui.about;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.empower.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

public class AboutBottomPopup extends BottomPopupView {

    public AboutBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.about_popup;
    }


    ViewPager about_pager;

    @Override
    protected void onCreate() {
        super.onCreate();
        about_pager = findViewById(R.id.about_pager);
        about_pager.setAdapter(new PAdapter());
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.85f);
    }

    class PAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position){
            NestedScrollView scrollView = new NestedScrollView(container.getContext());
            LinearLayout linearLayout = new LinearLayout(container.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(linearLayout);
            TextView textView = new TextView(container.getContext());
            textView.setPadding(40,40,40,40);
            textView.setText("container test");
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(R.mipmap.logo_empower);
            linearLayout.addView(imageView);
            container.addView(scrollView);


            return scrollView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }

    }

}
