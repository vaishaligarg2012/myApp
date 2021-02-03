package com.hindustantimes.SubscriptionPortal.models;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "\"lead\"")
public class Lead {
    @Id
    @Column(name="ht_heroku_id__c")
    private String id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_account__r__ht_heroku_id__c")
    private Account account;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_scheme__r__ht_heroku_id__c")
    @JsonManagedReference
    private PublicationScheme scheme;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_salesorg__r__ht_heroku_id__c")
    @JsonManagedReference
    private Territory location;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_order__r__ht_heroku_id__c")
    @JsonManagedReference
    private Order order;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="convertedopportunity__ht_heroku_id__c")
    @JsonManagedReference
    private Opportunity opportunity;

    @Column(name="mobilephone")
    private String mobile;

    @Column(name="ht_digital_order_id__c")
    private String digitalOrderId;

    @Column(name="ht_digital_customer_id__c")
    private String customerUniqueId;

    @Column(name="ht_locality__r__ht_heroku_id__c")
    private String locality;

    @Column(name="other_locality__c")
    private String otherLocality;

    @Column(name="ht_society__r__ht_heroku_id__c")
    private String society;

    @Column(name="other_society__c")
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

    @Column(name="ht_booking_form_no__c")
    private String bookingFormNo;

    @Column(name="ht_booking_type__c")
    private String bookingType;

    @Column(name="ht_booking_channel__c")
    private String channel;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_payment__r__ht_heroku_id__c")
    @JsonManagedReference
    private Payment payment;
}
