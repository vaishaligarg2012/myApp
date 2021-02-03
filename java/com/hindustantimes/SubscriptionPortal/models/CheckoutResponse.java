package com.hindustantimes.SubscriptionPortal.models;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CheckoutResponse extends BaseResponse {

    String txnToken;
}
