package com.springboot.projectdemo.UrlShortener.Repository;

import com.springboot.projectdemo.UrlShortener.models.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    Optional<Organization> findByName(String name);
    boolean existsOrganizationByName(String name);
    long deleteOrganizationByName(String name);
    Optional<Organization> findByEmail(String email);
    boolean existsOrganizationByEmail(String email);
    long deleteOrganizationByEmail(String email);
    Optional<Organization> findByNamespace(String namespace);
    boolean existsOrganizationByNamespace(String namespace);
    long deleteOrganizationByNamespace(String namespace);

    @Query(value = "{ 'namespace' : ?0, 'urls.slug' : ?1 }")
    List<Organization> findByNamespaceAndUrlsSSlug(String namespace, String slug);



}
