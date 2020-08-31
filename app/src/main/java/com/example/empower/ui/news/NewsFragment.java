package com.example.empower.ui.news;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;
import com.example.empower.api.NewsAPI;
import com.example.empower.entity.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private View root;

    private EditText newsEditText;
    private Button newsSearchButton;
    private ProgressBar newsProgressBar;

    private static final String TAG = "searchApp";
    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        root = inflater.inflate(R.layout.fragment_news, container, false);

        newsEditText = root.findViewById(R.id.news_keyword_editText);
        newsSearchButton = root.findViewById(R.id.news_searchButton);
        newsProgressBar = root.findViewById(R.id.news_search_progressbar);

        searchNewsWithKeyword("disability sport");

        newsSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchString = newsEditText.getText().toString();
                String txt = "Searching for : " + searchString;
                Log.d(TAG, txt);

                // hide keyboard
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // remove spaces
                String searchStringNoSpaces = searchString.replace(" ", "+");

                searchNewsWithKeyword(searchStringNoSpaces);
            }
        });




        return root;
    }


    public void searchNewsWithKeyword(String searchStringNoSpaces){
        // Your Google API key
        String key="AIzaSyBenJ8IiMcO7vlKFYcZXb9WhKWuTQEJeo4";

        // Your Google Search Engine ID
        String cx = "968c0e899a94db872";

        String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }
        Log.d(TAG, "Url = "+  urlString);


        // start AsyncTask
        GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
        searchTask.execute(url);
    }


    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String>{

        protected void onPreExecute(){

            Log.d(TAG, "AsyncTask - onPreExecute");
            // show mProgressBar
            newsProgressBar.setVisibility(View.VISIBLE);

        }


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

                if(responseCode != null && responseCode == 200) {

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

                }else{

                    // response problem

                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return  result;

                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);

        }

        protected void onPostExecute(String result) {

            Log.d(TAG, "AsyncTask - onPostExecute, result=" + result);
            String testResult = result;

            // hide mProgressBar
            newsProgressBar.setVisibility(View.GONE);


            NewsAPI newsAPI = new NewsAPI(result);

            List<News> newsList = newsAPI.getNewsFromJsonResult();


            //bind new list with view adapter
            NewsAdapter newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), newsList);



            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
            RecyclerView recyclerView = root.findViewById(R.id.news_recyclerView);

            //manage recycle view contents with new adapter
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setLayoutManager(layoutManager);

            // add item decoration among different news item
            recyclerView.addItemDecoration(new NewsItemDecoration(2));


        }


    }





}