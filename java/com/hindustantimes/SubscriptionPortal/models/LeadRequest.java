package com.hindustantimes.SubscriptionPortal.models;

import lombok.Data;

@Data
public class LeadRequest {

    private String txnId;
    private String amount;
    private String schemeCode;
    private String accountId;
    private String orderId;
    private String name;
    private String email;
    private String mobile;
    private String salesOrg;


}
