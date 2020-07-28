package com.springboot.projectdemo.UrlShortener.service;

import com.springboot.projectdemo.UrlShortener.Repository.UrlRepository;
import com.springboot.projectdemo.UrlShortener.models.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public List<Url> findAll() {
        return urlRepository.findAll();
    }

    @Override
    public Optional<Url> findBySlug(String slug) {
        return urlRepository.findBySlug(slug);
    }

    @Override
    public void saveOrUpdateUrl(Url url) {
        urlRepository.save(url);
    }

    @Override
    public void deleteShort(String id) {
        urlRepository.deleteById(id);
    }

    @Override
    public boolean existsUrlBySlug(String slug) {
        return urlRepository.existsUrlBySlug(slug);
    }
}
