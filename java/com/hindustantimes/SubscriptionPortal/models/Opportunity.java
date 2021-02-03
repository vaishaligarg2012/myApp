package com.hindustantimes.SubscriptionPortal.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="opportunity")
@Entity
public class Opportunity {

    @Id
    @Column(name="ht_heroku_id__c")
    private String id;

    @Column(name = "ht_order__c")
    private String orderId;

    @Column(name="ht_digital_order_id__c")
    private String digitalOrderId;

    @Column(name="ht_digital_customer_id__c")
    private String customerUniqueId;

    @Column(name="ht_locality__r__ht_heroku_id__c")
    private String locality;

    @Column(name="ht_other_locality__c")
    private String otherLocality;

    @Column(name="ht_society__r__ht_heroku_id__c")
    private String society;

    @Column(name="ht_other_society__c")
    private String otherSociety;

    @Column(name="ht_flat_no__c")
    private String flatNo;

    @Column(name="ht_floor__c")
    private String floor;

    @Column(name="ht_block__c")
    private String blockOrStreet;

    @Column(name="ht_city__c")
    private String city;

    @Column(name="ht_state__c")
    private String state;

    @Column(name="ht_pin_code__c")
    private String pinCode;
}
