package com.example.empower.entity;

import com.example.empower.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsWarehouse {
    private static String[] imageUrls = {
            "https://editorial.fxstreet.com/images/Markets/Currencies/Cryptocurrencies/Coins/Ripple/ripple_Large.jpg",
            "https://editorial.fxstreet.com/images/Markets/Currencies/Digital Currencies/Bitcoin/bitcoins-36855764_Large.jpg",
            "https://s3.cointelegraph.com/storage/uploads/view/a413c08e47fd23d7981164ee370d785c.jpg",
            "https://editorial.fxstreet.com/images/Macroeconomics/Events/Coronavirus/coronavirus3_Large.jpg",
            "https://99bitcoins.com/wp-content/uploads/2020/08/This-week-in-Bitcoin-Aug-24-2020.jpg",
            "https://i1.wp.com/dailyfintech.com/wp-content/uploads/2020/08/ethereum-aces-2.png?fit=533%2C533&ssl=1"
    };

    private static String[] titles = {
            "Ripple Technical Analysis: XRP/USD rock-solid support at $0.28 and the impending spike past $0.30",
            "Cryptocurrency News Update: Bitcoin locked below $12,000, Cosmos and Augur are on roller coaster",
            "‘Bitcoin Will Never Ditch You’ Ad Tells HK’s Apple Daily Readers",
            "Forex Today: Markets cheering Trump's plasma push, Sino-American thaw, politics, Fed eyed",
            "Signs of Bullish Crypto Market Expectations\u200C\u200C | Bitcoin News Summary Aug 24, 2020",
            "Ethereum is holding all the aces"
    };



    public static List<News> createNews(int num){
        Random random = new Random();
        List<News> newsList = new ArrayList<>();
        int arySize = imageUrls.length;
        for (int i = 0; i< num; i++){
            int a = i % arySize;
            String url = imageUrls[a];
            String title = titles[a];
            newsList.add(new News(url, title));
        }
        return newsList;
    }
}
