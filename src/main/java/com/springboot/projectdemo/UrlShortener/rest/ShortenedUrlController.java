package com.springboot.projectdemo.UrlShortener.rest;

import com.springboot.projectdemo.UrlShortener.models.Url;
import com.springboot.projectdemo.UrlShortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ShortenedUrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping(value = "/{url}")
    public ResponseEntity<?> saveOrUpdateUrl(@RequestBody Url url){
        urlService.saveOrUpdateUrl(url);
        return new ResponseEntity<>("Url added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public RedirectView redirectUrl(@RequestParam String id){

        return new RedirectView("");
    }

}
