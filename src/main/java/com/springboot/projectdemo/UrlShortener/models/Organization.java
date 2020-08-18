package com.springboot.projectdemo.UrlShortener.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Organization {

    @Id
    private String id;

    @Indexed(unique = true)
    private String namespace;

    @Indexed(unique = true)
    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;
    private long timestamp;

    private List<Url> urls;

    public Organization(String namespace, String name, String email, String password) {
        this.namespace = namespace;
        this.name = name;
        this.email = email;
        this.password = password;
        this.timestamp = new Date().getTime();
        this.urls = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addUrl(Url url) {
        if (url.getSlug().trim().equals("")){
            throw new IllegalArgumentException();
        }
        urls.add(url);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return timestamp == that.timestamp &&
                Objects.equals(id, that.id) &&
                Objects.equals(namespace, that.namespace) &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namespace, name, email, timestamp);
    }

    public String toJson(){
        return "{ " +
                "\"name\":\"" + name + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"namespace\":\"" + namespace + '\"' +
                " }";
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", namespace='" + namespace + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
