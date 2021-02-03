package com.hindustantimes.SubscriptionPortal.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountAddressResponse {
        private String flatNum;
        private String floor;
        private String apartment;
        private String blockOrStreet;
        private String societyName;
        private String sector;
        private String localityName;
        private String pincode;
        private String city;
        private String state;
        private String localityId;
        private String societyId;
}
