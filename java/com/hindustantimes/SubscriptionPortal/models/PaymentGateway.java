package com.hindustantimes.SubscriptionPortal.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "payment_gateway_paymentgatewayconfiguration")
public class PaymentGateway {


    @Id
    private long id;

    @Column(name = "territory_id")
    private String territoryId;
    @Column(name = "publication")
    private String publication;
    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "account")
    private String mId;
    @Column(name = "access_key")
    private String key;
}
