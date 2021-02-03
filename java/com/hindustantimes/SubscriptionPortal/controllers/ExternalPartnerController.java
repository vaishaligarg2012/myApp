package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;
import com.hindustantimes.SubscriptionPortal.Repository.ExternalPartnerRepository;
import com.hindustantimes.SubscriptionPortal.builder.CouponResponseBuilder;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}"})
@RequestMapping(value = "${hindustan-subscription.api.version}" + "/external-partner")
class ExternalPartnerController{

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    ExternalPartnerRepository externalPartnerRepo;

    @Value("${VENDOR}")
    String vendor;


    @GetMapping("/change-vendor/{accountId}/{mobile}")
    public ExternalPartnerResponse<?> findOrders(@PathVariable String accountId,
                                        @PathVariable String mobile){

        ExternalPartnerResponse response = new ExternalPartnerResponse();
        Optional<Account> acc= accountRepo.findById(accountId);
        if(!acc.isPresent()){
            response.setSuccess(false);
            response.setMessage("Invalid Account Id");
            return response;
        }
        Account account = acc.get();
        String locationId = account.getMainCentre().getLocation().getId();
        Optional<ExternalPartner> partner = externalPartnerRepo.findByLocation_idAndMobileAndIsActiveAndType(locationId,mobile,true,vendor);
        if(!partner.isPresent()){
            response.setSuccess(false);
            response.setMessage("No Vendor exist with this mobile number");
            return response;
        }
        ExternalPartner vendor = partner.get();
        response.setSuccess(true);
        response.setMessage("success");
        response.setId(vendor.getId());
        response.setName(vendor.getName());
        response.setEmployeeCode(vendor.getEmployeeCode());
        response.setType(vendor.getType());
        response.setActive(vendor.getIsActive());
        response.setMobile(vendor.getMobile());
        return response;

    }




}

