package com.hindustantimes.SubscriptionPortal.models;


import javax.persistence.*;

@Entity
@Table(name = "renewal_mapping__c")
public class RenewalMapping {

	@Id
	@Column(name = "ht_heroku_id__c")
    private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PublicationScheme getOldScheme() {
		return oldScheme;
	}

	public void setOldScheme(PublicationScheme oldScheme) {
		this.oldScheme = oldScheme;
	}

	public PublicationScheme getRenewalScheme() {
		return renewalScheme;
	}

	public void setRenewalScheme(PublicationScheme renewalScheme) {
		this.renewalScheme = renewalScheme;
	}

	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_old_scheme__r__ht_heroku_id__c")
	private PublicationScheme oldScheme;
	
	@ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="ht_renewal_scheme__r__ht_heroku_id__c")
	private PublicationScheme renewalScheme;
	
}
