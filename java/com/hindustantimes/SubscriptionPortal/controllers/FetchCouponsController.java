package com.hindustantimes.SubscriptionPortal.controllers;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;
import com.hindustantimes.SubscriptionPortal.Services.CouponService;
import com.hindustantimes.SubscriptionPortal.Services.VerifyOtpService;
import com.hindustantimes.SubscriptionPortal.builder.CouponResponseBuilder;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "${hindustan-subscription.api.version}" + "/coupons")
class FetchCouponsController{

    @Autowired
    CouponResponseBuilder couponResponseBuilder;

    @Autowired
    CouponService couponService;

    @Autowired
    VerifyOtpService otpService;

    @Autowired
    AccountRepository accountRepo;


    @GetMapping("/get-all-coupons/{accountId}/{publication}/{source}")
    public ResponseEntity<?> findOrders(@PathVariable String accountId,
                                        @PathVariable String publication,
                                        @PathVariable String source){
        BaseResponse baseResponse = new BaseResponse();
        if(source.toLowerCase().equals("digital")){
            if (accountRepo.findByDigitalCustomerId(accountId) != null){
                accountId = accountRepo.findByDigitalCustomerId(accountId).getId();
            }
            else{
                baseResponse.setMessage("No customer account found");
                baseResponse.setSuccess(false);
                return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        Map<String,Object> couponList = couponResponseBuilder.fetchAllCoupons(accountId,publication);
        Optional<Account> accountOptional= accountRepo.findById(accountId);
        if(!accountOptional.isPresent()){
            baseResponse.setMessage("No account found");
            baseResponse.setSuccess(false);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
        }
        Account account = accountOptional.get();
        if(couponList != null){
            CouponResponse cResp = new CouponResponse();
            ExternalPartnerResponse vendor = new ExternalPartnerResponse();
            vendor.getPartnerResponse(account.getVendor());
            cResp.setCouponList(couponList);
            cResp.setVendor(vendor);
            cResp.setMessage("OK");
            cResp.setSuccess(true);
            return new ResponseEntity<>(cResp, HttpStatus.OK);
        }
        baseResponse.setMessage("No valid coupons found");
        baseResponse.setSuccess(false);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/redeem-coupons")
    public ResponseEntity<?> redeemCoupons(@Valid @RequestBody RedeemMCouponRequest request, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            CirculationCouponResponse response =new CirculationCouponResponse();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            response.setSuccess(false);
            for (FieldError e : errors) {
                message.add( e.getField() + "-" + e.getDefaultMessage());
            }
            response.setMessage(message.toString());
            return new ResponseEntity<CirculationCouponResponse>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        boolean isOtpVerified = otpService.otpVerifiying(request.getOtp(),request.getMobile(),request.getSource());
        if(!isOtpVerified)
        {
            CirculationCouponResponse response =new CirculationCouponResponse();
            response.setSuccess(false);
            response.setMessage("Invalid OTP");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CirculationCouponResponse<?> response = couponService.redeemMCoupon(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

