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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.empower.R;
import com.example.empower.database.LikedVenue;
import com.example.empower.ui.news.NewsViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private Button feedbackButton;
    private WebView webView;
    private TextView contactTextview;

    private ArrayList<String> dataList;

    private SwipeRecyclerView mRecyclerView;


    private AboutViewModel aboutViewModel;

    private View root;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        root = inflater.inflate(R.layout.fragment_about, container, false);

        contactTextview = root.findViewById(R.id.about_contactdetail_textView);
        feedbackButton = root.findViewById(R.id.about_feedback_button);

        mRecyclerView = root.findViewById(R.id.favourite_venue_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(
                new DefaultItemDecoration(ContextCompat.getColor(getContext(), R.color.blue_background)));

        mRecyclerView.setOnItemClickListener(mItemClickListener);
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);



        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        aboutViewModel.initalizeVars(getActivity().getApplication());


        // initialize the LikedVenue list with sample example
        LikedVenue one = new LikedVenue("1","venue one", "3168");
        LikedVenue two = new LikedVenue("2","venue two", "3056");
        aboutViewModel.insert(one);
        aboutViewModel.insert(two);

        MainAdapter menuAdapter = new MainAdapter(getContext());
        mRecyclerView.setAdapter(menuAdapter);

        // get LikedVenue object from
        aboutViewModel.getAllLikedVenues().observe(this, new Observer<List<LikedVenue>>() {
            @Override
            public void onChanged(List<LikedVenue> likedVenues) {
                dataList = new ArrayList<>();
                for (LikedVenue tempLikedVenue: likedVenues){
                    dataList.add(tempLikedVenue.toString());
                }
                menuAdapter.notifyDataSetChanged(dataList);
            }

        });




//        dataList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            dataList.add("我是第" + i + "个。");
//        }



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


        return root;
    }




    /**
     * RecyclerView的Item点击监听。
     */
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Toast.makeText(getContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = getResources().getDimensionPixelSize(R.dimen.dp_50);

            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.selector_green)
                    .setImage(R.drawable.football)
                    .setWidth(width)
                    .setHeight(height);
            swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

            SwipeMenuItem closeItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.selector_red)
                    .setImage(R.drawable.ic_delete_forever)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。
        }
    };

    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };



}