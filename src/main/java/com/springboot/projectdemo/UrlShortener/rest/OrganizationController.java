package com.springboot.projectdemo.UrlShortener.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import com.springboot.projectdemo.UrlShortener.models.AuthenticationRequest;
import com.springboot.projectdemo.UrlShortener.models.AuthenticationResponse;
import com.springboot.projectdemo.UrlShortener.models.Organization;
import com.springboot.projectdemo.UrlShortener.models.Url;
import com.springboot.projectdemo.UrlShortener.service.JwtOrganizationDetailsService;
import com.springboot.projectdemo.UrlShortener.service.OrganizationService;
import com.springboot.projectdemo.UrlShortener.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(allowCredentials = "true")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtOrganizationDetailsService organizationDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/auth/signup")
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization){
        organization.setPassword(bCryptPasswordEncoder.encode(organization.getPassword()));

        boolean created = organizationService.saveOrUpdateOrganization(organization);

        return created ? ResponseEntity.ok("Organization created") :
                ResponseEntity.badRequest().
                        body("Could not create Organization with specified values");
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request,
                                   HttpServletResponse response){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (BadCredentialsException e){
            throw new IllegalArgumentException("Incorrect email of password");
        }

        final UserDetails organizationDetails = organizationDetailsService.
                loadUserByUsername(request.getEmail());

        final String jwt = jwtUtil.generateToken(organizationDetails);

        Cookie cookie = new Cookie("token", jwt);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

//        response.addCookie(cookie);

        return ResponseEntity.ok().build();
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

