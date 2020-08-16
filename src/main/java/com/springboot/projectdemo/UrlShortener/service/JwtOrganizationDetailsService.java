package com.springboot.projectdemo.UrlShortener.service;

import com.springboot.projectdemo.UrlShortener.models.Organization;
import com.springboot.projectdemo.UrlShortener.util.JwtOrganizationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtOrganizationDetailsService implements UserDetailsService {

    @Autowired
    OrganizationService organizationService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Organization> organizationOptional =
                organizationService.findByEmail(email);
        //TODO: check if I can make this a lazy load
        return organizationOptional.map(
                JwtOrganizationDetails::new
        ).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
