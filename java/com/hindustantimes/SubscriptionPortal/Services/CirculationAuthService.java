package com.hindustantimes.SubscriptionPortal.Services;
import java.util.Collections;
import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.hindustantimes.SubscriptionPortal.Repository.CustomTokenRepository;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;
import com.hindustantimes.SubscriptionPortal.Repository.PublicationSchemeRepository;

@Service
public class CirculationAuthService {

    @Value("${CIRCULATION_AUTH_USERNAME}")
    String username;

    @Value("${CIRCULATION_AUTH_PASSOWRD}")
    String password;

    @Value("${CIRCULATION_URL}")
    String circulationUrl;

    @Autowired
    CustomTokenRepository customTokenRepo;

    public HttpHeaders generateTokenWithHeader(){
        String token = customTokenRepo.findAll().get(0).getPrimaryToken();
        HttpHeaders headers = new HttpHeaders();
        String headervalue = "Bearer " + token;
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", headervalue);
        return headers;
    }

    public CirculationAuthToken genereateToken() {
        CirculationTokenRequest request = new CirculationTokenRequest();
        request.setUsername(username);
        request.setPassword(password);
        String apiUrl = circulationUrl + "/circulation/api/generate-token/";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CirculationTokenRequest> entity = new HttpEntity<>(request);
        ResponseEntity<?> response= restTemplate.postForEntity(apiUrl, entity,CirculationAuthToken.class);
        System.out.println(response);
        return (CirculationAuthToken) response.getBody();
    }


}
