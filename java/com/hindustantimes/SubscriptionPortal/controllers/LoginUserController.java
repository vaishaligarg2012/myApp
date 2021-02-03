package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Services.*;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.apache.catalina.core.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hindustantimes.SubscriptionPortal.Exceptions.ResponseToUser;
import com.hindustantimes.SubscriptionPortal.Repository.OtpAdditionRepository;
import com.hindustantimes.SubscriptionPortal.Services.AccountService;
import com.hindustantimes.SubscriptionPortal.Services.OtpGenerateService;
import com.hindustantimes.SubscriptionPortal.Services.VerifyOtpService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@CrossOrigin(origins = {"${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}"})
@RequestMapping(value = "${hindustan-subscription.api.version}" + "/login")
public class LoginUserController {

    @Autowired
    OtpAdditionRepository otpRepo;

    @Autowired
    OtpGenerateService otpGenerateService;

    @Autowired
    AccountService accountService;

    @Autowired
    VerifyOtpService verifyOtpService;

    @Autowired
    OrderService orderService;

    private static final Logger LOGGER = LogManager.getLogger(LoginUserController.class);

    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOTP(@RequestBody OTPAddition requestInfo) {
        BaseResponse baseResponse = new BaseResponse();
        if (requestInfo.getMobileNum() == null || requestInfo.getMobileNum().isEmpty()) {
            baseResponse.setMessage("enter mobile number");
            baseResponse.setSuccess(false);
            return new ResponseEntity<BaseResponse>(baseResponse,
                    HttpStatus.BAD_REQUEST);
        }
        if (requestInfo.getSource() == null || requestInfo.getSource().isEmpty()) {
            baseResponse.setMessage("enter source");
            baseResponse.setSuccess(false);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
        }

        switch (requestInfo.getSource().toLowerCase()) {
            case "ssp": {
                Account customerAccount = accountService.getRegisteredUsersByMobile(requestInfo.getMobileNum());
                LOGGER.warn("Working till Registered user verified");
                LOGGER.warn(" authLead() " + customerAccount + " sef " + requestInfo.getSource());

                if (customerAccount != null) {
                    return getResponseEntity(requestInfo.getMobileNum(), requestInfo.getSource());
                } else {
                    baseResponse.setMessage("Mobile number does Not exists");
                    baseResponse.setSuccess(false);
                    return new ResponseEntity<BaseResponse>(baseResponse,
                            HttpStatus.NOT_ACCEPTABLE);
                }
            }
            case "vendorotp":
            case "digital": {
                    if(requestInfo.getMobileNum().length() != 10 ){
                        baseResponse.setMessage("Enter valid mobile number ");
                        baseResponse.setSuccess(false);
                        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
                    }
                    System.out.println(requestInfo.getSource());
                    if(!requestInfo.getSource().equalsIgnoreCase("Digital") && !requestInfo.getSource().equalsIgnoreCase("VENDOROTP")){
                        baseResponse.setMessage("Please enter valid source");
                        baseResponse.setSuccess(false);
                        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
                    }
                    return getResponseEntity(requestInfo.getMobileNum(), requestInfo.getSource());
                }
            default: {
                System.out.println("I am here");
                baseResponse.setMessage("Please enter valid source");
                baseResponse.setSuccess(false);
                return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);

            }
        }


    }

    private ResponseEntity<?> getResponseEntity(String mobile, String source) {
            BaseResponse baseResponse = new BaseResponse();
            OTPAddition obj = otpGenerateService.generateOtpAndAddToTable(mobile, source);
            LOGGER.warn("after save method");
            if (obj != null && obj.getMobileNum() != null) {
                baseResponse.setMessage("OTP generated successfully");
                baseResponse.setSuccess(true);
                return new ResponseEntity<BaseResponse>(baseResponse,
                        HttpStatus.OK);
            } else {
                baseResponse.setMessage("Some network issue while generating OTP");
                baseResponse.setSuccess(false);
                return new ResponseEntity<BaseResponse>(baseResponse,
                        HttpStatus.BAD_REQUEST);
            }
    }

    @GetMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestParam(value = "source") String source,
                                       @RequestParam(value = "mobile") String mobile,
                                       @RequestParam(value = "otp") int otp, HttpSession session) {
        LOGGER.warn("User has entered " + otp + " " + source + " " + mobile);
        BaseResponse baseResponse = new BaseResponse();
        if (mobile == null || mobile.isEmpty()) {
            baseResponse.setMessage("Enter mobile number");
            baseResponse.setSuccess(false);
            return new ResponseEntity<BaseResponse>(baseResponse,
                    HttpStatus.BAD_REQUEST);
        }
        if (source == null || source.isEmpty()) {
            baseResponse.setMessage("Enter source");
            baseResponse.setSuccess(false);
            return new ResponseEntity<BaseResponse>(baseResponse,
                    HttpStatus.BAD_REQUEST);
        }
        if (otp == 0) {
            baseResponse.setMessage("Enter otp");
            baseResponse.setSuccess(false);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
        }
        boolean isOtpValid = verifyOtpService.otpVerifiying(otp, mobile, source);

        if (isOtpValid) {
            switch (source.toLowerCase()) {
                case "ssp": {
                    Account accountInfo = accountService.getRegisteredUsersByMobile(mobile);
                    if (accountInfo != null) {
                        LandingPageResponse resp = orderService.fetchLandingPageResponse(accountInfo);
                        session.setAttribute("UserAccountInfo", resp);
                        return new ResponseEntity<>(resp, HttpStatus.OK);
                    } else {
                        baseResponse.setMessage("OTP is not valid");
                        baseResponse.setSuccess(false);
                        return new ResponseEntity<>(baseResponse,
                                HttpStatus.NOT_FOUND);
                    }
                }

                case "digital":{
                    baseResponse.setMessage("OTP has been validated");
                    baseResponse.setSuccess(true);
                    return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);

                }
                default: {
                    baseResponse.setMessage("Check source");
                    baseResponse.setSuccess(false);
                    return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
                }
            }
        } else {
            baseResponse.setMessage("OTP is not valid");
            baseResponse.setSuccess(false);
            return new ResponseEntity<>(baseResponse,
                    HttpStatus.NOT_FOUND);
        }

    }


}
