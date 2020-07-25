package com.springboot.projectdemo.UrlShortener.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
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

    @PostMapping(value = "/url")
    public ResponseEntity<?> saveOrUpdateUrl(@RequestBody Url url){
        //Throw Error if the url is the same as the domain
        String slug = url.getSlug();

        if(slug == null){
            do {
                url.setSlug(NanoIdUtils.randomNanoId(
                        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                        NanoIdUtils.DEFAULT_ALPHABET,
                        5));
            }while(urlService.existsUrlBySlug(url.getSlug()));

            urlService.saveOrUpdateUrl(url);
        }else{
            if(urlService.existsUrlBySlug(slug)){
//                throw new IllegalArgumentException("Specified slug is already in use.");
                return new ResponseEntity<>("Specified slug is already in use.", HttpStatus.CONFLICT);
            }

            urlService.saveOrUpdateUrl(url);
        }

        return new ResponseEntity<>("Url added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public RedirectView redirectUrl(@PathVariable String id){

        return new RedirectView("");
    }

}
