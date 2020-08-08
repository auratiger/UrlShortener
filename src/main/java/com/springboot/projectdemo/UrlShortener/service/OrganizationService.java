package com.springboot.projectdemo.UrlShortener.service;

import com.springboot.projectdemo.UrlShortener.models.Organization;
import com.springboot.projectdemo.UrlShortener.models.Url;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    List<Organization> findAll();
    void saveOrUpdateOrganization(Organization organization);
    Optional<Organization> findByName(String name);
    boolean existsOrganizationByName(String name);
    long deleteOrganizationByName(String name);
    Optional<Organization> findByEmail(String email);
    boolean existsOrganizationByEmail(String email);
    long deleteOrganizationByEmail(String email);
    Optional<Organization> findByNamespace(String namespace);
    boolean existsOrganizationByNamespace(String namespace);
    long deleteOrganizationByNamespace(String namespace);

    Optional<Url> findByNamespaceAndUrlsSSlug(String namespace, String slug);
}
