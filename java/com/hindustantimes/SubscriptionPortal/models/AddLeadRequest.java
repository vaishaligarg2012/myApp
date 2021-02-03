package com.hindustantimes.SubscriptionPortal.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddLeadRequest {
	private String mob; 
	private String remarks_non_master; 
	private String channel; 
	private String is_spot_or_gift; 
	private String block_or_street; 
	private String city_non_master; 
	private String publication; 
	private String with_vendor; 
	private String type_of_booking; 
	private String lat; 
	private String area; 
	private String lng; 
	private String locality;
	private String micr_no; 
	private String old_coupon_number; 
	private String otp; 
	private String schemes; 
	private String cheque_date; 
	private String reading_interest; 
	private String profession_occupation; 
	private String payment_mode_no; 
	private String city; 
	private String signature; 
	private String pin_code; 
	private String is_signature_mandatory; 
	private String society_non_master;
	private String locality_non_master;
	private String society;
	private String flat_no; 
	private String floor; 
	private String email; 
	private String lead_id; 
	private String bank_acc_no; 
	private String amount;
	private String payment_mode; 
	private String quantity;
	private String address; 
	private String belongs_to_vendor; 
	private String is_vendor_booking; 
	private String is_interested; 
	private String age_group; 
	private String suburb_non_master; 
	private String belongs_to_main_center; 
	private String manual_form_no; 
	private String suburb; 
	private String customer_name; 
	private String landline; 
	private String request_id; 
	private String apartment; 
	private String remarks; 
	private String source;
	private String sales_org;
	private String step;
	private String account;
	private String order;
	@JsonProperty("digital_order_id")
	private String digitalOrderId;
	@JsonProperty("digital_customer_id")
	private String digitalCustomerId;

}
