package com.hindustantimes.SubscriptionPortal.Services;

import com.hindustantimes.SubscriptionPortal.models.LeadRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CreateLeadService {

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    public boolean createLead(String txnID ){
        LeadRequest lead  = new LeadRequest();
         //UserAccountInfo
        HttpSession session = httpSessionFactory.getObject();
        System.out.println("I called "+session);
        // ResponseEntity<?> resp = leadService.createLead(lead);
        return false;
    }
}
