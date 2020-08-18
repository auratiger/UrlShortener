package com.springboot.projectdemo.UrlShortener.Repository;

import com.springboot.projectdemo.UrlShortener.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findBySlug(String slug);
    boolean existsUrlBySlug(String slug);
    long deleteUrlBySlug(String slug);

}
