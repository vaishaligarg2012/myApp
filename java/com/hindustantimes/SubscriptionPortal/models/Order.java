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

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "\"order\"")
public class Order {
    @Id
    @Column(name="ht_heroku_id__c")
    private String id;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="account__ht_heroku_id__c")
    private Account account;

    @Column(name="type")
    private String type;

    @Column(name="status")
    private String status;

    @Column(name="ht_days_remaining_to_expire__c")
    private String daysRemaining;

    @Column(name="enddate")
    private Date validUpto;

    @Column(name="effectivedate")
    private Date startDate;

    @Column(name="ht_couponnumber__c")
    private String couponNumber;

    @Column(name="ht_external_id__c")
    private String sapOrderNumber;

    @Column(name="ht_coupon_preference__c")
    private String couponPreference;

    @Column(name="ht_coupon_start_date__c")
    private Calendar couponStartDate;

    @Column(name="ht_coupon_end_date__c")
    private Calendar couponEndDate;

//    @Column(name = "ht_days_remaining_to_expire__c")
//    private int daysRemainingToExpire;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_scheme__r__ht_heroku_id__c")
    @JsonManagedReference
    private PublicationScheme scheme;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_sales_org__r__ht_heroku_id__c")
    @JsonManagedReference
    private Territory location;
}
