package com.springboot.projectdemo.UrlShortener.config;

import com.springboot.projectdemo.UrlShortener.service.JwtOrganizationDetailsService;
import com.springboot.projectdemo.UrlShortener.util.JwtOrganizationDetails;
import com.springboot.projectdemo.UrlShortener.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@PropertySource("classpath:authentication.properties")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtOrganizationDetailsService organizationDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if(path.startsWith("/urls") || path.startsWith("/auth")){
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader(tokenHeader);

        String email = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try{
                email = jwtUtil.getSubjectFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                throw new IllegalArgumentException();
            }catch (ExpiredJwtException ex){
                ex.printStackTrace();
            }
        }else{
            System.out.println("JWT Token does not begin with Bearer String");
            throw new MissingBearerTokenException("Missing Bearer token");
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails jwtOrganizationDetails =
                    organizationDetailsService.loadUserByUsername(email);

            if(jwtUtil.validateToken(jwtToken, jwtOrganizationDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                jwtOrganizationDetails,
                                null,
                                jwtOrganizationDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            chain.doFilter(request, response);
        }
    }
}
