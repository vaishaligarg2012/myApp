package com.hindustantimes.SubscriptionPortal.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Getter
@Setter 
@Table(name="ht_territory__c")
public class Territory {

	@Id
	@Column(name="ht_heroku_id__c")
	private String id;

	@Column(name="name")
	private String name;
	
	@Column(name="ht_territory_code__c")
	private String territoryCode;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_zone__r__ht_heroku_id__c")
	private Territory zone;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_region__r__ht_heroku_id__c")
	private Territory region;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_city_up_country__r__ht_heroku_id__c")
	private Territory cityUpc;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_sales_org__r__ht_heroku_id__c")
	private Territory location;
}