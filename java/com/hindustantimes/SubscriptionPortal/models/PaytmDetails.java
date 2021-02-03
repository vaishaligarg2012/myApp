package com.hindustantimes.SubscriptionPortal.models;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
public class PaytmDetails {

     private String mid;
     private String orderID;
     private String custId;
     private String mobile;
     private String email;
}
