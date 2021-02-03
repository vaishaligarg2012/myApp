package com.hindustantimes.SubscriptionPortal.models;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "product2")
public class PublicationScheme {

	@Id
	@Column(name = "ht_heroku_id__c")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "productcode")
    private String code;

    @Column(name = "ht_primary_publication__c")
    private String sapPublication;

    @Column(name = "isactive")
    private Boolean isActive;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_sales_org__r__ht_heroku_id__c")
    private Territory salesOrg;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_parent_scheme__c")
    private PublicationScheme parentScheme;
    @Column(name = "ht_scheme_combo__c")
    private String schemeCombo;
    @Column(name = "recordtypeid")
    private String recordType;
    @Column(name="ht_publications__c")
    private String publications;
    @Column(name="ht_end_date__c")
    private String validUpto;
    @Column(name = "ht_price__c")
    private Integer price;
    @Column(name = "ht_channel__c")
    private String channel;
    @Column(name="ht_scheme_source__c")
    private String source;
    @Column(name="ht_t_c_link__c")
    private String tncLink;
    @Column(name="ht_booking_type__c")
    private String bookingType;
    @Column(name="ht_days__c")
    private String days;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ht_scheme_territory_mapping__c",
            joinColumns = {
                    @JoinColumn(name = "ht_scheme__r__ht_heroku_id__c", referencedColumnName = "ht_heroku_id__c",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(name = "ht_territory__r__ht_heroku_id__c", referencedColumnName = "ht_heroku_id__c",
                            nullable = true, updatable = true)})
    private List<Territory> territory;

    @Column(name="ht_duration__c")
    private String duration;
}
