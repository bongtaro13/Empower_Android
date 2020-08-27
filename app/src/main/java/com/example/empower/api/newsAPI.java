package com.example.empower.api;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class newsAPI {
    private static final String news_api_key = "f7ca0d43fdbf4400a3a93674add42a47";

    public static String newsSearch(String  inputString){
        inputString = inputString.trim().replace(" ", "%20");
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String textResult = "";


        try {
            url = new URL("http://newsapi.org/v2/everything?q=" + inputString +"&from=2020-07-27&sortBy=publishedAt&apiKey=" + news_api_key);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");


            Scanner inputStream = new Scanner(httpURLConnection.getInputStream());
            while (inputStream.hasNextLine()){
                textResult += inputStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }

        return textResult;
    }


//    public static List<String> getNewsResultPortion(String textResult){
//        List<>
//
//    }
}
