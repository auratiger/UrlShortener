package com.springboot.projectdemo.UrlShortener.rest;

import com.springboot.projectdemo.UrlShortener.models.Organization;
import com.springboot.projectdemo.UrlShortener.models.Url;
import com.springboot.projectdemo.UrlShortener.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @PostMapping(value = "/auth/signup")
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization){
        organizationService.saveOrUpdateOrganization(organization);
        return ResponseEntity.ok("Organization created");
    }

    @PostMapping(value = "/auth/login/{email}/{password}")
    public ResponseEntity<String> login(){

        //TODO: Login

        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/organization/{namespace}")
    public ResponseEntity<?> addUrl(@PathVariable String namespace, @RequestBody Url url){

        Optional<Organization> optionalOrganization = organizationService.findByNamespace(namespace);

        if(optionalOrganization.isPresent()){
            Organization organization = optionalOrganization.get();

            organization.addUrl(url);

            organizationService.saveOrUpdateOrganization(organization);
            return ResponseEntity.ok().body(organization);
        }else{
            return ResponseEntity.badRequest().body("Namespace " + namespace + " does not exist.");
        }
    }

    @GetMapping(value = "/organization/{namespace}/{slug}")
    public ResponseEntity<?> getOrganizationUrl(@PathVariable String namespace,
                                                @PathVariable String slug){
        Optional<Url> optionalUrl = organizationService.findByNamespaceAndUrlsSSlug(namespace, slug);

        if(optionalUrl.isPresent()){
            Url url = optionalUrl.get();

            return ResponseEntity.ok(url);
        }
        return ResponseEntity.badRequest().body(
                "Namespace " + namespace + " does not contain Url with slug value: " + slug);
    }



}

