package com.hindustantimes.SubscriptionPortal.Services;


import com.hindustantimes.SubscriptionPortal.Repository.ExternalPartnerRepository;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

	@Autowired 
	AccountRepository accountRepo;

	@Autowired
	ExternalPartnerRepository partnerRepository;

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	public CirculationCouponResponse updateAccountCenterVendor(String accountId,String vendorId){
		Optional<Account> accountOptional= accountRepo.findById(accountId);
		CirculationCouponResponse response = new CirculationCouponResponse();
		if(accountOptional.isEmpty()){
			response.setSuccess(false);
			response.setMessage("Invalid Account Id");
			return response;
		}
		Account account= accountOptional.get();
		Optional<ExternalPartner> vendorOptinal = partnerRepository.findById(vendorId);
		if(vendorOptinal.isEmpty()){
			response.setSuccess(false);
			response.setMessage("Invalid Vendor Id");
		}
		ExternalPartner vendor = vendorOptinal.get();
		List<Territory> territories = vendor.getTerritory();
		if(territories.isEmpty()){
			response.setSuccess(false);
			response.setMessage("Vendor does not belong to any Main Center");
			return response;
		}
		Territory mainCenter = territories.get(0);
		account.setVendor(vendor);
		account.setMainCentre(mainCenter);
		accountRepo.save(account);
		response.setMessage("success");
		response.setSuccess(true);
		return response;
	}

	public Account getRegisteredUsersByMobile(String mobile) {
		List<Account> accounts = accountRepo.findByMobileNumber(mobile);
		return (accounts != null && accounts.size() > 0) ? accounts.get(0): null;
	}

	public boolean updateEmail(String email, String mobile){
		List<Account> accounts = accountRepo.findByMobileNumber(mobile);
		if (accounts != null && accounts.size() > 0) {
			Account accountInfo = accounts.get(0);
			accountInfo.setEmail(email);
			try{
				accountRepo.save(accountInfo);
				return true;
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		return false;
	}

}
