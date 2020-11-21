package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.model.UserRequestModel;
import com.example.demo.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private CustomUserDetailsService usersService;
    private Environment environment;


    public AuthenticationFilter(CustomUserDetailsService usersService, Environment environment, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            //obtaining username(email) and pass word from req
            UserRequestModel creds = new ObjectMapper().readValue(req.getInputStream(),UserRequestModel.class);
            // paasing to token object
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),new ArrayList<>())

            );
        }

        catch(IOException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String userName = ((User) auth.getPrincipal()).getUsername();
        User userDetails = usersService.finduserdetails(userName);
        System.out.println(Long.parseLong(environment.getProperty("token.expiration_time")));
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secret"))
                .compact();

        res.addHeader("token", token);

        res.addHeader("userId", userDetails.getUsername());

    }

}
