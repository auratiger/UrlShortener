package com.springboot.projectdemo.UrlShortener.service;

import com.mongodb.MongoWriteException;
import com.springboot.projectdemo.UrlShortener.Repository.UrlRepository;
import com.springboot.projectdemo.UrlShortener.models.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    public boolean saveOrUpdateUrl(Url url) {
        try {
            urlRepository.save(url);
        }catch (DuplicateKeyException | MongoWriteException e){
//            e.printStackTrace();
            //TODO: Log to file
            return false;
        }
        return true;
    }

    @Override
    public void deleteShort(String id) {
        urlRepository.deleteById(id);
    }

    @Override
    public boolean existsUrlBySlug(String slug) {
        return urlRepository.existsUrlBySlug(slug);
    }

    @Override
    public long deleteUrlBySlug(String slug) {
        return urlRepository.deleteUrlBySlug(slug);
    }
}
