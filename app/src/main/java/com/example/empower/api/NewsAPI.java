package com.example.empower.api;


import com.example.empower.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsAPI {

    private String inputNewsSearchResult;

    public NewsAPI(String inputNewsSearchResult) {
        this.inputNewsSearchResult = inputNewsSearchResult;
    }

    public ArrayList<News> getNewsFromJsonResult(){
        ArrayList<News> newsResult = new ArrayList();
        try {
            JSONObject jsonObject_result = new JSONObject(inputNewsSearchResult);
            JSONArray jsonArray_newsItems = jsonObject_result.getJSONArray("items");


            //traverse the JSON array of items
            if (jsonArray_newsItems.length() > 0){
                for (int i = 0; i < jsonArray_newsItems.length(); i++){
                    JSONObject jsonItemObject = jsonArray_newsItems.getJSONObject(i);
                    JSONObject jsonPagemapObject = jsonItemObject.getJSONObject("pagemap");

                    //
                    JSONArray jsonMetaTagsArray = jsonPagemapObject.getJSONArray("metatags");

                    //
                    if (jsonMetaTagsArray.length() > 0) {
                        for (int j = 0; j < jsonMetaTagsArray.length(); j++){
                            JSONObject jsonMetaTagObject = jsonMetaTagsArray.getJSONObject(j);

                            String newsTitle = jsonMetaTagObject.getString("title");
                            String newsImageUrl = jsonMetaTagObject.getString("og:image");
                            String newsUrl = jsonMetaTagObject.getString("og:url");
                            String newsDate = jsonMetaTagObject.getString("article:published_time");

                            News currentNews = new News(newsImageUrl,newsTitle,newsUrl,newsDate);
                            newsResult.add(currentNews);


                        }
                    }



                }
            }


        }catch (JSONException e){
            e.printStackTrace();
        }



        return newsResult;
    }


}
