package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class McouponLeaf {

        @NotNull
    @JsonProperty("sap_order_number")
    @NotBlank(message = "Order Number is mandatory")
    private String sapOrderNumber;
    @NotNull
    @JsonProperty("coupon_number")
    @NotBlank(message = "Coupon Number is mandatory")
    private String couponNumber;
    @NotNull
    @JsonProperty("month_id")
    @NotBlank(message = "Month Id is mandatory")
    private Integer monthOfRedemption;
    @NotNull
    @NotBlank(message = "Expiry Date is mandatory")
    @JsonProperty("expiry_date")
    private String expiryDate;
    @NotNull
    @NotBlank(message = "Location is mandatory")
    @JsonProperty("location")
    private String location;

}
