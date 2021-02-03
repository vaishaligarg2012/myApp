package com.hindustantimes.SubscriptionPortal.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hindustantimes.SubscriptionPortal.Validations.OrderAddressConstraint;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderRequest {

	@NotNull
	@NotBlank(message = "Name is mandatory")
	private String customerName;
	@NotNull
	@NotBlank(message = "Mobile is mandatory")
	@OrderAddressConstraint
	@Size(max=10, min = 10, message = "Mobile number should be 10 digit only")
    private String mobile;
	@NotNull
	@NotBlank(message = "Email is mandatory")
	private String email;
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
	@JsonProperty("locality_non_master")
	private String localityNonMaster;
	@JsonProperty("society_non_master")
	private String societyNonMaster;
	private String cityNonMaster;
	private String area;
	private String publication;
	private String society;
	private String lead_id;
	
	@NotNull
	@NotBlank(message = "Scheme is mandatory")
	private String scheme;
	@NotNull
	@NotBlank(message = "TransectionId is mandatory")
	@JsonProperty("transaction_id")
	@Size(max=18)
	private String transactionId;
	@NotNull
	@NotBlank(message = "Source is mandatory")
	private String source;
	@JsonProperty("digital_order_id")
	private String digitalOrderId;
	@JsonProperty("customer_unique_id")
	private String customerUniqueId;
	@NotNull(message="Amount is mandatory")
	private Integer amount;
	@NotNull
	@NotBlank(message = "SalesOrg is mandatory")  
	private String salesOrg;
	@JsonProperty("is_address_filled")
	private boolean isAddressFilled;
	private String order;
	private String account;


}
