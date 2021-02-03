package com.hindustantimes.SubscriptionPortal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Table(name="ht_society__c")
@Entity
public class Society {

	@Id
	@Column(name="ht_heroku_id__c")
	private String id;

	@Column(name="ht_code__c")
	private String code;

	@Column(name="ht_locality__r__ht_heroku_id__c")
	private String localityHerokuId;

	@Column(name="ht_sales_org__c")
	private String salesOrg;

	@Column(name="ht_active__c")
	private boolean isActive;

	@Column(name="name")
	private String name;

	@Column(name="ht_sales_org__r__ht_heroku_id__c")
	private String salesOrgHerokuId;

	@Column(name="ht_locality__c")
	private String locality;

	@Column(name="isdeleted")
	private boolean isdeleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLocalityHerokuId() {
		return localityHerokuId;
	}

	public void setLocalityHerokuId(String localityHerokuId) {
		this.localityHerokuId = localityHerokuId;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalesOrgHerokuId() {
		return salesOrgHerokuId;
	}

	public void setSalesOrgHerokuId(String salesOrgHerokuId) {
		this.salesOrgHerokuId = salesOrgHerokuId;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
}
