package com.hindustantimes.SubscriptionPortal.Services;

import com.hindustantimes.SubscriptionPortal.Repository.PaymentGatewayConfig;
import com.hindustantimes.SubscriptionPortal.models.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentGatewayDetails {

    @Autowired
    PaymentGatewayConfig paymentGatewayConfig;
    PaymentGateway configInfo;

    public PaymentGateway getPaymentGatewayDetails(String territoryId, String publication, String paymentMode, boolean isActive) {
        if (territoryId != null) {
            configInfo = paymentGatewayConfig.findAll().stream().filter(obj -> obj.getPaymentMode().equals(paymentMode) && obj.getTerritoryId().equals(territoryId) && obj.getPublication().equals(publication.split(";")[0]) && (isActive == obj.isActive())).findAny().orElse(null);

        } else {
            configInfo = paymentGatewayConfig.findAll().stream().filter(obj -> obj.getPaymentMode().equals(paymentMode) && obj.getPublication().equals(publication.split(";")[0]) && (isActive == obj.isActive())).findAny().orElse(null);

        }
        return configInfo;
    }

}
