package com.springboot.projectdemo.UrlShortener.service;

import com.mongodb.MongoWriteException;
import com.springboot.projectdemo.UrlShortener.Repository.OrganizationRepository;
import com.springboot.projectdemo.UrlShortener.models.Organization;
import com.springboot.projectdemo.UrlShortener.models.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public boolean saveOrUpdateOrganization(Organization organization) {
        try {
            organizationRepository.save(organization);
        }catch (DuplicateKeyException | MongoWriteException e){
            e.printStackTrace();
            //TODO: Log to file
            return false;
        }
        return true;
    }

    @Override
    public Optional<Organization> findByName(String name) {
        return organizationRepository.findByName(name);
    }

    @Override
    public boolean existsOrganizationByName(String name) {
        return organizationRepository.existsOrganizationByName(name);
    }

    @Override
    public long deleteOrganizationByName(String name) {
        return organizationRepository.deleteOrganizationByName(name);
    }

    @Override
    public Optional<Organization> findByEmail(String email) {
        return organizationRepository.findByEmail(email);
    }

    @Override
    public boolean existsOrganizationByEmail(String email) {
        return organizationRepository.existsOrganizationByEmail(email);
    }

    @Override
    public long deleteOrganizationByEmail(String email) {
        return organizationRepository.deleteOrganizationByEmail(email);
    }

    @Override
    public Optional<Organization> findByNamespace(String namespace) {
        return organizationRepository.findByNamespace(namespace);
    }

    @Override
    public boolean existsOrganizationByNamespace(String namespace) {
        return organizationRepository.existsOrganizationByNamespace(namespace);
    }

    @Override
    public long deleteOrganizationByNamespace(String namespace) {
        return organizationRepository.deleteOrganizationByNamespace(namespace);
    }

    @Override
    public Optional<Url> findUrlByNamespaceAndSlug(String namespace, String slug) {
        List<Organization> results = organizationRepository.findByNamespaceAndUrlsSSlug(namespace, slug);
        Url url = null;

        for(Organization organization : results) {
            url = organization.getUrls().get(0);
        }

        return Optional.ofNullable(url);
    }

    @Override
    public Optional<Organization> findOrganizationByNamespaceAndSlug(String namespace, String slug) {

        List<Organization> results = organizationRepository.findByNamespaceAndUrlsSSlug(namespace, slug);

        for(Organization organization : results){
            return Optional.of(organization);
        }

        return Optional.empty();
    }
}
