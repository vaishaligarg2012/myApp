package com.hindustantimes.SubscriptionPortal.builder;

import com.hindustantimes.SubscriptionPortal.Services.AccountService;
import com.hindustantimes.SubscriptionPortal.models.Account;
import com.hindustantimes.SubscriptionPortal.models.OrderResponse;
import com.hindustantimes.SubscriptionPortal.models.PaymentGateway;
import com.hindustantimes.SubscriptionPortal.models.PaytmDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaytmPaymentBuilder {

    @Autowired
    AccountService accountService;


    public PaytmDetails initializedPaymentDetails(String linkedID, Account account, PaymentGateway paymentGateway) {

        OrderResponse orderResponseList = new OrderResponse();
        PaytmDetails paytmDetails = new PaytmDetails();
        paytmDetails.setMid(paymentGateway.getMId());
        paytmDetails.setOrderID(linkedID);
        paytmDetails.setCustId(account.getId());
        paytmDetails.setEmail(account.getEmail());
        paytmDetails.setMobile(account.getMobileNumber());
        return paytmDetails;
    }

}
