package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hindustantimes.SubscriptionPortal.Validations.OrderAddressConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RedeemMCouponRequest {

    @NotNull
    @NotBlank(message = "Vendor Id is mandatory")
    @JsonProperty("vendor_id")
    private String vendorId;
    @JsonProperty("vendor_updated")
    private boolean vendorUpdated;
    @JsonProperty("account_id")
    private String accountId;
    @NotBlank(message = "Source is mandatory")
    private String source;
    @NotBlank(message = "Mobile Number is mandatory")
    private String mobile;
    @NotNull(message = "Otp is mandatory")
    private int otp;
    @JsonProperty("redemption_source")
    @NotBlank(message = "Redemption source is mandatory")
    private String redemptionSource;
    List<McouponLeaf> coupons;


}
