package com.hindustantimes.SubscriptionPortal.builder;

import java.util.ArrayList;
import java.util.List;

import com.hindustantimes.SubscriptionPortal.Repository.OpportunityRepository;
import com.hindustantimes.SubscriptionPortal.models.Opportunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hindustantimes.SubscriptionPortal.models.Order;
import com.hindustantimes.SubscriptionPortal.models.OrderResponse;


@Component
public class OrderResponseBuilder {
    @Autowired
    OpportunityRepository opportunityRepository;

    public List<OrderResponse> buildOrderResponse(List<Order> orderList) {
      List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();
        if (orderList != null && orderList.size() > 0) {
            for (Order order : orderList) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setId(order.getId());
                orderResponse.setStatus(order.getStatus());
                orderResponse.setScheme(order.getScheme());
                orderResponse.setDaysRemaining(order.getDaysRemaining());
                List<Opportunity> opportunities = opportunityRepository.findByOrderId(order.getId());
                if (opportunities != null && opportunities.size() > 0){
                    //should be true
                    orderResponse.setOrderIdExistsInOpportunity(true);
                }
                orderResponseList.add(orderResponse);
            }
        }
        return orderResponseList;
    }
}
