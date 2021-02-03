package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.Account;
import com.hindustantimes.SubscriptionPortal.models.Order;
import com.hindustantimes.SubscriptionPortal.models.PublicationScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

    List<Order> findByAccount(Account account);
    List<Order> findByAccount_IdAndCouponPreferenceOrderByCouponStartDate(String accountId,String couponPreference);
}
 