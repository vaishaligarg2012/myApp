package com.hindustantimes.SubscriptionPortal.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
@Getter
@Setter
@Component
public class CouponResponse extends BaseResponse {

    private Map<String,Object> couponList;
    private ExternalPartnerResponse vendor;

}
