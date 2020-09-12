package com.example.empower.entity;

public class News {
    private String imageUrl;
    private String title;
    private String newsUrl;
    private String newsDate;

    public News(String imageUrl, String title, String newsUrl, String newsDate) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.newsUrl = newsUrl;
        this.newsDate = newsDate;
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

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}
