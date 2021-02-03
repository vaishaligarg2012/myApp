package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hindustantimes.SubscriptionPortal.Validations.OrderAddressConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateOrderRequest {
	@NotNull
	@JsonProperty("order_id")
	private String leadId;
	@JsonProperty("flat_no")
	private String flatNo;
	@JsonProperty("floor")
	private String floor;
	@JsonProperty("block_or_street")
	private String blockOrStreet;
	@JsonProperty("city")
	private String city;
	@JsonProperty("state")
	private String state;
	@JsonProperty("pin_code")
	private String pinCode;
	private String locality;
	private String society;
	@JsonProperty("locality_non_master")
	private String localityNonMaster;
	@JsonProperty("society_non_master")
	private String societyNonMaster;
}
