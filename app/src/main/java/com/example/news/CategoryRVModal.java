package com.example.news;

public class CategoryRVModal {
    private String category;
    private String categoryUrl;

//    public String getCategoryUrl() {
//        return categoryUrl;
//    }
//
//    public void setCategoryUrl(String categoryUrl) {
//        this.categoryUrl = categoryUrl;
//    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CategoryRVModal(String category) {
        this.category = category;
//        this.categoryUrl = categoryUrl;
    }
}
