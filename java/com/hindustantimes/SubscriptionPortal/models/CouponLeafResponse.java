package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponLeafResponse {

    @JsonProperty("coupon_number")
    private String couponNumber;
    @JsonProperty("sap_order_number")
    private String sapOrderNumber;
    @JsonProperty("month_id")
    private Integer monthOfRedemption;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("message")
    private String message;
}
