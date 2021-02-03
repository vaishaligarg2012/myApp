package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  OpportunityRepository extends JpaRepository<Opportunity, String> {
    List<Opportunity> findByOrderId(String orderId);
}
