package com.hindustantimes.SubscriptionPortal.Services;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hindustantimes.SubscriptionPortal.Repository.OpportunityRepository;
import com.hindustantimes.SubscriptionPortal.builder.LandingPageResponseBuilder;
import com.hindustantimes.SubscriptionPortal.builder.OrderResponseBuilder;
import com.hindustantimes.SubscriptionPortal.models.LandingPageResponse;
import com.hindustantimes.SubscriptionPortal.models.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.OrderRepository;

import com.hindustantimes.SubscriptionPortal.models.Account;
import com.hindustantimes.SubscriptionPortal.models.Order;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    LandingPageResponseBuilder landingPageResponseBuilder;
    @Autowired
    OrderResponseBuilder orderResponseBuilder;

    public LandingPageResponse fetchLandingPageResponse(Account accountInfo) {

        return landingPageResponseBuilder.buildLandingPageResponse(accountInfo);
        }
 }


