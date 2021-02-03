package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;
import com.hindustantimes.SubscriptionPortal.Repository.ExternalPartnerRepository;
import com.hindustantimes.SubscriptionPortal.Services.CirculationAuthService;
import com.hindustantimes.SubscriptionPortal.Services.CreateCaseService;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = {"${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}"})
@RequestMapping(value = "${hindustan-subscription.api.version}" + "/case")
class CaseController{

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    ExternalPartnerRepository externalPartnerRepo;

    @Value("${VENDOR}")
    String vendor;

    @Autowired
    CreateCaseService createCaseService;


    @PostMapping("/create-case")
    public ResponseEntity<?> findOrders(@Valid @RequestBody CaseCreateRequest request, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            BaseResponse response =new BaseResponse();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            response.setSuccess(false);
            for (FieldError e : errors) {
                message.add( e.getField() + "-" + e.getDefaultMessage());
            }
            response.setMessage(message.toString());
            return new ResponseEntity<BaseResponse>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        return createCaseService.createCase(request);

    }




}

