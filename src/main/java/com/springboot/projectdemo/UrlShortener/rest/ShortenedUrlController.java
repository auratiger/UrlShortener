package com.springboot.projectdemo.UrlShortener.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.springboot.projectdemo.UrlShortener.models.Url;
import com.springboot.projectdemo.UrlShortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class ShortenedUrlController {

    private final UrlService urlService;

    public ShortenedUrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/urls")
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
                return new ResponseEntity<>("Specified slug is already in use.", HttpStatus.BAD_REQUEST);
            }

            urlService.saveOrUpdateUrl(url);
        }

        return new ResponseEntity<>("Url added successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/urls/{slug}")
    public RedirectView redirectUrl(@PathVariable String slug){

        Optional<Url> url = urlService.findBySlug(slug);

        return url.map(value -> new RedirectView(value.getUrl()))
                .orElse(new RedirectView("http://localhost:8081"));
    }

    @GetMapping(value = "/urls/abs/{slug}")
    public ResponseEntity<String> getUrl(@PathVariable String slug){
        Optional<Url> url = urlService.findBySlug(slug);

        return url.map(value -> ResponseEntity.ok().body(value.getUrl()))
                .orElse(ResponseEntity.badRequest().body("Url does not exist."));
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hello", HttpStatus.BAD_REQUEST);
    }

}
