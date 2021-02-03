package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;
import com.hindustantimes.SubscriptionPortal.Services.OrderService;
import com.hindustantimes.SubscriptionPortal.builder.LandingPageResponseBuilder;
import com.hindustantimes.SubscriptionPortal.models.Account;
import com.hindustantimes.SubscriptionPortal.models.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}"})
@RequestMapping(value = "${hindustan-subscription.api.version}" + "/orders")
class FetchOrdersController{


    @Autowired
    LandingPageResponseBuilder landingPageResponseBuilder;

    @Autowired
    AccountRepository accountRepo;


    @GetMapping("/get-all-orders/{accountId}")
    public ResponseEntity<?> findOrders(@PathVariable String accountId ){
        BaseResponse baseResponse = new BaseResponse();
        Optional<Account> a = accountRepo.findById(accountId);
        Account accountInfo = a.orElse(null);
        List<Object> orderList = landingPageResponseBuilder.fetchAllOrders(accountInfo);

        if(orderList != null){
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        }
        baseResponse.setMessage("Orders not found");
        baseResponse.setSuccess(false);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}

