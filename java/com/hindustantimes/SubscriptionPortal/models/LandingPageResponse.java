package com.hindustantimes.SubscriptionPortal.models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class LandingPageResponse {


    private String id;
    private String mobile;
    private String email;
    private String customerName;
    private String address;
    private List<Object> orders;
    private String vendorId;
    private String vendorCode;
    private String vendorName;
    private String vendorMobile;
    private String customerAddress;
    private AccountAddressResponse AddressDetails;
    private String locationCode;

}
