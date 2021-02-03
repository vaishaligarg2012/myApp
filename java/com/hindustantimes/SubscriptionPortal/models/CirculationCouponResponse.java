package com.hindustantimes.SubscriptionPortal.models;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Component
public class CirculationCouponResponse<T> extends BaseResponse{
    private List<CouponLeafResponse> coupons;
}
