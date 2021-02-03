package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Services.AccountService;
import com.hindustantimes.SubscriptionPortal.builder.AccountInfoBuilder;
import com.hindustantimes.SubscriptionPortal.models.Account;
import com.hindustantimes.SubscriptionPortal.models.AccountResponse;
import com.hindustantimes.SubscriptionPortal.models.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = { "${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}" })
@RequestMapping(value = "${hindustan-subscription.api.version}")
public class AccountController {

    public static final Pattern VALIDATE_EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    AccountService accountService;

    @Autowired
    AccountInfoBuilder accountInfoBuilder;

    @PatchMapping("/update-email")
    public ResponseEntity<?> updateAccountInfo(@RequestParam(value = "email") String email,
                                               @RequestParam(value = "mobile") String mobile){
        Matcher checkEmail = VALIDATE_EMAIL.matcher(email);
        boolean validated = checkEmail.find();
        BaseResponse baseResponse = new BaseResponse();
        if(validated){
          boolean updated = accountService.updateEmail(email, mobile);

            if(updated) {
                baseResponse.setSuccess(true);
                baseResponse.setMessage("Email has been updated");
                return new ResponseEntity<BaseResponse>(baseResponse,
                        HttpStatus.OK);
            }else{
                baseResponse.setSuccess(false);
                baseResponse.setMessage("Email not updated");
                return new ResponseEntity<BaseResponse>(baseResponse,
                        HttpStatus.OK);
            }
        }else{
            baseResponse.setSuccess(false);
            baseResponse.setMessage("Email is not valid");
            return new ResponseEntity<BaseResponse>(baseResponse,
                    HttpStatus.OK);
        }


    }

    @GetMapping("/get-account-details")
    public ResponseEntity<?> getAccountInfo(@RequestParam(value = "mobile") String mobile){
       Account accountInfo = accountService.getRegisteredUsersByMobile(mobile);

        BaseResponse baseResponse = new BaseResponse();
        if (accountInfo != null) {
            AccountResponse accountResponse = accountInfoBuilder.buildAccountInfo(accountInfo);
            return new ResponseEntity<AccountResponse>(accountResponse,
                    HttpStatus.OK);
        }
        baseResponse.setSuccess(false);
        baseResponse.setMessage("Account not found");
        return new ResponseEntity<BaseResponse>(baseResponse,
                HttpStatus.NOT_ACCEPTABLE);
    }
}
