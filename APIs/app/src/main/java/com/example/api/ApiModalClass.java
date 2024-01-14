package com.example.api;

public class ApiModalClass {

    String apiCategory;
    String apiLink;
    String apiName;

    public ApiModalClass(String apiName, String apiCategory, String apiLink) {
        this.apiName = apiName;
        this.apiCategory = apiCategory;
        this.apiLink = apiLink;
    }

    public String getApiName() {
        return this.apiName;
    }

    public String getApiLink() {
        return this.apiLink;
    }

    public String getApiCategory() {
        return this.apiCategory;
    }
}
