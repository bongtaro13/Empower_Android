package com.example.empower.entity;

public class News {
    private String imageUrl;
    private String title;
    private String newsUrl;


    public News(String imageUrl, String title, String newsUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.newsUrl = newsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
