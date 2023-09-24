package com.example.news;

public class Article {
    private String title;
    private String description;
    private String content;
    private String urlToImage;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Article(String title, String description, String content, String urlToImage, String url) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.urlToImage = urlToImage;
        this.url = url;
    }
}
