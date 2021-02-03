package com.hindustantimes.SubscriptionPortal.models;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "ht_heroku_id__c")
    private String id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "lastname")
    private String lastName;
	
	@Column(name = "personmobilephone")
	private String mobileNumber;

	@Column(name = "personemail")
	private String email;
	
	@Column(name = "ht_pin_code__c")
	private String pinCode;
	
	@Column(name = "ht_center__c")
	private String centerName;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "ht_mobile_otp__c")
	private String otp;

	@Column(name = "ht_flat_no__c")
	private String flatNum;

	@Column(name = "ht_floor__c")
	private String floor;

	@Column(name = "ht_landline__c")
	private String landLine;

	@Column(name = "ht_area__c")
	private String area;

	@Column(name = "ht_apartment__c")
	private String appartment;

	@Column(name = "ht_other_society__c")
	private String societyNonMaster;

	@Column(name = "ht_sector__c")
	private String sector;

	@Column(name = "ht_state__c")
	private String state;

	@Column(name = "ht_block__c")
	private String blockOrStreet;

	@Column(name = "ht_city__c")
	private String city;
 
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_locality__r__ht_heroku_id__c")
	private Locality locality;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_salesorg__r__ht_heroku_id__c")
	private Territory location;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_society__r__ht_heroku_id__c")
	private Society society;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_vendor__r__ht_heroku_id__c")
	private ExternalPartner vendor;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_center__r__ht_heroku_id__c")
	private Territory mainCentre;

	@Column(name = "ht_digital_customer_id__c")
	private String digitalCustomerId;



}
