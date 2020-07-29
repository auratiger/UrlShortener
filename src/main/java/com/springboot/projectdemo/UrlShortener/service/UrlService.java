package com.springboot.projectdemo.UrlShortener.service;

import com.springboot.projectdemo.UrlShortener.models.Url;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<Url> findAll();
    Optional<Url> findBySlug(String slug);
    void saveOrUpdateUrl(Url url);
    void deleteShort(String id);
    long deleteUrlBySlug(String slug);
    boolean existsUrlBySlug(String slug);
}
