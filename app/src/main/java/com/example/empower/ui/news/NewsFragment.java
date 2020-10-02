package com.example.empower.ui.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.MainActivity;
import com.example.empower.R;
import com.example.empower.api.NewsAPI;
import com.example.empower.entity.News;
import com.example.empower.ui.dialog.GuideDialogNewsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * class name: NewsFragment.java
 * function: main aim of this class is to build and list news with attaching with MainActivity class
 */

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private View root;

    // visual component on the news page
    private Spinner newsSpinner;
    private List<String> newsSpinnerDataList;
    private ArrayAdapter<String> newsSpinnerAdapter;


    private Button podcastButton;
    private ProgressBar newsProgressBar;

    private static final String TAG = "searchApp";

    // search field about news
    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";


    // initialize the news fragment with certain functions
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        root = inflater.inflate(R.layout.fragment_news, container, false);

        // initialize three visual components
        newsSpinner = root.findViewById(R.id.news_Spinner);
        //podcastButton = root.findViewById(R.id.podcast_Button);
        newsProgressBar = root.findViewById(R.id.news_search_progressbar);


        // listen the news spinner on the page, if button has been clicked, get info from the input and search the related news
        newsSpinnerDataList = new ArrayList<>();
        newsSpinnerDataList.add("Paralympic");
        newsSpinnerDataList.add("Football");
        newsSpinnerDataList.add("Basketball");
        newsSpinnerDataList.add("Cricket");

        newsSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, newsSpinnerDataList);
        newsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newsSpinner.setAdapter(newsSpinnerAdapter);

        // add listener to the spinner selected action
        newsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast toast_news_sport = Toast.makeText(getActivity(), "you selected sport is: " + newsSpinnerAdapter.getItem(position), Toast.LENGTH_SHORT);
                toast_news_sport.setGravity(Gravity.TOP | Gravity.CENTER, 0, 400);
                toast_news_sport.show();
                String searchString = "disability+" + newsSpinnerAdapter.getItem(position);
                searchNewsWithKeyword(searchString);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return root;
    }


    // search news with input keyword, google custom engine key and google search API for Android included
    public void searchNewsWithKeyword(String searchStringNoSpaces) {
        // Your Google API key
        String key = "AIzaSyBenJ8IiMcO7vlKFYcZXb9WhKWuTQEJeo4";

        // Your Google Search Engine ID
        String cx = "968c0e899a94db872";

        String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }
        Log.d(TAG, "Url = " + urlString);


        // start AsyncTask
        GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
        searchTask.execute(url);
    }

    // use asynchronous to search teh result with keyword and display with recycle view
    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {

            Log.d(TAG, "AsyncTask - onPreExecute");
            // show mProgressBar
            newsProgressBar.setVisibility(View.VISIBLE);

        }

        // do in background with get news result from RESTful API supported by google search
        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }


            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());

            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if (responseCode != null && responseCode == 200) {

                    // response OK

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    rd.close();

                    conn.disconnect();

                    result = sb.toString();

                    Log.d(TAG, "result=" + result);

                    return result;

                } else {

                    // response problem

                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);

        }

        // after getting result from the RESTful API, format the result and displayed in recycle view list
        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);
            String testResult = result;

            // hide mProgressBar
            newsProgressBar.setVisibility(View.GONE);


            NewsAPI newsAPI = new NewsAPI(result);

            final List<News> newsList = newsAPI.getNewsFromJsonResult();


            //bind new list with view adapter
            MyNewsAdapter myNewsAdapter = new MyNewsAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), newsList);


            RecyclerView recyclerView = root.findViewById(R.id.news_recyclerView);

            //manage recycle view contents with new adapter
            recyclerView.setAdapter(myNewsAdapter);


            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            // listen the specific item of the new list, if it has been cliked, jump to the browser with mapping URL
            myNewsAdapter.setOnItemClickListener(new MyNewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(newsList.get(position).getNewsUrl());
                    intent.setData(content_url);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Clicked news: " + position + 1, Toast.LENGTH_SHORT).show();
                }
            });


            recyclerView.setLayoutManager(layoutManager);

            // add item decoration among different news item
            recyclerView.addItemDecoration(new NewsItemDecoration(2));


        }


    }


}