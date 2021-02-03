package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {


    public Optional<Payment> findByLinkId(String orderId);

}
