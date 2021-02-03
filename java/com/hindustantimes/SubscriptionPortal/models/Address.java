package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Address {
    @JsonProperty("flat_no")
    private String flatNum;
    private String floor;
    private String apartment;
    @JsonProperty("block_or_street")
    private String blockOrStreet;
    private String area;
    @JsonProperty("suburb_non_master")
    private String suburbNonMaster;
    private String society;
    @JsonProperty("society_non_master")
    private String societyNonMaster;
    @JsonProperty("city_non_master")
    private String cityNonMaster;
    private String locality;
    @JsonProperty("pin_code")
    private String pincode;
}
