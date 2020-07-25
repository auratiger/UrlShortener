package com.springboot.projectdemo.UrlShortener.service;

import com.springboot.projectdemo.UrlShortener.models.Url;

import java.util.List;

public interface UrlService {
    List<Url> findAll();
    Url findBySlug(String slug);
    void saveOrUpdateUrl(Url url);
    void deleteShort(String id);
    boolean existsUrlBySlug(String slug);
}
