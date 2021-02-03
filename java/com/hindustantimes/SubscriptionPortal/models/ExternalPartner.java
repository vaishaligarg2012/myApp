package com.hindustantimes.SubscriptionPortal.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "ht_external_partner__c")
public class ExternalPartner {

    @Id
    @Column(name = "ht_heroku_id__c")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "ht_phone__c")
    private String mobile;

    @Column(name = "ht_partner_code__c")
    private String employeeCode;

    @Column(name = "ht_partnertype__c")
    private String type;

    @Column(name = "ht_isactive__c")
    private Boolean isActive;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="ht_sales_org__r__ht_heroku_id__c")
    @JsonManagedReference
    private Territory location;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ht_territory_role__c",
            joinColumns = {
                    @JoinColumn(name = "ht_external_partner__r__ht_heroku_id__c", referencedColumnName = "ht_heroku_id__c",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(name = "ht_territory__r__ht_heroku_id__c", referencedColumnName = "ht_heroku_id__c",
                            nullable = true, updatable = true)})
    private List<Territory> territory;

}
