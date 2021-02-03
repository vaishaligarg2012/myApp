package com.hindustantimes.SubscriptionPortal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Getter
@Setter
@Table(name="ht_locality__c")
public class Locality {


	@Column(name="ht_sales_org__r__ht_heroku_id__c")
	private String salesOrgHerokuId;

	@Id
	@Column(name="ht_heroku_id__c")
	private String id;
	
	@Column(name="ht_area__c")
	private String area;
	
	@Column(name="ht_code__c")
	private String code;
	
	@Column(name="ht_state__c")
	private String state;
	
	@Column(name="ht_sales_org__c")
	private String salesOrgId;
	 
	@Column(name="ht_city__c")
	private String city;
	
	@Column(name="ht_active__c")
	private boolean isActive;
	
	@Column(name="name")
	private String name;
	
	
	@Column(name="lastmodifieddate")
	private String lastmodifieddate;
	
	@Column(name="isdeleted")
	private boolean isdeleted;
	
	@Column(name="ht_pincode__c")
	private String pincode;

}
