package com.springboot.projectdemo.UrlShortener.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Url {

    @Id
    private String id;

    @Indexed(unique = true)
    private String slug;
    private String url;

    private long timestamp;

    public Url(String slug, String url) {
        this.slug = slug;
        this.url = url;
        this.timestamp = new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{ " +
                "\"slug\":\"" + slug + '\"' +
                ", \"url\":\"" + url + '\"' +
                " }";
    }
}
