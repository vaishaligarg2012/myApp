package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@Table(name = "coupon_schedule__c")
public class CouponSchedule {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ht_coupon_type__c")
    private String couponType;

    @Column(name = "ht_month_id__c")
    private Integer month;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_scheme__r__ht_heroku_id__c")
    private PublicationScheme scheme;

}
