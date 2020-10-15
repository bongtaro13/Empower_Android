package com.example.empower.ui.about;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;
import com.example.empower.database.LikedVenue;
import com.example.empower.entity.Venue;
import com.example.empower.ui.dialog.LikedVenueDialogAboutFragment;
import com.example.empower.ui.dialog.StepsDialogMapFragment;
import com.example.empower.ui.map.VenueFilter;
import com.example.empower.ui.news.NewsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lxj.xpopup.XPopup;
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
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class AboutFragment extends Fragment {

    private ArrayList<Venue> venueArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private ArrayList<String> dataList;

    private SwipeRecyclerView mRecyclerView;

    private TextView defaultTextview;


    private AboutViewModel aboutViewModel;

    private View root;

    private MainAdapter menuAdapter;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        venueArrayList = new ArrayList<>();
        getSportsListFromFireStore();

        root = inflater.inflate(R.layout.fragment_about, container, false);

        defaultTextview= root.findViewById(R.id.default_textView);

        mRecyclerView = root.findViewById(R.id.favourite_venue_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(
                new DefaultItemDecoration(ContextCompat.getColor(getContext(), R.color.blue_background)));

        mRecyclerView.setOnItemClickListener(mItemClickListener);
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);


        mRecyclerView.setItemViewSwipeEnabled(false);


        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        aboutViewModel.initalizeVars(getActivity().getApplication());


        // initialize the LikedVenue list with sample example
//        LikedVenue one = new LikedVenue("1","venue one", "3168");
//        LikedVenue two = new LikedVenue("2","venue two", "3056");
//        aboutViewModel.insert(one);
//        aboutViewModel.insert(two);

        menuAdapter = new MainAdapter(getContext());
        mRecyclerView.setAdapter(menuAdapter);

        // get LikedVenue object from
        aboutViewModel.getAllLikedVenues().observe(this, new Observer<List<LikedVenue>>() {
            @Override
            public void onChanged(List<LikedVenue> likedVenues) {
                dataList = new ArrayList<>();
                for (LikedVenue tempLikedVenue : likedVenues) {
                    dataList.add(tempLikedVenue.toString());
                }
                if (dataList.isEmpty()){
                    defaultTextview.setVisibility(View.VISIBLE);
                }else {
                    defaultTextview.setVisibility(View.INVISIBLE);
                }
                menuAdapter.notifyDataSetChanged(dataList);
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
            //Toast.makeText(getContext(), "Index " + position, Toast.LENGTH_SHORT).show();



            String[] totalString = dataList.get(position).split(";");
            String selectedLikedVenueID = totalString[0].replace("venueID=", "");

            VenueFilter venueFilter = new VenueFilter();
            Venue foundVenue = venueFilter.findVenueByVenueId(selectedLikedVenueID, venueArrayList);

            if (foundVenue != null) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("selectLikedVenue",foundVenue);

                // display the liked venue details in bottom popup window
                new XPopup.Builder(getContext())
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                        .asCustom(new LikedVenuePopup(getContext(), bundle)/*.enableDrag(false)*/)
                        .show();
            }

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
            int height = getResources().getDimensionPixelSize(R.dimen.dp_80);

            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.selector_green)
                    .setImage(R.drawable.ic_map_black_24dp)
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
                Toast.makeText(getContext(), "list index" + position + "; delete clicked " + menuPosition, Toast.LENGTH_SHORT).show();

                String[] totalString = dataList.get(position).split(";");


                String selectedLikedVenueID = totalString[0].replace("venueID=", "");
                String selectedLikedVenueName = totalString[1].replace("name=", "");
                String selectedLikedVenuePostcode = totalString[2].replace("postcode=", "");

                LikedVenue choosenLikedVenue = new LikedVenue(selectedLikedVenueID, selectedLikedVenueName, selectedLikedVenuePostcode);
                aboutViewModel.delete(choosenLikedVenue);
                dataList.remove(position);
                if (dataList.isEmpty()){
                    defaultTextview.setVisibility(View.VISIBLE);
                }else {
                    defaultTextview.setVisibility(View.INVISIBLE);
                }
                menuAdapter.notifyDataSetChanged(dataList);

            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getContext(), "list index " + position + "; left menu " + menuPosition, Toast.LENGTH_SHORT).show();

            }
        }
    };

    // get the sports venues info from the Cloud FireStore NoSql database
    private void getSportsListFromFireStore() {
        db.collection("sportsVenues")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Map<String, Object> tempSportsVenueMap = document.getData();
                                String venueID = Objects.requireNonNull(tempSportsVenueMap.get("venueID")).toString();
                                String type = Objects.requireNonNull(tempSportsVenueMap.get("type")).toString();
                                String name = Objects.requireNonNull(tempSportsVenueMap.get("name")).toString();
                                String address = Objects.requireNonNull(tempSportsVenueMap.get("address")).toString();
                                String suburb = Objects.requireNonNull(tempSportsVenueMap.get("suburb")).toString();
                                String postcode = Objects.requireNonNull(tempSportsVenueMap.get("postcode")).toString();
                                String businessCategory = Objects.requireNonNull(tempSportsVenueMap.get("businessCategory")).toString();
                                String lga = Objects.requireNonNull(tempSportsVenueMap.get("lga")).toString();
                                String latitude = Objects.requireNonNull(tempSportsVenueMap.get("latitude")).toString();
                                String longitude = Objects.requireNonNull(tempSportsVenueMap.get("longitude")).toString();

                                venueArrayList.add(new Venue(venueID, type, name, address, suburb, postcode, businessCategory, lga, latitude, longitude));


                            }
                        } else {
                            Log.d(TAG, "Error getting sportsVenues documents: " + task.getException());
                        }
                    }
                });
    }




}