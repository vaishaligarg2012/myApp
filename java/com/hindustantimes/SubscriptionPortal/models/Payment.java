package com.hindustantimes.SubscriptionPortal.models;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "ht_payment__c")
public class Payment {

    @Id
    @Column(name = "ht_heroku_id__c")
    private String id;


    @Column(name = "id")
    private int oldId;

//    @Column(name="ht_sales_org__c")
//    private String salesOrg;

    @Column(name = "name")
    private String name;

    @Column(name = "ht_amount__c")
    private Double amount;

//    @ManyToOne(cascade={CascadeType.ALL})
//    @JoinColumn(name="ht_lead__r__ht_heroku_id__c")
//    private Lead lead;

    @Column(name = "ht_payment_getway_txn_id__c")
    private String gatewayTransactionId;

    @Column(name = "ht_payment_status__c")
    private String paymentStatus;

    @Column(name = "ht_payment_mode__c")
    private String paymentMode;

    @Column(name = "ht_linkid__c")
    private String linkId;

    @Column(name = "ht_schemeamount__c")
    private Double schemeAmount;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ht_scheme__r__ht_heroku_id__c")
    private PublicationScheme scheme;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ht_lead__r__ht_heroku_id__c")
    private Lead lead;

    @Temporal(TemporalType.DATE)
    @Column(name = "createddate")
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;
}
