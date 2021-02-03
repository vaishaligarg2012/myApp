package com.hindustantimes.SubscriptionPortal.builder;

import com.hindustantimes.SubscriptionPortal.models.Account;
import com.hindustantimes.SubscriptionPortal.models.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountInfoBuilder {

    public AccountResponse buildAccountInfo(Account account){
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setEmail(account.getEmail());
        accountResponse.setMobile(account.getMobileNumber());
        accountResponse.setName(account.getName());
        return accountResponse;
    }
}
