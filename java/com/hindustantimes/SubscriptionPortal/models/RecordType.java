package com.hindustantimes.SubscriptionPortal.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recordtype")
public class RecordType {

	@Id
	@Column(name = "sfid")
    private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSobjecttype() {
		return sObjectType;
	}

	public void setSobjecttype(String sObjectType) {
		this.sObjectType = sObjectType;
	}

	@Column(name = "developername")
    private String name;
	
	@Column(name = "sobjecttype")
	private String sObjectType;

}
