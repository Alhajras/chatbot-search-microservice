package com.chatbot.springbootchatbot.elasticsearch.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Article {

    private String id;
    private String url;
    private String data;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}

