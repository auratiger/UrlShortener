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
import java.util.Random;

@RestController
@CrossOrigin
public class ShortenedUrlController {

    private final UrlService urlService;

    public ShortenedUrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/urls")
    public ResponseEntity<?> saveOrUpdateUrl(@RequestBody Url url){
        //Throw Error if the url is the same as the domain
        String slug = url.getSlug();

        if(slug.trim().equals("")){
            int max = 15;
            int min = 1;
            int range = max - min + 1;

            do {
                int size = (int)(Math.random() * range + min);
                url.setSlug(NanoIdUtils.randomNanoId(
                        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                        NanoIdUtils.DEFAULT_ALPHABET,
                        size));
            }while(urlService.existsUrlBySlug(url.getSlug()));

            System.out.println(url);
            urlService.saveOrUpdateUrl(url);
        }else{
            if(urlService.existsUrlBySlug(slug)){
//                throw new IllegalArgumentException("Specified slug is already in use.");
                return new ResponseEntity<>("Specified slug is already in use.", HttpStatus.BAD_REQUEST);
            }

            urlService.saveOrUpdateUrl(url);
        }

        return new ResponseEntity<>(url, HttpStatus.CREATED);
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

    @DeleteMapping(value = "/urls/delete/{slug}")
    public ResponseEntity<String> deleteBySlug(@PathVariable String slug){

        long result = urlService.deleteUrlBySlug(slug);

        return result == 1 ? ResponseEntity.ok().body("Url with slug " + slug + " deleted successfully.") :
                ResponseEntity.badRequest().body("No such item exists.");
    }

    @PutMapping(value = "/urls/update/{slug}")
    public ResponseEntity<String> updateUrl(@RequestBody Url newUrl,@PathVariable String slug){
        Optional<Url> url = urlService.findBySlug(slug);

        if(url.isPresent()) {
            Url modifiedUrl = url.get();
            modifiedUrl.setSlug(newUrl.getSlug());
            modifiedUrl.setUrl(newUrl.getUrl());
            urlService.saveOrUpdateUrl(modifiedUrl);
            return ResponseEntity.ok().body("Update successful");
        }

        return ResponseEntity.badRequest().body("No such item exists.");
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<String> asdtest(){
        System.out.println("MAMA TI HUBAVA");
        return ResponseEntity.status(404).body("hello");
    }
}
