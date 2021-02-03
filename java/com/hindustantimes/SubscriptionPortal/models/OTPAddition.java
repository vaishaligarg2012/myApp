package com.hindustantimes.SubscriptionPortal.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "unsold_communication_lead_addition_otp")
public class OTPAddition {


	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	

    @Column(name = "mobile_num")
	private String mobileNum;
    
    @Column(name = "otp")
	private int otp; 
    
    @Column(name = "generation_time")
	private LocalDateTime generationTime;
	
    @Column(name = "expiration_time")
    private LocalDateTime expiredTime;
    
    @Column(name = "is_api_call_success")
	private boolean isApiCallSuccess;
    
    @Column(name = "is_consumed")
	private boolean isConsumed;
    
    @Column(name = "is_verified")
	private boolean isVerified;
    
    @Column(name = "source")
	private String source;



}
