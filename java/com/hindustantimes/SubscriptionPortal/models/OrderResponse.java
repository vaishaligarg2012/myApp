package com.hindustantimes.SubscriptionPortal.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

	@Column(name="ht_heroku_id__c")
	private String id;
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ht_scheme__r__ht_heroku_id__c")
	@JsonManagedReference
	private PublicationScheme scheme;
	private String daysRemaining;
	private String status;
	private String validUpto;
	private boolean orderIdExistsInOpportunity;


}
