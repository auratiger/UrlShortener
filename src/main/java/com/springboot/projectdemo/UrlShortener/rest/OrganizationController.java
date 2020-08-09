package com.springboot.projectdemo.UrlShortener.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import com.springboot.projectdemo.UrlShortener.models.Organization;
import com.springboot.projectdemo.UrlShortener.models.Url;
import com.springboot.projectdemo.UrlShortener.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @PostMapping(value = "/auth/signup")
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization){
        boolean created = organizationService.saveOrUpdateOrganization(organization);

        return created ? ResponseEntity.ok("Organization created") :
                ResponseEntity.badRequest().
                        body("Could not create Organization with specified values");
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
        Optional<Url> optionalUrl = organizationService.findUrlByNamespaceAndSlug(namespace, slug);

        if(optionalUrl.isPresent()){
            Url url = optionalUrl.get();

            return ResponseEntity.ok(url);
        }
        return ResponseEntity.badRequest().body(
                "Namespace " + namespace + " does not contain Url with slug value: " + slug);
    }

    @GetMapping(value = "/organization/{namespace}")
    public ResponseEntity<?> getAllOrganizationUrls(@PathVariable String namespace){
        Optional<Organization> optionalOrganization =
                organizationService.findByNamespace(namespace);

        return optionalOrganization
                .map(organization ->
                        ResponseEntity.ok().body(organization.getUrls()))
                .orElse(
                        ResponseEntity.ok().body(new ArrayList<>()));
    }

    @GetMapping(value = "/organization/red/{namespace}/{slug}")
    public RedirectView redirectToUrl(@PathVariable String namespace,
                                      @PathVariable String slug){
        Optional<Url> optionalUrl = organizationService.findUrlByNamespaceAndSlug(namespace, slug);

        if(optionalUrl.isPresent()){
            Url url = optionalUrl.get();

            return new RedirectView(url.getUrl());
        }
        return new RedirectView("http://localhost:8081");
    }

    @PutMapping(value = "/organization/{namespace}/{slug}")
    public ResponseEntity<?> updateOrganizationUrl(@PathVariable String namespace,
                                                   @PathVariable String slug,
                                                   @RequestBody Url url){

        Optional<Organization> optionalOrganization =
                organizationService.findOrganizationByNamespaceAndSlug(namespace, slug);

        if(optionalOrganization.isPresent()){
            Organization organization = optionalOrganization.get();
            Url organizationUrl = organization.getUrls().get(0);

            organizationUrl.setSlug(url.getSlug());
            organizationUrl.setUrl(url.getUrl());

            boolean created = organizationService.saveOrUpdateOrganization(organization);

            return created ? ResponseEntity.ok("Organization Url updated.") :
                    ResponseEntity.badRequest().
                            body("Could not update organization url with duplicate values.");
        }

        return ResponseEntity.badRequest().body("Could not find such Url.");
    }

    @DeleteMapping(value = "/organization/delete/{email}")
    public ResponseEntity<String> deleteOrganization(@PathVariable String email){
        long deleted = organizationService.deleteOrganizationByEmail(email);
        return deleted == 1 ? ResponseEntity.ok().body("Organization deleted") :
                ResponseEntity.badRequest().body("No such organization found");
    }

    @DeleteMapping(value = "organization/delete/{namespace}/{slug}")
    public ResponseEntity<?> deleteOrganizationUrl(@PathVariable String namespace,
                                                   @PathVariable String slug){

        Query query = new Query(Criteria.where("namespace").is(namespace));

        Update update = new Update().pull("Urls",
                new BasicDBObject("slug", slug));

        UpdateResult result = mongoTemplate.updateFirst(query, update, Organization.class);

        return result.wasAcknowledged() ?
                ResponseEntity.ok("Organization Url deleted.") :
                ResponseEntity.badRequest().body("No such url was found.");
    }

}

