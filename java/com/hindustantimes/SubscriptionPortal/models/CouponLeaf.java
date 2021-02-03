package com.hindustantimes.SubscriptionPortal.models;

import lombok.Getter;
import lombok.Setter;
import java.util.Calendar;

@Getter
@Setter
public class CouponLeaf {

    private String couponNumber;
    private String sapOrderNumber;
    private Calendar expiryDate;
//    private Order order;
//    private PublicationScheme scheme;
    private Integer monthOfRedemption;
    private Integer rate;
    private String couponPreference;
    private String publication;
    private String tenure;
    private String location;
    private Calendar validUpto;
    private String type;
}
