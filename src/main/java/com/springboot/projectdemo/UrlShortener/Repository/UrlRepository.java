package com.springboot.projectdemo.UrlShortener.Repository;

import com.springboot.projectdemo.UrlShortener.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {
    Url findBySlug(String slug);
}
